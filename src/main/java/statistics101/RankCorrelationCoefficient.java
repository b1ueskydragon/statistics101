package statistics101;

/**
 * スピアマン: 順位差の二乗を使用
 * ケンドール: ペアごとの順序関係を比較
 * <p>
 * 一般的に,
 * |rs| ≥ |τ|
 * スピアマン係数(r_s) ≈ 1.5 × ケンドール係数(τ もしくは r_k)
 */
class RankCorrelationCoefficient {
    /**
     * Spearman's Rank Correlation Coefficient
     * <p>
     * Formula:
     * r_s = 1 - (6Σ(d_i)²) / (n(n²-1))
     * where d_i is the difference between the ranks of corresponding elements in the two groups,
     * and n is the total number of elements.
     *
     * @param x dataset x
     * @param y dataset y
     * @return r_s
     */
    static double spearmansRankCc(double[] x, double[] y) {
        final double n = x.length;
        if (n != y.length) {
            throw new IllegalArgumentException("Arrays must have the same length");
        }
        double acc = 0;
        for (int i = 0; i < n; i++) {
            acc += Math.pow(x[i] - y[i], 2);
        }
        return 1 - (6 * acc / (Math.pow(n, 3) - n));
    }

    /**
     * Note: 通常のピアソン相関係数 (r_xy) と違って「順位相関係数」なので, Kendall's Tau を二乗して決定係数としては使えない.
     * <p>
     * Kendall's Tau (r_k)
     * <p>
     * Kendall's Tau-a
     * Formula:
     * τ = (G-H) / (n(n-1)/2)
     * <p>
     * G: The number of concordant pairs (pairs that are ranked in the same order in both groups).
     * H: The number of discordant pairs (pairs ranked in opposite orders).
     * The denominator is the total number of pairs, nC2, which represents all possible pair combinations in the dataset.
     * <p>
     * 一方, タイ補正をいれる場合,
     * Kendall's Tau-b
     * Formula:
     * τ = (G - H) / √((G + H + T_x)(G + H + T_y))
     * T_x: x 内のタイのペア数
     * T_y: y 内のタイのペア数
     * <p>
     * つまり,
     * G + H + T_x: dataset x の可能なペアの総数
     * G + H + T_y: dataset y の可能なペアの総数
     * 対して tau-a は,
     * タイの影響を考慮せず理論上の最大ペア数(nC2)で単純に割る.
     * <p>
     * ちなみに分母において幾何平均を使う理由は, 両者のスケールを均一化し、バランスを取るため.
     * 例えば T_x と T_y が極端的に違う場合, 算術平均を使うと片方のタイの影響が過剰に反映される.
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
        double G = 0;
        double H = 0;
        double Tx = 0;
        double Ty = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (x[j] == x[i]) {
                    Tx++;
                }
                if (y[j] == y[i]) {
                    Ty++;
                }
                if (x[j] == x[i] || y[j] == y[i]) {
                    continue;
                }
                // 以下は、Tau-a, Tau-b 関係なくタイがある場合は数えない
                if ((x[j] - x[i] > 0 && y[j] - y[i] > 0) ||
                        (x[j] - x[i] < 0 && y[j] - y[i] < 0)) {
                    G++;
                } else {
                    H++;
                }
            }
        }
        return (G - H) / Math.sqrt((G + H + Tx) * (G + H + Ty));
    }
}
