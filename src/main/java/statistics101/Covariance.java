package statistics101;

import static statistics101.DispersionCalculator.mean;

class Covariance {
    // 2 dimensions
    // データ x と y がどれだけ一緒に変動するかを示す指標
    // Cov(x,y) もしくは　Sxy と表す
    static double covariance(double[] x, double[] y) {
        final int n = x.length;
        if (n != y.length) {
            throw new IllegalArgumentException("Should be a same length");
        }
        double sxy = 0;
        final double[] deviationX = deviationFromMean(x);
        final double[] deviationY = deviationFromMean(y);
        for (int i = 0; i < n; i++) {
            sxy += (deviationX[i] * deviationY[i]);
        }
        // 平均なので n で割る
        return sxy / n;
    }

    // 平均からの偏差
    private static double[] deviationFromMean(double[] x) {
        final int n = x.length;
        final double mean = mean(x);
        double[] deviation = new double[n];
        for (int i = 0; i < n; i++) {
            deviation[i] = x[i] - mean;
        }
        return deviation;
    }
}
