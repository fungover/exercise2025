package org.example.utils;

import org.example.entities.*;
import org.example.map.Tile;
import org.example.map.TileType;

import java.util.Random;

public class RandomUtils {
    private static final Random random = new Random();

    public static Enemy randomEnemy() {
        int roll = random.nextInt(3);
        return switch (roll) {
            case 0 -> new Goblin();
            case 1 -> new Orc();
            default -> new Dragon();
        };
    }

    public static Item randomWeapon() {
        return switch (random.nextInt(3)) {
            case 0 -> new Item("Iron Sword", Item.ItemType.WEAPON, 10);
            case 1 -> new Item("Battle Axe", Item.ItemType.WEAPON, 15);
            default -> new Item("Steel Dagger", Item.ItemType.WEAPON, 8);
        };
    }

    public static Item randomArmor() {
        return switch (random.nextInt(3)) {
            case 0 -> new Item("Leather Armor", Item.ItemType.ARMOR, 5);
            case 1 -> new Item("Chainmail", Item.ItemType.ARMOR, 10);
            default -> new Item("Plate Armor", Item.ItemType.ARMOR, 15);
        };
    }

    public static Item randomPotion() {
        return switch (random.nextInt(2)) {
            case 0 -> new Item("Potion", Item.ItemType.CONSUMABLE, 50);
            default -> new Item("Elixir", Item.ItemType.CONSUMABLE, 30);
        };
    }

    public static boolean chance(double probability) {
        return random.nextDouble() < probability;
    }
}