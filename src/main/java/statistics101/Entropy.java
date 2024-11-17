package statistics101;

import java.util.Map;

class Entropy {
    /**
     * @param p probabilities (確率 or 相対度数 or 相対頻度）の配列
     * @return entropy
     */
    static double entropy(double[] p) {
        double acc = 0;
        for (int i = 0; i < p.length; i++) {
            // information content (情報量)
            // そのカテゴリがもたらすインパクト (情報量)
            // 「扱いやすい」値にするため対数をとり
            // 「小さい」ものを「大きい」と表現するために符号を反転する
            //  確率 p[i] が小さいほど、起こった時のインパクト -log(p[i]) が強い
            double information = -1 * Math.log10(p[i]);

            // weighting (重み付け)
            // p[i] は確率でありながら、全体への寄与度 or 重み
            double weight = p[i];

            // 正しく重み付けされた各カテゴリの情報量
            double weightedInformation = weight * information;
            acc += weightedInformation;
        }
        return acc;
    }

    /**
     * @param rawData 階級値 -> 度数 のペア
     * @return 相対度数 p[i] の配列. p[i] = f[i] / n
     */
    static double[] relativeFrequencies(Map<String, Double> rawData) {
        // 絶対度数だけ取り出した
        // 今回階級値は使わない(便宜上 String)
        Iterable<Double> f = rawData.values();

        // 度数を全て足した結果. e.g. 100人対象の調査なら100
        double n = sum(f);
        // カテゴリ(階級)数
        int k = rawData.size();
        // 相対度数(相対頻度) はつまり確率である
        double[] p = new double[k];

        var it = f.iterator();
        for (int i = 0; i < k; i++) {
            // hasNext 確認しなくても階級数だけ(k回) loop するので大丈夫
            double fi = it.next();
            p[i] = fi / n;
        }

        return p;
    }

    private static double sum(Iterable<Double> xs) {
        double acc = 0;
        for (var x : xs) {
            acc += x;
        }
        return acc;
    }
}
