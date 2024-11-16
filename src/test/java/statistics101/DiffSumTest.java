package statistics101;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static statistics101.DiffSum.version0;
import static statistics101.DiffSum.version1;
import static statistics101.DiffSum.version2;
import static statistics101.DiffSum.version3;

class DiffSumTest {
    @Test
    void dataSet4Len() {
        final int[] xs = {5, 2, 9, 8};

        assertThat(version0(xs))
                .isEqualTo(version1(xs))
                .isEqualTo(version2(xs))
                .isEqualTo(version3(xs))
                .isEqualTo(48);
    }

    @Test
    void dataSet5Len() {
        final int[] xs = {5, 9, 10, 8, 2};

        assertThat(version0(xs))
                .isEqualTo(version1(xs))
                .isEqualTo(version2(xs))
                .isEqualTo(version3(xs))
                .isEqualTo(80);
    }
}
