package items;

import entities.Item;

/**
 * Factory class for creating pirate treasures and items
 */
public class PirateTreasureFactory {

    // === GOLD COIN ===
    public static GoldCoin createSmallGoldCoin() {
        return new GoldCoin(10);
    }

    public static GoldCoin createLargeGoldCoin() {
        return new GoldCoin(25);
    }

    public static GoldCoin createRareGoldCoin() {
        return new GoldCoin(50);
    }

    // === MAGIC KEY ===
    public static MagicKey createMagicKey() {
        return new MagicKey();
    }

    // === RUM BOTTLES ===
    public static RumBottle createWeakRum() {
        return new RumBottle("svag", 20);
    }

    public static RumBottle createStrongRum() {
        return new RumBottle("stark", 40);
    }

    // === PIRATE TREASURES ===
    public static PirateTreasure createJewelBox() {
        return new PirateTreasure(
                "Juvelskrin",
                "Ett litet skrin fyllt med gnistrande ädelstenar och pärlor",
                100
        );
    }

    public static PirateTreasure createGoldenChalice() {
        return new PirateTreasure(
                "Gyllene kalk",
                "En massiv guldkalk incrusterad med rubiner",
                150
        );
    }

    public static PirateTreasure createTreasureMap() {
        return new PirateTreasure(
                "Skattekarta",
                "En gammal pergamentrulle som visar vägen till en begravd skatt",
                200
        );
    }

    // === OLD SWORDS ===
    public static OldSword createRustySword() {
        return new OldSword("Rostigt svärd", 5);
    }

    public static OldSword createPirateSaber() {
        return new OldSword("Piratsabel", 12);
    }

    // === RANDOM ITEMS ===
    public static Item createRandomTreasure() {
        int random = (int)(Math.random() * 12); // 0-11

        switch (random) {
            case 0: return createSmallGoldCoin();
            case 1: return createLargeGoldCoin();
            case 2: return createWeakRum();
            case 3: return createStrongRum();
            case 4: return createJewelBox();
            case 5: return createGoldenChalice();
            case 6: return createRustySword();
            case 7: return createPirateSaber();
            case 8: return createWeakRum(); // Rom är vanligare
            case 9: return createSmallGoldCoin(); // Guldmynt är vanligare
            case 10: return createMagicKey(); // Sällsynt!
            case 11: return createTreasureMap(); // Mycket sällsynt!
            default: return createSmallGoldCoin();
        }
    }
}
