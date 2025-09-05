package Enemy;

import java.util.Random;

public final class EnemyRandom {
    private static final Random RNG = new Random();

    private EnemyRandom() {}

    public static Enemy randomAt(int x, int y) {
        int roll = RNG.nextInt(3);
        return switch (roll) {
            case 0 -> new Spider(x, y);
            case 1 -> new Skeleton(x, y);
            case 2 -> new Dragon(x, y);
            default -> new Spider(x, y);
        };
    }
}
