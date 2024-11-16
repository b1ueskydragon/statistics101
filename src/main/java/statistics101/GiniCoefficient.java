package statistics101;

public class GiniCoefficient {
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

    static double avg(int[] xs) {
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
        return normalizeDiffSum(xs, 2 * avg(xs));
    }

    // 分散
    static double variance(int[] xs) {
        final double avg = avg(xs);
        double acc = 0;
        for (int x : xs) {
            acc += Math.pow((x - avg), 2);
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
        final double avg = avg(xs);
        if (avg == 0) {
            return 0;
        }
        return standardDeviation(xs) / avg;
    }
}
