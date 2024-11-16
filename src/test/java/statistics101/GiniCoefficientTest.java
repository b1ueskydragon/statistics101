package statistics101;

import java.util.stream.Stream;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static statistics101.GiniCoefficient.avg;
import static statistics101.GiniCoefficient.cv;
import static statistics101.GiniCoefficient.gi;
import static statistics101.GiniCoefficient.mad;
import static statistics101.GiniCoefficient.standardDeviation;
import static statistics101.GiniCoefficient.variance;

class GiniCoefficientTest {
    private static final Percentage PER = Percentage.withPercentage(1e-1);

    static Stream<Arguments> all() {
        // 今回のケースでは平均差とジニ係数が桁ずれのような形になっているが、これは偶然にすぎない
        // データセット A, B, C の平均がそれぞれ一定に5であり、分母 (2 x 5 = 10) が10と一定となった
        // 結果、たまたま桁が1つずれるような形になった
        int[] A = {0, 3, 3, 5, 5, 5, 5, 7, 7, 10};
        int[] B = {0, 1, 2, 3, 5, 5, 7, 8, 9, 10};
        int[] C = {3, 4, 4, 5, 5, 5, 5, 6, 6, 7};

        // 別の、極端的な格差が存在する例
        // CV>1 なので、ばらつきが非常に大きい
        int[] D = {0, 0, 0, 0, 10};

        return Stream.of(
                //        データセット,
                //        算術平均, 平均差, ジニ係数, 分散, 標準偏差, 変動係数
                arguments(A,
                          5.00, 2.76, 0.276, 6.60, 2.57, 0.514),
                arguments(B,
                          5.00, 3.76, 0.376, 10.8, 3.286, 0.657),
                arguments(C,
                          5.00, 1.20, 0.120, 1.20, 1.095, 0.219),
                arguments(D,
                          2.00, 3.20, 0.800, 16.0, 4.00, 2.00)
        );
    }

    @ParameterizedTest
    @MethodSource
    void all(int[] xs,
             double avg, double mad, double gi, double variance, double sd, double cv) {
        assertThat(avg(xs)).isCloseTo(avg, PER);
        assertThat(mad(xs)).isCloseTo(mad, PER);
        assertThat(gi(xs)).isCloseTo(gi, PER);
        assertThat(variance(xs)).isCloseTo(variance, PER);
        assertThat(standardDeviation(xs)).isCloseTo(sd, PER);
        assertThat(cv(xs)).isCloseTo(cv, PER);
    }
}
