package enemies;

import entities.Enemy;

/**
 * Factory för att skapa olika typer av fiender
 */
public class EnemyFactory {

    // === SPECIFIKA FIENDER ===
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

    // === SLUMPMÄSSIGA FIENDER ===
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

    // === FIENDER BASERAT PÅ SVÅRIGHETSGRAD ===
    public static Enemy createWeakEnemy() {
        // Lätta fiender för början av spelet
        int random = (int)(Math.random() * 2);
        switch (random) {
            case 0: return createBat();
            case 1: return createSpider();
            default: return createBat();
        }
    }

    public static Enemy createMediumEnemy() {
        // Medelsvåra fiender
        int random = (int)(Math.random() * 2);
        switch (random) {
            case 0: return createSkeleton();
            case 1: return createSpider();
            default: return createSkeleton();
        }
    }

    public static Enemy createStrongEnemy() {
        // Starka fiender för slutet
        return createPirate();
    }

    // === BOSS FIENDER ===
    public static Enemy createBossEnemy() {
        // Skapa en extra stark pirat som boss
        Pirate bossPirate = new Pirate();
        // Du kan lägga till extra hälsa eller skada här senare
        return bossPirate;
    }

    // === FIENDER FÖR SPECIFIKA OMRÅDEN ===
    public static Enemy createCaveEnemy() {
        // Fiender som passar i grottor
        int random = (int)(Math.random() * 3);
        switch (random) {
            case 0: return createBat();
            case 1: return createSpider();
            case 2: return createSkeleton();
            default: return createBat();
        }
    }

    public static Enemy createTreasureGuardian() {
        // Fiender som vaktar skatter
        int random = (int)(Math.random() * 2);
        switch (random) {
            case 0: return createSkeleton(); // Undead vakter
            case 1: return createPirate();   // Rival pirater
            default: return createSkeleton();
        }
    }
}