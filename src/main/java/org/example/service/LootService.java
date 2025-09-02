package org.example.service;

import org.example.entities.Player;
import org.example.entities.items.armor.IronBoots;
import org.example.entities.items.armor.IronChestplate;
import org.example.entities.items.armor.IronHelm;
import org.example.entities.items.weapons.IronSword;
import org.example.utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class LootService {
    private boolean swordDropped = false;
    private boolean helmetDropped = false;
    private boolean chestplateDropped = false;
    private boolean bootsDropped = false;
    private final RandomGenerator randomGenerator;

    public LootService() {
        this.randomGenerator = new RandomGenerator();
    }

    public LootService(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public LootDropOutcome tryDropRandomLoot(Player player) {
        if (randomGenerator.nextInt(100) >= 50) { //
            return new LootDropOutcome(false, "No loot dropped.");
        }

        List<String> availableItems = getAvailableItems();

        if (availableItems.isEmpty()) {
            return new LootDropOutcome(false, "No loot dropped - all items have already been found.");
        }

        String randomItem = availableItems.get(randomGenerator.nextInt(availableItems.size()));
        return dropItem(randomItem, player);
    }

    private List<String> getAvailableItems() {
        List<String> availableItems = new ArrayList<>();
        if (!swordDropped) availableItems.add("SWORD");
        if (!helmetDropped) availableItems.add("HELMET");
        if (!chestplateDropped) availableItems.add("CHESTPLATE");
        if (!bootsDropped) availableItems.add("BOOTS");
        return availableItems;
    }

    public LootDropOutcome dropItem(String itemType, Player player) {
        switch (itemType) {
            case "SWORD" -> {
                swordDropped = true;
                player.addToInventory(new IronSword(player.getPosition()));
                return new LootDropOutcome(true, "An Iron Sword was dropped!");
            }
            case "HELMET" -> {
                helmetDropped = true;
                player.addToInventory(new IronHelm(player.getPosition()));
                return new LootDropOutcome(true, "An Iron Helm was dropped!");
            }
            case "CHESTPLATE" -> {
                chestplateDropped = true;
                player.addToInventory(new IronChestplate(player.getPosition()));
                return new LootDropOutcome(true, "An Iron Chestplate was dropped!");
            }
            case "BOOTS" -> {
                bootsDropped = true;
                player.addToInventory(new IronBoots(player.getPosition()));
                return new LootDropOutcome(true, "A pair of Iron Boots was dropped!");
            }
            default -> {
                return new LootDropOutcome(false, "Unknown item type.");
            }
        }
    }

    // Getters for testing purposes
    public boolean isSwordDropped() {
        return swordDropped;
    }

    public boolean isHelmetDropped() {
        return helmetDropped;
    }

    public boolean isChestplateDropped() {
        return chestplateDropped;
    }

    public boolean isBootsDropped() {
        return bootsDropped;
    }
}