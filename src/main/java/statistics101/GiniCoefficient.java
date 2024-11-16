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

    public static void main(String[] args) {
        // 有効数字3桁
        // せっかくなので分散、標準偏差、変動係数まで求める

        int[] A = {0, 3, 3, 5, 5, 5, 5, 7, 7, 10};
        int[] B = {0, 1, 2, 3, 5, 5, 7, 8, 9, 10};
        int[] C = {3, 4, 4, 5, 5, 5, 5, 6, 6, 7};

        System.out.printf("[A] 算術平均: %.2f, 平均差: %.2f, ジニ係数: %.3f%n", avg(A), mad(A), gi(A)); // 5.00, 2.76, 0.276
        System.out.printf("[A] 分散: %.2f, 標準偏差: %.2f, 変動係数: %.3f%n", variance(A), standardDeviation(A), cv(A)); // 6.60, 2.57, 0.514
        System.out.println("--");
        System.out.printf("[B] 算術平均: %.2f, 平均差: %.2f, ジニ係数: %.3f%n", avg(B), mad(B), gi(B)); // 5.00, 3.76, 0.376
        System.out.printf("[B] 分散: %.1f, 標準偏差: %.2f, 変動係数: %.3f%n", variance(B), standardDeviation(B), cv(B)); // 10.8, 3.29, 0.657
        System.out.println("--");
        System.out.printf("[C] 算術平均: %.2f, 平均差: %.2f, ジニ係数: %.3f%n", avg(C), mad(C), gi(C)); // 5.00, 1.20, 0.120
        System.out.printf("[C] 分散: %.2f, 標準偏差: %.2f, 変動係数: %.3f%n", variance(C), standardDeviation(C), cv(C)); // 1.20, 1.10, 0.219
        System.out.println("--");


        // 今回のケースでは平均差とジニ係数が桁ずれのような形になっているが、これは偶然にすぎない
        // データセット A, B, C の平均がそれぞれ一定に5であり、分母 (2 x 5 = 10) が10と一定となった
        // 結果、たまたま桁が1つずれるような形になった


        // 別の、極端的な格差が存在する例
        int[] D = {0, 0, 0, 0, 10};
        System.out.printf("[D] 算術平均: %.2f, 平均差: %.2f, ジニ係数: %.3f%n", avg(D), mad(D), gi(D)); // 2.00, 3.20, 0.800
        // CV>1 なので、ばらつきが非常に大きい
        System.out.printf("[D] 分散: %.1f, 標準偏差: %.2f, 変動係数: %.2f%n", variance(D), standardDeviation(D), cv(D)); // 16.0, 4.00, 2.00

    }
}
