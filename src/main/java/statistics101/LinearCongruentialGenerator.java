package statistics101;

/**
 * LCG:
 * Xn+1 = (A * Xn + C) % M
 * <p>
 * 良いパラメータの条件:
 * (1) M は大きな数（通常2の累乗）
 * mod の性質上, M 未満の範囲でしか求められない. 結果は必ず 0 から m-1 の範囲.
 * (2) A は M より小さい
 * M より大きいところで意味がない. e.g. 9 % 8 = 1 は 1 % 8 = 1 で十分に表せる.
 * (3)C と M は互いに素
 * 互いに素でないと生成される値が特定のパターンに制限される. e.g. M=8, C=4 の場合偶数しか生成されない.
 * <p>
 * このアルゴリズムの特徴:
 * - 簡単
 * - 周期性がある（いずれ同じ数列に戻る）
 * - 品質はパラメータの選び方に依存
 * <p>
 * LCG は 疑似乱数生成器 なので同じ seed, 同じパラメータ（A, C, M）を使うと, 常に同じ数列が生成される.
 */
class LinearCongruentialGenerator {
    private long x;
    private final long a;
    private final long c;
    private final long m;

    enum Mode {
        ANSI_C,
        NUMERICAL_RECIPES,
    }

    LinearCongruentialGenerator(long seed, Mode mode) {
        this.x = seed;
        switch (mode) {
            case NUMERICAL_RECIPES -> {
                this.a = 1664525; // m との関係で良い周期性を持ち, 生成される値の分布が比較的一様
                this.c = 1013904223; // m と互いに素, 十分に大きな値
                this.m = 1L << 32; // 2^32
            }
            case ANSI_C -> {
                this.a = 1103515245;
                this.c = 12345;
                this.m = 1L << 31;
            }
            default -> throw new IllegalArgumentException(mode + " is Invalid.");
        }
    }

    // LCG は 状態を持つ 擬似乱数生成器
    // 次の乱数を生成
    long next() {
        // LCG lcg = new LCG(seed);
        // lcg.next();  // 1回目の生成
        // lcg.next();  // 2回目の生成：1回目の x を使う
        // ... のため x を更新する必要がある

        // ビットマスク
        // n が 2の累乗(2^k)のとき, x % n == x & (n - 1)
        // そのため & (m - 1) は % m と同じ.
        x = (a * x + c) & (m - 1);
        return x;
    }

    // さらに m で割って [0,1] の値を生成
    double uniformRandom() {
        return next() / (double) m;
    }

    // こちらは単に, テキストの模範解答の再現.
    // [1,11]の整数乱数を生成.
    // uniformRandom() の値 r に応じて以下の通り値を返す:
    // [0, 1/11) → 1
    // [1/11, 2/11) → 2
    // ...
    // [10/11, 1] → 11
    int nextInt1to11() {
        final int rangeInclusive = 11;
        final double r = uniformRandom(); // r は [0,1] の一様乱数
        for (int i = 0; i < rangeInclusive; i++) {
            if (r >= (double) i / rangeInclusive && r < (double) (i + 1) / rangeInclusive) {
                return i + 1;
            }
        }
        return rangeInclusive; // r = 1 の場合
    }

    // より汎用的なもの
    public int nextInt(int min, int max) {
        // 本当は next() % (max − min + 1) なのだが通常の % は負の数になる可能性があるため, floorMod に代替.
        // e.g. 3 * -2 + 1 = -5, -5 % 3 = 1
        return min + Math.floorMod(next(), (max - min + 1));
    }
}
