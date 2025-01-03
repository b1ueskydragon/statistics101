package statistics101;

import java.util.stream.Stream;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static statistics101.RankCorrelationCoefficient.kendallsTau;
import static statistics101.RankCorrelationCoefficient.spearmansRankCc;

class RankCorrelationCoefficientTest {
    private static final Percentage PER = Percentage.withPercentage(1e-1);

    @ParameterizedTest
    @MethodSource
    void testSpearmansRankCc(double[] x, double[] y, double expected) {
        assertThat(spearmansRankCc(x, y)).isCloseTo(expected, PER);
    }

    static Stream<Arguments> testSpearmansRankCc() {
        return Stream.of(
                Arguments.of(DATA_SET_1, DATA_SET_1, 1),
                Arguments.of(DATA_SET_1, DATA_SET_2, 0.822),
                Arguments.of(DATA_SET_1, DATA_SET_3, 0.927),
                Arguments.of(DATA_SET_1, DATA_SET_4, 0.593),
                Arguments.of(DATA_SET_2, DATA_SET_2, 1),
                Arguments.of(DATA_SET_2, DATA_SET_3, 0.672),
                Arguments.of(DATA_SET_2, DATA_SET_4, 0.637),
                Arguments.of(DATA_SET_3, DATA_SET_3, 1),
                Arguments.of(DATA_SET_3, DATA_SET_4, 0.534),
                Arguments.of(DATA_SET_4, DATA_SET_4, 1)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testKendallsTau(double[] x, double[] y, double expected) {
        assertThat(kendallsTau(x, y)).isCloseTo(expected, PER);
    }

    static Stream<Arguments> testKendallsTau() {
        return Stream.of(
                Arguments.of(DATA_SET_1, DATA_SET_1, 1),
                Arguments.of(DATA_SET_1, DATA_SET_2, 0.664),
                Arguments.of(DATA_SET_1, DATA_SET_3, 0.768), //テキストの解は 0.766
                Arguments.of(DATA_SET_1, DATA_SET_4, 0.439),
                Arguments.of(DATA_SET_2, DATA_SET_2, 1),
                Arguments.of(DATA_SET_2, DATA_SET_3, 0.502), // テキストの解は 0.499
                Arguments.of(DATA_SET_2, DATA_SET_4, 0.462),
                Arguments.of(DATA_SET_3, DATA_SET_3, 1),
                Arguments.of(DATA_SET_3, DATA_SET_4, 0.359),
                Arguments.of(DATA_SET_4, DATA_SET_4, 1)
        );
    }

    // 女性有権者団体 (1) の Social Risk 順位評価
    private static final double[] DATA_SET_1 = {
            1,  // 原子力
            2,  // 自動車
            3,  // 銃
            4,  // 喫煙
            5,  // バイク
            6,  // アルコール飲料
            7,  // 自家用飛行機
            8,  // 警察職務
            9,  // 殺虫剤
            10, // 外科手術
            11, // 消防職務
            12, // 大規模建設工事
            13, // 狩猟
            14, // スプレー
            15, // 登山
            16, // 自転車
            17, // 飛行機
            18, // 電気
            19, // 水泳
            20, // 避妊ピル
            21, // スキー
            22, // X線
            23, // フットボール
            24, // 鉄道
            25, // 食品添加物
            26, // 食品着色料
            27, // 自動芝刈機
            28, // 抗生物質
            29, // 家庭用具
            30  // 予防注射
    };

    // 短期大学生 (2) の Social Risk 順位評価
    private static final double[] DATA_SET_2 = {
            1,  // 原子力
            5,  // 自動車
            2,  // 銃
            3,  // 喫煙
            6,  // バイク
            7,  // アルコール飲料
            15, // 自家用飛行機
            8,  // 警察職務
            4,  // 殺虫剤
            11, // 外科手術
            10, // 消防職務
            14, // 大規模建設工事
            18, // 狩猟
            13, // スプレー
            22, // 登山
            24, // 自転車
            16, // 飛行機
            19, // 電気
            30, // 水泳
            9, // 避妊ピル
            25, // スキー
            17, // X線
            26, // フットボール
            23, // 鉄道
            12, // 食品添加物
            20, // 食品着色料
            28, // 自動芝刈機
            21, // 抗生物質
            27, // 家庭用具
            29  // 予防注射
    };

    // 経営者団体 (3) の Social Risk 順位評価
    // Note: タイ (22, 22) あり
    private static final double[] DATA_SET_3 = {
            8, 3, 1, 4, 2, 5, 11, 7, 15, 9, 6, 13, 10, 22, 12, 14,
            18, 19, 17, 22, 16, 24, 21, 20, 28, 30, 25, 26, 27, 29
    };

    // 大学教授, 研究者, 専門職等 (4) の Social Risk 順位評価
    private static final double[] DATA_SET_4 = {
            20, 1, 4, 2, 6, 3, 12, 17, 8, 5, 18, 13, 23, 26, 29,
            15, 16, 9, 10, 11, 30, 7, 27, 19, 14, 21, 28, 24, 22, 25
    };
}
