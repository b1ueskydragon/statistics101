package statistics101;

import java.util.Arrays;

class DispersionCalculator {
    static double diffSum(double[] x) {
        final int n = x.length;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                sum += Math.abs(x[i] - x[j]); // 差を片方向で計算
            }
        }
        return sum * 2; // 対称性を考慮
    }

    // 単純な総和
    // 浮動小数点演算による誤差があり得るので単純な for-loop は避ける
    private static double sum(double[] x) {
        return Arrays.stream(x).sum();
    }

    // 算術平均
    static double mean(double[] x) {
        return sum(x) / x.length;
    }

    static double normalizeDiffSum(double[] x, double scaleFactor) {
        final int n = x.length;
        return diffSum(x) / (n * n * scaleFactor);
    }

    // 平均差 (mean absolute difference)
    static double mad(double[] x) {
        return normalizeDiffSum(x, 1);
    }

    // ジニ係数 Gini coefficient
    static double gi(double[] x) {
        return normalizeDiffSum(x, 2 * mean(x));
    }

    // 分散
    static double variance(double[] x) {
        final double mean = mean(x);
        double acc = 0;
        for (double xi : x) {
            acc += Math.pow((xi - mean), 2);
        }
        final int n = x.length;
        return acc / n;
    }

    // 標準偏差
    static double standardDeviation(double[] x) {
        return Math.sqrt(variance(x));
    }

    // 変動係数 (coefficient of variation)
    static double cv(double[] x) {
        final double mean = mean(x);
        if (mean == 0) {
            return 0;
        }
        return standardDeviation(x) / mean;
    }

    // 標準化 (標準得点 or zスコア)
    // z[i] = (x[i] - 平均) / 標準偏差
    static double[] toZscore(double[] x) {
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
        if (sum(z) != 0) {
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
