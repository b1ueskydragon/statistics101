package statistics101.util;

import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static statistics101.util.KahanSum.kahanSum;
import static statistics101.util.KahanSum.streamSum;
import static statistics101.util.KahanSum.sumWithoutCompensation;

@SuppressWarnings("PrimitiveArrayArgumentToVariableArgMethod")
class KahanSumTest {
    @ParameterizedTest
    @MethodSource
    void sum(double[] x) {
        double streamSum = streamSum(x);
        double kahanSum = kahanSum(x);
        assertEquals(streamSum, kahanSum);
        assertNotEquals(sumWithoutCompensation(x), kahanSum);
    }

    static Stream<Arguments> sum() {
        // Plain for-loop sum において浮動小数点演算による誤差があり得る例を集める
        return Stream.of(
                // 1e-16 == 10^(-16) == 0.0000000000000001
                // 例えば 1 + 1e-16 の 1e-16 が丸め誤差で消える
                // 1e-16 の表現には問題ないが, 1.0 と 1e-16の演算には有効数字が足りず, 結果が丸められる
                arguments(new double[]{1.0, 1e-16, -1.0}),
                // standardization で扱った zスコア変換後の配列
                arguments(new double[]{-1.521, -1.217, -0.913, -0.609, 0.0, 0.0, 0.609, 0.913, 1.217, 1.521})
        );
    }

    // Kahan-sum, Arrays#stream#sum どちらも丸められるケース
    @Test
    void roundingErrorCase() {
        // precise sum should be 2.0000000000000001
        // but 1e-16 will be lost
        //
        // [1.0, 1e-16, -1.0] : 大きな値が相殺され, 補正することで小さな値を残せる
        // [1.0, 1e-16, 1.0] : 大きな値は相殺されない(大きな値が支配的), 小さな値は失われる
        // そもそも 2.0000000000000001 が double で正確に表現できない
        final double[] x = new double[]{1.0, 1e-16, 1.0};
        assertEquals(2.0000000000000000, streamSum(x));
        assertEquals(2.0000000000000000, kahanSum(x));

        // BigDecimal を使わない限り丸められる
        final var a = new BigDecimal("1.0");
        final var b = new BigDecimal("1e-16");
        final var c = new BigDecimal("1.0");
        assertEquals(new BigDecimal("2.0000000000000001"), a.add(b).add(c));
    }

    // Kahan-sum, Arrays#stream#sum どちらもOverflow や Underflow には対応しない
    @Test
    void overFlowCase() {
        final double[] x = {1e308, 1e308, -1e308};
        assertTrue(Double.isInfinite(streamSum(x)));
        assertTrue(Double.isNaN(kahanSum(x)));
    }
}
