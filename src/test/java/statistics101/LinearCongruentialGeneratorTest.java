package statistics101;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LinearCongruentialGeneratorTest {
    @Test
    void testLCG() {
        final long seed = 3;
        final var lcg = new LinearCongruentialGenerator(seed);
        final double[] actual = new double[(int) seed];
        for (int i = 0; i < seed; i++) {
            actual[i] = lcg.uniformRandom();
        }
        // LCG は 疑似乱数生成器 なので同じ seed, 同じパラメータを使うと, 常に同じ数列が生成される.
        assertThat(actual[0]).isEqualTo(0.23723063012585044);
        assertThat(actual[1]).isEqualTo(0.5506782040465623);
        assertThat(actual[2]).isEqualTo(0.8736585769802332);
    }
}
