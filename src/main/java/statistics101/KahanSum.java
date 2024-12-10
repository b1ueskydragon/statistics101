package statistics101;

import java.util.Arrays;

/**
 * Kahan Sum のゴール:
 * - 足し算における丸め誤差の補正
 * - 足し算で生じる丸め誤差に特化したアルゴリズム
 * <p>
 * java の double 型は 64ビットの浮動小数点
 * IEEE754仕様で 52 + 1 ビットが実際の有効数字であり
 * 10進数表現の有効数字だと
 * 1og(2^53) = 15.954.. 約15桁に相当する
 * <p>
 * ちなみに
 * - 丸め誤差(Rounding Error): 計算途中「小さすぎる」値が失われる. 一応計算は続行できる
 * - 桁溢れ(Overflow): 計算途中桁が足りず, 計算続行できない
 * - Kahan-sum も Overflow, Underflow には対応しない
 */
public class KahanSum {

    private static final String FORMAT = "%.16f%n";

    static double kahanSum(double[] x) {
        // ゴールとする合計
        double sum = 0;
        // 補正値: A running compensation for lost low-order bits
        double c = 0;
        for (double xi : x) {
            // y は補正後の値
            // c は前回の補正
            //
            // なぜ引き算をするのか?
            // - 補正自体が丸められることを防げる
            // - 符号を反転させた引き算によって, 丸められた分を正しく補える
            // - 引き算を使うことで, 丸め誤差を「逆向き」に記録
            // - 次の計算で, その逆向きの値を引くことで (実質的に足すことで) 補正
            //
            // ちなみに足し算で保証すると...
            // - 補正値 c を足すとまた丸め誤差が発生し, 補正の意味が失われる
            // - 特に小さい値（c）を大きな値（xi）に足すと、小さい値が再び無視されてしまう
            double y = xi - c;

            // 仮の和. 丸め誤差が発生する恐れがある
            // sum + y が桁から溢れる場合. y は失われる（丸められる）
            // これまでの sum = 1.0,  y = 1e-16 の例だと
            // 1e-16 の表現には問題ない(重要), 1.0 + 1e-16の演算には有効数字で表現できず
            // 結果が丸められる
            // 計算後は 1e-16 が失われ t = 1.0 となる
            double t = sum + y;

            // 補正値の更新
            // 失われた値の「逆」(符号反転値) を補正値として保持
            // 次の loop で失われた分を引き算することで足し戻す
            // これまでの sum = 1.0,  y = 1e-16 の例だと, 失われた 1e-16が -1e-16 として保持され
            // 次の loop で -(-1e-16) することで　+1e-16 として補うことが実現できる
            c = (t - sum) - y;

            // 合計値の更新
            sum = t;
        }
        System.out.printf("Kahan-sum: " + FORMAT, sum);
        return sum;
    }

    // stream#sum は比較的正確
    static double streamSum(double[] x) {
        double sum = Arrays.stream(x).sum();
        System.out.printf("stream-sum: " + FORMAT, sum);
        return sum;
    }

    static double sumWithoutCompensation(double[] x) {
        double sum = 0;
        for (double xi : x) sum += xi;
        System.out.printf("plain-for-loop-sum: " + FORMAT, sum);
        return sum;
    }
}
