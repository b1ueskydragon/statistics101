package statistics101;

import java.util.stream.Stream;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static statistics101.RankCorrelationCoefficient.kendallsTau;

class RankCorrelationCoefficientTest {
    private static final Percentage PER = Percentage.withPercentage(1e-1);

    @ParameterizedTest
    @MethodSource
    void testKendallsTau(double[] x, double[] y, double expected) {
        assertThat(kendallsTau(x, y)).isCloseTo(expected, PER);
    }

    static Stream<Arguments> testKendallsTau() {
        return Stream.of(Arguments.of(DATA_SET_1, DATA_SET_2, 0.664));
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
            30,  // 水泳
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
}
