package statistics101;

import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class EntropyTest {
    private static final Percentage PER = Percentage.withPercentage(1e-1);

    @ParameterizedTest
    @MethodSource
    void entropy(double[] p, double expected) {
        assertThat(Entropy.entropy(p)).isCloseTo(expected, PER);
    }

    static Stream<Arguments> entropy() {
        return Stream.of(
                // やさしい例
                arguments(new double[]{0.5, 0.3, 0.2}, 0.447),

                // どれかの p[i] = 1 の時、そのデータのエントロピーは 0 になる
                arguments(new double[]{0.0, 1.0, 0.0, 0.0}, 0.0),

                // 二つのエントロピーの比較
                arguments(Entropy.relativeFrequencies(Map.of("A", 32d, "B", 19d, "C", 10d, "D", 24d, "E", 15d)), 0.668),
                arguments(Entropy.relativeFrequencies(Map.of("A", 28d, "B", 13d, "C", 18d, "D", 29d, "E", 12d)), 0.670)
        );
    }
}
