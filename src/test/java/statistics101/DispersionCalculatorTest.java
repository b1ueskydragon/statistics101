package statistics101;

import java.util.stream.Stream;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static statistics101.DispersionCalculator.cv;
import static statistics101.DispersionCalculator.gi;
import static statistics101.DispersionCalculator.mad;
import static statistics101.DispersionCalculator.mean;
import static statistics101.DispersionCalculator.standardDeviation;
import static statistics101.DispersionCalculator.toTscore;
import static statistics101.DispersionCalculator.toZscore;
import static statistics101.DispersionCalculator.variance;

class DispersionCalculatorTest {
    private static final Percentage PER = Percentage.withPercentage(1e-1);

    static Stream<Arguments> all() {
        // 今回のケースでは平均差とジニ係数が桁ずれのような形になっているが、これは偶然にすぎない
        // データセット A, B, C の平均がそれぞれ一定に5であり、分母 (2 x 5 = 10) が10と一定となった
        // 結果、たまたま桁が1つずれるような形になった
        double[] A = {0, 3, 3, 5, 5, 5, 5, 7, 7, 10};
        double[] B = {0, 1, 2, 3, 5, 5, 7, 8, 9, 10};
        double[] C = {3, 4, 4, 5, 5, 5, 5, 6, 6, 7};

        // 別の、極端的な格差が存在する例
        // CV>1 なので、ばらつきが非常に大きい
        double[] D = {0, 0, 0, 0, 10};

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
    void all(double[] x,
             double mean, double mad, double gi, double variance, double sd, double cv) {
        assertThat(mean(x)).isCloseTo(mean, PER);
        assertThat(mad(x)).isCloseTo(mad, PER);
        assertThat(gi(x)).isCloseTo(gi, PER);
        assertThat(variance(x)).isCloseTo(variance, PER);
        assertThat(standardDeviation(x)).isCloseTo(sd, PER);
        assertThat(cv(x)).isCloseTo(cv, PER);
    }

    @ParameterizedTest
    @MethodSource
    void standardization(double[] x, double[] zScore, double[] tScore) {
        double[] z = toZscore(x);
        double[] t = toTscore(z);
        for (int i = 0; i < x.length; i++) {
            assertThat(z[i]).isCloseTo(zScore[i], PER);
            assertThat(t[i]).isCloseTo(tScore[i], PER);
        }
    }

    static Stream<Arguments> standardization() {
        return Stream.of(
                arguments(
                        new double[]{2, 4, 6}, // x
                        new double[]{-1.224, 0, 1.224}, // z
                        new double[]{37.75, 50, 62.24} // t
                ),
                arguments(
                        new double[]{0, 1, 2, 3, 5, 5, 7, 8, 9, 10}, // x
                        new double[]{-1.521, -1.217, -0.913, -0.609, 0.0, 0.0, 0.609, 0.913, 1.217, 1.521}, // z
                        new double[]{34.79, 37.83, 40.87, 43.91, 50.0, 50.0, 56.09, 59.13, 62.17, 65.21} // t
                )
        );
    }

}
