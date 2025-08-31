package org.example.service;

import org.example.entities.IronSword;
import org.example.entities.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootService {
    private static boolean swordDropped = false;
    private static boolean helmetDropped = false;
    private static boolean chestplateDropped = false;
    private static boolean bootsDropped = false;

    public void tryDropRandomLoot(Player player) {
        Random random = new Random();
        if (random.nextInt(100) < 33) {

            List<String> availableItems = new ArrayList<>();
            if (!swordDropped) availableItems.add("SWORD");
            if (!helmetDropped) availableItems.add("HELMET");
            if (!chestplateDropped) availableItems.add("CHESTPLATE");
            if (!bootsDropped) availableItems.add("BOOTS");

            if (!availableItems.isEmpty()) {
                String randomItem = availableItems.get(random.nextInt(availableItems.size()));
                dropItem(randomItem, player);
            } else {
                System.out.println("No loot dropped - all items have already been found.");
            }
        } else {
            System.out.println("No loot dropped.");
        }
    }

    public void dropItem(String itemType, Player player) {
        switch (itemType) {
            case "SWORD" -> {
                swordDropped = true;
                player.addToInventory(new IronSword(player.getPosition()));
                System.out.println("An Iron Sword was dropped!");
            }
            case "HELMET" -> {
                helmetDropped = true;
                System.out.println("To be implemented");
            }
            case "CHESTPLATE" -> {
                chestplateDropped = true;
                System.out.println("To be implemented");
            }
            case "BOOTS" -> {
                bootsDropped = true;
                System.out.println("To be implemented");
            }
        }
    }
}