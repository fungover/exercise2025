package items;

import entities.Item;

/**
 * Factory-klass för att skapa piratskatter och föremål
 * Detta gör det enkelt att skapa standardföremål för ditt piratspel
 */
public class PirateTreasureFactory {

    // === GULDMYNT ===
    public static GoldCoin createSmallGoldCoin() {
        return new GoldCoin(10);
    }

    public static GoldCoin createLargeGoldCoin() {
        return new GoldCoin(25);
    }

    public static GoldCoin createRareGoldCoin() {
        return new GoldCoin(50);
    }

    // === MAGISK NYCKEL ===
    public static MagicKey createMagicKey() {
        return new MagicKey();
    }

    // === ROM-FLASKOR (istället för health potions) ===
    public static RumBottle createWeakRum() {
        return new RumBottle("svag", 20);
    }

    public static RumBottle createStrongRum() {
        return new RumBottle("stark", 40);
    }

    public static RumBottle createCaptainsRum() {
        return new RumBottle("kaptenens", 75);
    }

    // === PIRATSKATTER ===
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

    public static PirateTreasure createCaptainsMedallion() {
        return new PirateTreasure(
                "Kaptenens medaljong",
                "En tung guldmedaljong med en döskalle och korslagda ben",
                300
        );
    }

    // === GAMLA SVÄRD ===
    public static OldSword createRustySword() {
        return new OldSword("Rostigt svärd", 5);
    }

    public static OldSword createPirateSaber() {
        return new OldSword("Piratsabel", 12);
    }

    public static OldSword createCaptainsCutlass() {
        return new OldSword("Kaptenens enterkryssare", 20);
    }

    public static OldSword createLegendaryCutlass() {
        return new OldSword("Legendarisk kaparsabel", 35);
    }

    // === SLUMPMÄSSIGT FÖREMÅL ===
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

    // === FÖREMÅL FÖR SPECIFIKA PLATSER ===
    public static Item createBossRoomTreasure() {
        // Extra bra föremål för att besegra en boss
        int random = (int)(Math.random() * 3);
        switch (random) {
            case 0: return createCaptainsCutlass();
            case 1: return createCaptainsMedallion();
            case 2: return createLegendaryCutlass();
            default: return createCaptainsCutlass();
        }
    }

    public static Item createSecretRoomTreasure() {
        // Hemliga rum har alltid bra saker
        int random = (int)(Math.random() * 4);
        switch (random) {
            case 0: return createMagicKey();
            case 1: return createTreasureMap();
            case 2: return createCaptainsRum();
            case 3: return createRareGoldCoin();
            default: return createMagicKey();
        }
    }
}
