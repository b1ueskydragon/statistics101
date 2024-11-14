package statistics101;

public class GiniCoefficient {
    private static double diffSum(int[] xs) {
        final int n = xs.length;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                sum += Math.abs(xs[i] - xs[j]);
            }
        }
        sum *= 2;
        return sum;
    }

    private static double avg(int[] xs) {
        double acc = 0;
        for (int x : xs) {
            acc += x;
        }
        return acc / xs.length;
    }

    // mean absolute difference
    private static double mad(int[] xs) {
        int n = xs.length;
        return diffSum(xs) / (n * n);
    }

    // Gini coefficient
    private static double gi(int[] xs) {
        int n = xs.length;
        return diffSum(xs) / (n * n * 2 * avg(xs));
    }

    public static void main(String[] args) {
        // 有効数字3桁

        int[] A = {0, 3, 3, 5, 5, 5, 5, 7, 7, 10};
        System.out.println("[A] 平均差: " + mad(A)); // 2.76
        System.out.println("[A] ジニ係数: " + gi(A)); // 0.276

        int[] B = {0, 1, 2, 3, 5, 5, 7, 8, 9, 10};
        System.out.println("[B] 平均差: " + mad(B)); // 3.76
        System.out.println("[B] ジニ係数: " + gi(B)); // 0.376

        int[] C = {3, 4, 4, 5, 5, 5, 5, 6, 6, 7};
        System.out.println("[C] 平均差: " + mad(C)); // 1.20
        System.out.println("[C] ジニ係数: " + gi(C)); // 0.120
    }
}
