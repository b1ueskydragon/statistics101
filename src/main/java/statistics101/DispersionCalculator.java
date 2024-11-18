package statistics101;

import java.util.Arrays;

class DispersionCalculator {
    static double diffSum(int[] xs) {
        final int n = xs.length;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                sum += Math.abs(xs[i] - xs[j]); // 差を片方向で計算
            }
        }
        return sum * 2; // 対称性を考慮
    }

    // 算術平均
    static double mean(int[] xs) {
        double acc = 0;
        for (int x : xs) {
            acc += x;
        }
        return acc / xs.length;
    }

    static double normalizeDiffSum(int[] xs, double scaleFactor) {
        int n = xs.length;
        return diffSum(xs) / (n * n * scaleFactor);
    }

    // 平均差 (mean absolute difference)
    static double mad(int[] xs) {
        return normalizeDiffSum(xs, 1);
    }

    // ジニ係数 Gini coefficient
    static double gi(int[] xs) {
        return normalizeDiffSum(xs, 2 * mean(xs));
    }

    // 分散
    static double variance(int[] xs) {
        final double mean = mean(xs);
        double acc = 0;
        for (int x : xs) {
            acc += Math.pow((x - mean), 2);
        }
        final int n = xs.length;
        return acc / n;
    }

    // 標準偏差
    static double standardDeviation(int[] xs) {
        return Math.sqrt(variance(xs));
    }

    // 変動係数 (coefficient of variation)
    static double cv(int[] xs) {
        final double mean = mean(xs);
        if (mean == 0) {
            return 0;
        }
        return standardDeviation(xs) / mean;
    }

    // 標準化 (標準得点 or zスコア)
    // z[i] = (x[i] - 平均) / 標準偏差
    static double[] toZscore(int[] x) {
        final int n = x.length;
        // 配列xを配列zに変換(標準化)するイメージ
        final double[] z = new double[n];
        final double mean = mean(x);
        final double Sx = standardDeviation(x);
        for (int i = 0; i < n; i++) {
            z[i] = (x[i] - mean) / Sx;
        }
        // 全ての z[i] を足し合わせた結果は0.
        // 算術平均の特徴, 元データxにおいて (x[i] - x̄) の和が 0であるため
        if (Arrays.stream(z).sum() != 0) {
            throw new IllegalStateException("z-scores must sum to 0");
        }
        return z;
    }

    // 偏差値得点 (tスコア)
    // zスコア元にさらにtスコアとして調整する
    // t[i] = 10 * z[i] + 50
    static double[] toTscore(double[] z) {
        final int n = z.length;
        final double[] t = new double[n];
        for (int i = 0; i < n; i++) {
            t[i] = 10 * z[i] + 50;
        }
        return t;
    }
}
