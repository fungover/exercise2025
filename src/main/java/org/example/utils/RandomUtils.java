package org.example.utils;

import org.example.entities.*;
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
        int roll = random.nextInt(100); // 0–99
        if (roll < 50) {
            return new Item("Iron Sword", Item.ItemType.WEAPON, 10);   // 50%
        } else if (roll < 80) {
            return new Item("Battle Axe", Item.ItemType.WEAPON, 15);  // 30%
        } else if (roll < 95) {
            return new Item("Magic Sword", Item.ItemType.WEAPON, 20); // 15%
        } else {
            return new Item("Excalibur", Item.ItemType.WEAPON, 50);   // 5%
        }
    }

    public static Item randomArmor() {
        int roll = random.nextInt(100);
        if (roll < 50) {
            return new Item("Leather Armor", Item.ItemType.ARMOR, 5);   // 50%
        } else if (roll < 80) {
            return new Item("Chainmail", Item.ItemType.ARMOR, 10);     // 30%
        } else if (roll < 95) {
            return new Item("Plate Armor", Item.ItemType.ARMOR, 15);   // 15%
        } else {
            return new Item("Dragon Scale Mail", Item.ItemType.ARMOR, 20);   // 5%
        }
    }

    public static Item randomPotion() {
        int roll = random.nextInt(100);
        if (roll < 60) {
            return new Item("Potion", Item.ItemType.CONSUMABLE, 30);    // 60%
        } else if (roll < 90) {
            return new Item("Hi Potion", Item.ItemType.CONSUMABLE, 100); // 30%
        } else {
            return new Item("Elixir", Item.ItemType.CONSUMABLE, 9999);  // 10%
        }
    }

    public static boolean chance(double probability) {
        return random.nextDouble() < probability;
    }
}
