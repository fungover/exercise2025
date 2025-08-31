package org.example.service;

import org.example.entities.HealthPotion;
import org.example.entities.Item;
import org.example.entities.Player;
import org.example.entities.Position;
import org.example.map.Dungeon;
import org.example.utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ItemService {

    private final List<Item> items;

    public ItemService() {
        this.items = new ArrayList<>();
    }

    public void placeRandomItems(Dungeon dungeon) { // Method to place random items in the dungeon
        List<Position> positions = RandomGenerator.getRandomFloorPositions(3, dungeon); // Calls random generator to get 3 random floor positions

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

            if (choice > 0 && choice <= inventory.size()) {
                Item item = inventory.get(choice - 1);
                player.useItem(item.getName());
                return true;
            } else {
                System.out.println("invalid choice.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("invalid input.");
            return false;
        }
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
}
