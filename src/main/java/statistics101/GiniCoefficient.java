package statistics101;

public class GiniCoefficient {
    private static double diffSum(int[] xs) {
        final int n = xs.length;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                sum += Math.abs(xs[i] - xs[j]); // 差を片方向で計算
            }
        }
        return sum * 2; // 対称性を考慮
    }

    private static double avg(int[] xs) {
        double acc = 0;
        for (int x : xs) {
            acc += x;
        }
        return acc / xs.length;
    }

    private static double normalizeDiffSum(int[] xs, double scaleFactor) {
        int n = xs.length;
        return diffSum(xs) / (n * n * scaleFactor);
    }

    // 平均差 (mean absolute difference)
    private static double mad(int[] xs) {
        return normalizeDiffSum(xs, 1);
    }

    // ジニ係数 Gini coefficient
    private static double gi(int[] xs) {
        return normalizeDiffSum(xs, 2 * avg(xs));
    }

    public static void main(String[] args) {
        // 有効数字3桁

        int[] A = {0, 3, 3, 5, 5, 5, 5, 7, 7, 10};
        int[] B = {0, 1, 2, 3, 5, 5, 7, 8, 9, 10};
        int[] C = {3, 4, 4, 5, 5, 5, 5, 6, 6, 7};

        System.out.printf("[A] 平均差: %.2f, ジニ係数: %.3f%n", mad(A), gi(A)); // 2.76, 0.276
        System.out.printf("[B] 平均差: %.2f, ジニ係数: %.3f%n", mad(B), gi(B)); // 3.76, 0.376
        System.out.printf("[C] 平均差: %.2f, ジニ係数: %.3f%n", mad(C), gi(C)); // 1.20, 0.120

        // 今回のケースでは平均差とジニ係数が桁ずれのような形になっているが、これは偶然にすぎない
        // データセット A, B, C の平均がそれぞれ一定に5であり、分母 (2 x 5 = 10) が10と一定となった
        // 結果、たまたま桁が1つずれるような形になった
    }
}
