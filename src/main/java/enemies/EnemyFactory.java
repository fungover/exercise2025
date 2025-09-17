package enemies;

import entities.Enemy;

/**
 * Factory for creating different types of enemies
 */
public class EnemyFactory {

    // === SPECIFIC ENEMIES ===
    public static Skeleton createSkeleton() {
        return new Skeleton();
    }

    public static Spider createSpider() {
        return new Spider();
    }

    public static Pirate createPirate() {
        return new Pirate();
    }

    public static Bat createBat() {
        return new Bat();
    }

    // === RANDOM ENEMIES ===
    public static Enemy createRandomEnemy() {
        int random = (int)(Math.random() * 4); // 0-3

        switch (random) {
            case 0: return createSkeleton();
            case 1: return createSpider();
            case 2: return createBat();
            case 3: return createPirate(); // Sällsyntare
            default: return createSkeleton();
        }
    }
}