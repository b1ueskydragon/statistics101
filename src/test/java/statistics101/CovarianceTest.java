package statistics101;

import java.util.stream.Stream;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CovarianceTest {
    private static final Percentage PER = Percentage.withPercentage(1e-1);

    @ParameterizedTest
    @MethodSource
    void testCovariance(double[] x, double[] y, double expected) {
        assertThat(Covariance.covariance(x, y)).isCloseTo(expected, PER);
    }

    static Stream<Arguments> testCovariance() {
        return Stream.of(
                // (4/3) > 0 なので, x が増える時に y も増える傾向にある
                arguments(new double[]{1, 2, 3}, new double[]{2, 4, 6}, 4.0 / 3.0)
        );
    }
}
