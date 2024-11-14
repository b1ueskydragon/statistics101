package graffiti2024;

import java.util.Arrays;

// The sum of the absolute differences of all possible combinations.
// |xi - xj| の総当たり sum を求める
class DiffSum {
    // 元のシグマで表現された数式に充実
    // O(N^2)
    static int version0(int[] xs) {
        int n = xs.length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sum += Math.abs(xs[i] - xs[j]);
            }
        }
        return sum;
    }

    // (1) 自己差の排除 (xs[i] - xs[i] == 0 のため）
    // (2) 対称性を考慮して
    //      - 合計を2倍する
    //      - j = i + 1 から始める
    // 結局 O(N^2) だけど、内側のループは n(n - 1) / 2 回実行なので version0 よりは効率的
    static int version1(int[] xs) {
        int n = xs.length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                sum += Math.abs(xs[i] - xs[j]);
            }
        }
        sum *= 2;
        return sum;
    }

    // データをソート済みと仮定
    // データによってはソートしてはいけないのと(元データの順序が重要な場合)
    // ソートの計算量がかかる
    // ソート込みで max(O(n log n), O(n)), O(N log N)
    //
    // ソートされているため：
    //   - インデックスが小さい → より多く引かれる
    //   - インデックスが大きい → より多く足される
    //   - i番目の値は、i回足されて(n-i-1)回引かれる
    //
    // e.g.
    //  xs=[2,5,8,9] なら、sum*=2をする前の段階で
    //  2 : 0回足す、3回引く
    //  5 : 1回足す、2回引く
    //  8 : 2回足す、1回引く
    //  9 : 3回足す、0回引く
    static int version2(int[] rawXs) {
        int[] xs = rawXs.clone();
        // クイックソート or マージソートでソート
        Arrays.sort(xs);

        int n = xs.length;
        var sum = 0;
        var cumSum = 0; // 一番左の要素は早めに積まれて毎度引かれる
        for (int i = 0; i < n; i++) {
            // 一番右の要素は後から大きい i によって一気にたくさん足される
            sum += i * xs[i] - cumSum;
            cumSum += xs[i];
        }
        sum *= 2;
        return sum;
    }

    static int version3(int[] rawXs) {
        int[] xs = rawXs.clone();
        // クイックソート or マージソートでソート
        Arrays.sort(xs);

        int n = xs.length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            // xs[i] は i 回足され      : 自分より小さい数との差分で足される回数
            //         n-i-1 回引かれる : 自分より大きい数との差分で引かれる回数
            sum += xs[i] * i - xs[i] * (n - i - 1);
        }
        sum *= 2;
        return sum;
    }

    public static void main(String[] args) {
        final int[] xs = {5, 2, 9, 8};

        System.out.println(version0(xs));
        System.out.println(version1(xs));
        System.out.println(version2(xs));
        System.out.println(version3(xs));
    }
}
