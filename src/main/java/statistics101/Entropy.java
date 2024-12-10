package statistics101;

import java.util.Arrays;
import java.util.Map;

import static statistics101.DispersionCalculator.sum;

class Entropy {
    /**
     * @param p probabilities (確率 or 相対度数 or 相対頻度）の配列
     * @return entropy
     */
    static double entropy(double[] p) {
        // 扱うものは確率なので p の要素を全て足してピッタリ1になるべき
        if (Arrays.stream(p).sum() != 1) {
            throw new IllegalArgumentException("Probabilities must sum to 1");
        }
        double acc = 0;
        for (int i = 0; i < p.length; i++) {
            // 扱うものは確率なので負の値はありえない
            if (p[i] < 0) {
                throw new IllegalArgumentException("Probabilities must be greater than 0");
            }
            // information content (情報量)
            // そのカテゴリがもたらすインパクト (情報量)
            // 「扱いやすい」値にするため対数をとり
            // 「小さい」ものを「大きい」と表現するために符号を反転する
            //  確率 p[i] が小さいほど、起こった時のインパクト -log(p[i]) が強い
            double information = -1 * safeLog10(p[i]);

            // weighting (重み付け)
            // p[i] は確率でありながら、全体への寄与度 or 重み
            double weight = p[i];

            // 正しく重み付けされた各カテゴリの情報量
            double weightedInformation = weight * information;
            acc += weightedInformation;
        }
        return acc;
    }

    // 注意 !!
    //  - 通常 log0 は定義されないが、ここでは0と定義する
    //  - カテゴリが存在しない場合、そのカテゴリはエントロピーに寄与しないことを意味
    private static double safeLog10(double d) {
        return (d == 0) ? 0 : Math.log10(d);
    }

    /**
     * @param rawData 階級値 -> 度数 のペア
     * @return 相対度数 p[i] の配列. p[i] = f[i] / n
     */
    static double[] relativeFrequencies(Map<String, Double> rawData) {
        // 絶対度数だけ取り出した
        // 今回階級値は使わない(便宜上 String)
        final double[] f = rawData.values().stream().mapToDouble(d -> d).toArray();

        // 度数を全て足した結果. e.g. 100人対象の調査なら100
        double n = sum(f);
        // カテゴリ(階級)数
        int k = rawData.size();
        // 相対度数(相対頻度) はつまり確率である
        double[] p = new double[k];

        // 階級数だけ(k回) loop する
        for (int i = 0; i < k; i++) {
            double fi = f[i];
            p[i] = fi / n;
        }

        return p;
    }
}
