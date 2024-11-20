package statistics101.util;

import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static statistics101.util.KahanSum.kahanSum;
import static statistics101.util.KahanSum.plainForLoopSum;

class KahanSumTest {
    @ParameterizedTest
    @MethodSource
    void sum(double[] x) {
        // stream#sum は比較的正確
        double actual = kahanSum(x);
        assertEquals(Arrays.stream(x).sum(), actual);
        assertNotEquals(plainForLoopSum(x), actual);
    }

    static Stream<Arguments> sum() {
        // Plain for-loop sum において浮動小数点演算による誤差があり得る例を集める
        return Stream.of(
                // 例えば 1 + 1e-16 の 1e-16 が丸め誤差で消える
                // 1e-16 の表現には問題ないが、1.0 と 1e-16の演算には有効数字が足りず、結果が丸められる
                arguments(new double[]{1.0, 1e-16, -1.0}),
                // standardization で扱った zスコア変換後の配列
                arguments(new double[]{-1.521, -1.217, -0.913, -0.609, 0.0, 0.0, 0.609, 0.913, 1.217, 1.521})
        );
    }

    //  Kahan-sum も Overflow, Underflow には対応しない
//    void overFlowCase() {
//        final double[] x = {1e308, 1e308, -1e308};
//    }
}
