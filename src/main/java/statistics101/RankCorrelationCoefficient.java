package statistics101;

class RankCorrelationCoefficient {
    // TODO: Spearman’s Rank Correlation Coefficient (r_s)

    /**
     * Note: 通常のピアソン相関係数 (r_xy) と違って「順位相関係数」なので, Kendall's Tau を二乗して決定係数としては使えない.
     * <p>
     * Kendall's Tau (r_k)
     * <p>
     * Formula:
     * (G-H) / (n(n-1)/2)
     * <p>
     * G: The number of concordant pairs (pairs that are ranked in the same order in both groups).
     * H: The number of discordant pairs (pairs ranked in opposite orders).
     * The denominator is the total number of pairs, nC2, which represents all possible pair combinations in the dataset.
     *
     * @param x dataset x
     * @param y dataset y
     * @return r_k
     */
    static double kendallsTau(double[] x, double[] y) {
        final double n = x.length;
        if (n != y.length) {
            throw new IllegalArgumentException("Arrays must have the same length");
        }
        double gh = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                // どちらか一方でも同順位(タイ)があった場合は G や H として数えない
                // 理由: タイのペアは順位の明確な順序を持たないため
                //
                // Note: 厳密には Kendall's Tau-b を使った方が精度が高い
                // τ = (G - H) / √((G + H + T_x)(G + H + T_y))
                // T_x: x 内のタイのペア数, T_y: y 内のタイのペア数
                // Tau-b のタイ処理を将来的に実装する場合, この部分を修正する
                if (x[j] == x[i] || y[j] == y[i]) {
                    continue;
                }
                if (x[j] - x[i] > 0 && y[j] - y[i] > 0) {
                    gh += 1;
                } else {
                    gh -= 1;
                }
            }
        }
        return gh / (n * (n - 1) / 2);
    }
}
