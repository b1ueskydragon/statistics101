package statistics101;

import java.util.HashMap;
import java.util.Map;

/**
 * スピアマン: 順位差の二乗を使用
 * ケンドール: ペアごとの順序関係を比較
 * <p>
 * 一般的に,
 * |r_s| ≥ |τ|
 * スピアマン係数(r_s) ≈ 1.5 × ケンドール係数(τ もしくは r_k)
 * <p>
 * Note:
 * どちらも通常のピアソン相関係数 (r_xy) と違って順位相関係数である
 * - スピアマンの順位相関係数は, 線形モデルの説明力を示す指標ではないため決定係数として使えない.
 * - Kendall's Tauも, 二乗して決定係数としては使えない.
 */
class RankCorrelationCoefficient {
    /**
     * Spearman's Rank Correlation Coefficient (r_s)
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
     * τ = (G - H) / √((nC2 - T)(nC2 - U))
     * T: Number of pairs of tied values in the dataset x (nC2 - T: x での実際に比較可能なペア数)
     * U: Number of pairs of tied values in the dataset y (nC2 - U: y での実際に比較可能なペア数)
     * <p>
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
        int G_H = 0;
        double T = computeTieCorrection(x);
        double U = computeTieCorrection(y);

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (x[j] == x[i] || y[j] == y[i]) {
                    continue;
                }
                // 以下は、Tau-a, Tau-b 関係なくタイがある場合は数えない
                // タイであるペアは意味がないため
                if ((x[j] - x[i] > 0 && y[j] - y[i] > 0) ||
                        (x[j] - x[i] < 0 && y[j] - y[i] < 0)) {
                    G_H++;
                } else {
                    G_H--;
                }
            }
        }
        final double allPairs = n * (n - 1) / 2;
        return G_H / Math.sqrt((allPairs - T) * (allPairs - U));
    }

    /**
     * Calculates the tie correction for a given dataset.
     * <p>
     * T = Σ(t_iC2)
     * t_i is the frequency of the tied value in the dataset.
     * <p>
     * e.g.
     * DataSet: [...., V, V, ...,V ....] (各 "V" の indices: i, j, k とする)
     * Frequency : 3,
     * (_, _) <--- i, j, k で作れる２ペア組み合わせの数 : 3C2 = (3 * 2) / (2 * 1)
     */
    private static double computeTieCorrection(double[] dataSet) {
        final Map<Double, Integer> frequencyMap = new HashMap<>();
        for (var data : dataSet) {
            frequencyMap.put(data, frequencyMap.getOrDefault(data, 0) + 1);
        }
        double acc = 0;
        for (var freq : frequencyMap.values()) {
            if (freq > 1) {
                acc += (freq * (freq - 1)) / 2.0;
            }
        }
        return acc;
    }
}
