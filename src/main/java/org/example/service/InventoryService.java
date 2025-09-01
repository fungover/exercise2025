package org.example.service;

import org.example.entities.Player;

public class InventoryService {
    public static void showInventory(Player player) {
        System.out.println("Inventory:");
        if (player.getInventory().isEmpty()) {
            System.out.println("- your inventory is empty");
        } else {
            player.getInventory().forEach(item -> System.out.println("- " + item.getName()));
        }
    }
}

