package org.example.service;

import org.example.entities.*;
import org.example.entities.equipment.Equippable;
import org.example.entities.items.consumables.HealthPotion;
import org.example.map.Dungeon;
import org.example.utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ItemService {
    private final List<Item> items;
    private final RandomGenerator randomGenerator;

    public ItemService() {
        this.items = new ArrayList<>();
        this.randomGenerator = new RandomGenerator();
    }

    public ItemService(RandomGenerator randomGenerator) {
        this.items = new ArrayList<>();
        this.randomGenerator = randomGenerator;
    }

    public void placeRandomItems(Dungeon dungeon) { // Method to place random items in the dungeon
        List<Position> positions = randomGenerator.getRandomFloorPositions(3, dungeon); // Calls random generator to get 3 random floor positions

        for (Position pos : positions) { // For each position, create a health potion and add it to the items list
            HealthPotion potion = new HealthPotion(pos);
            items.add(potion);
        }
    }

    public Item getItemAt(Position position) { // Method to get an item at a specific position
        return items.stream() // Create a stream from the items list
                .filter(item -> item.getPosition().equals(position)) // Filter items to find those that match the position
                .findFirst() // Find the first item that matches the position
                .orElse(null); // Return the item if found, otherwise return null
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    // Method for item usage outcome. Will be tested and used everywhere else.
    public ItemUseOutcome useItem(Player player, int itemIndex) {
        List<Item> inventory = player.getInventory();

        if (inventory.isEmpty()) {
            return new ItemUseOutcome(false, "Your inventory is empty.");
        }

        if (itemIndex < 0 || itemIndex > inventory.size()) {
            return new ItemUseOutcome(false, "Invalid choice.");
        }

        Item item = inventory.get(itemIndex);
        player.useItem(item.getName());
        return new ItemUseOutcome(true, "Used: " + item.getName());
    }

    // UI-Wrapper that delegate the responsibility to useItem.
    public boolean useItemFromInventory(Player player, Scanner scanner, DisplayService displayService) {
        List<Item> inventory = player.getInventory();

        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return false;
        }

        displayService.showInventory(player);
        System.out.print("Use item (enter number or 0 to cancel): ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());

            if (choice == 0) {
                return false;
            }

            ItemUseOutcome outcome = useItem(player, choice - 1);
            System.out.println(outcome.getMessage());
            return outcome.isSuccess();

        } catch (NumberFormatException e) {
            System.out.println("invalid input.");
            return false;
        }
    }

    public EquipmentOutcome equipItem(Player player, int equipmentIndex) {
        List<Item> inventory = player.getInventory();

        List<Equippable> equippableItems = inventory.stream()
                .filter(item -> item instanceof Equippable)
                .map(item -> (Equippable) item)
                .toList();

        if (equippableItems.isEmpty()) {
            return new EquipmentOutcome(false, "You have no equipment to equip.");
        }

        if (equipmentIndex < 0 || equipmentIndex > equippableItems.size()) {
            return new EquipmentOutcome(false, "Invalid choice.");
        }

        Equippable equipment = equippableItems.get(equipmentIndex);
        player.equipItem(equipment);
        player.removeFromInventory((Item) equipment);
        return new EquipmentOutcome(true, "Equipped: " + ((Item) equipment).getName());
    }

    public boolean equipItemFromInventory(Player player, Scanner scanner, DisplayService displayService) {
        List<Item> inventory = player.getInventory();

        List<Equippable> equippableItems = inventory.stream()
                .filter(item -> item instanceof Equippable)
                .map(item -> (Equippable) item)
                .toList();

        if (equippableItems.isEmpty()) {
            System.out.println("You have no equipment to equip.");
            return false;
        }

        System.out.println("Choose equipment to equip:");
        for (int i = 0; i < equippableItems.size(); i++) {
            Equippable item = equippableItems.get(i);
            System.out.println((i + 1) + ". " + ((Item) item).getName());
        }

        System.out.print("Enter number (or 0 to cancel): ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());

            if (choice == 0) {
                System.out.println("Cancelled.");
                return false;
            }

            EquipmentOutcome outcome = equipItem(player, choice - 1);
            System.out.println(outcome.getMessage());
            return outcome.isSuccess();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return false;
        }
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
}
