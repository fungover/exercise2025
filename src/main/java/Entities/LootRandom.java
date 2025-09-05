package Entities;

import java.util.Random;

public final class LootRandom {
    private static final Random RNG = new Random();

    private LootRandom() {}

    public static Item randomLoot() {

        if (RNG.nextBoolean()) {
            return new Item(" Small Health Potion", ItemType.POTION, 10);
        } else {
            return new Item("Gold Pouch", ItemType.LOOT, 25);
        }
    }
}
