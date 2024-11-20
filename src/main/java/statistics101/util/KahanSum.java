package statistics101.util;

/**
 * java の double 型は 64ビットの浮動小数点
 * IEEE754仕様で 52 + 1 ビットが実際の有効数字であり
 * 10進数表現の有効数字だと
 * 1og(2^53) = 15.954.. 約15桁に相当する
 * <p>
 * ちなみに
 * - 丸め誤差(Rounding Error): 計算途中「小さすぎる」値が失われる. 一応計算は続行できる
 * - 桁溢れ(Overflow): 計算途中桁が足りず、計算続行できない
 * - Kahan-sum も Overflow, Underflow には対応しない
 */
public class KahanSum {
    static double kahanSum(double[] x) {
        // ゴールとする合計
        double sum = 0;
        // 補正値: A running compensation for lost low-order bits
        double c = 0;
        for (double xi : x) {
            // 補正後の値
            // c は前回の補正
            double y = xi - c;

            // 仮の和. 丸め誤差が発生する恐れがある
            // sum + y が桁から溢れる場合. y は失われる（丸められる）
            // これまでの sum = 1.0,  y = 1e-16 の例だと
            // 1e-16 の表現には問題ない(重要)、1.0 + 1e-16の演算には有効数字で表現できず
            // 結果が丸められる
            // 計算後は 1e-16 が失われ t = 1.0 となる
            double t = sum + y;

            // 補正値の更新
            c = (t - sum) - y;

            // 合計値の更新
            sum = t;
        }
        System.out.printf("Kahan-sum: %.16f%n", sum);
        return sum;
    }

    static double plainForLoopSum(double[] x) {
        double sum = 0;
        for (double xi : x) {
            sum += xi;
        }
        System.out.printf("plain-for-loop-sum: %.16f%n", sum);
        return sum;
    }
}
