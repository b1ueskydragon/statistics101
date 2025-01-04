package statistics101;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static statistics101.LinearCongruentialGenerator.Mode.ANSI_C;
import static statistics101.LinearCongruentialGenerator.Mode.NUMERICAL_RECIPES;

class LinearCongruentialGeneratorTest {
    @ParameterizedTest
    @CsvSource({
            "0, 0.2403310495428741",
            "1, 0.2763083253521472",
            "2, 0.35132475569844246",
            "3, 0.07504692277871072",
            "4, 0.7152062063105404",
            "5, 0.8466270251665264",
            "6, 0.08513328526169062",
            "7, 0.7177181884180754",
            "8, 0.10364456987008452",
            "9, 0.2137309752870351",
            "10, 0.7877076249569654"
    })
    void testUniformRandom(int index, double value) {
        final long seed = 11;
        final var lcg = new LinearCongruentialGenerator(seed, NUMERICAL_RECIPES);
        final double[] actual = new double[(int) seed];
        for (int i = 0; i < seed; i++) {
            actual[i] = lcg.uniformRandom();
        }
        // LCG は 疑似乱数生成器 なので同じ seed, 同じパラメータを使うと, 常に同じ数列が生成される.
        assertThat(actual[index]).isEqualTo(value);
    }


    @ParameterizedTest
    @CsvSource({
            "0, 3",
            "1, 4",
            "2, 4",
            "3, 1",
            "4, 8",
            "5, 10",
            "6, 1",
            "7, 8",
            "8, 2",
            "9, 3",
            "10, 9"
    })
    void testNextInt1to11(int index, int value) {
        final long seed = 11;
        final var lcg = new LinearCongruentialGenerator(seed, NUMERICAL_RECIPES);
        final int[] actual = new int[(int) seed];
        for (int i = 0; i < seed; i++) {
            actual[i] = lcg.nextInt1to11();
        }
        // LCG は 疑似乱数生成器 なので同じ seed, 同じパラメータを使うと, 常に同じ数列が生成される.
        assertThat(actual[index]).isEqualTo(value);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1406932606",
            "1, 654583775",
            "2, 1449466924",
            "3, 229283573",
            "4, 1109335178",
            "5, 1051550459",
            "6, 1293799192",
            "7, 794471793",
            "8, 551188310",
            "9, 803550167",
            "10, 1772930244"
    })
    void testNext(int index, int value) {
        final long seed = 12345;
        final var lcg = new LinearCongruentialGenerator(seed, ANSI_C);
        final long[] actual = new long[(int) seed];
        for (int i = 0; i < seed; i++) {
            actual[i] = lcg.next();
        }
        // LCG は 疑似乱数生成器 なので同じ seed, 同じパラメータを使うと, 常に同じ数列が生成される.
        assertThat(actual[index]).isEqualTo(value);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 3",
            "1, 11",
            "2, 5",
            "3, 3",
            "4, 7",
            "5, 4",
            "6, 5",
            "7, 6",
            "8, 3",
            "9, 3",
            "10, 9"
    })
    void testNextInt(int index, int value) {
        final long seed = 12345;
        final var lcg = new LinearCongruentialGenerator(seed, ANSI_C);
        final int[] actual = new int[(int) seed];
        for (int i = 0; i < seed; i++) {
            actual[i] = lcg.nextInt(1, 11);
        }
        // LCG は 疑似乱数生成器 なので同じ seed, 同じパラメータを使うと, 常に同じ数列が生成される.
        assertThat(actual[index]).isEqualTo(value);
    }
}
