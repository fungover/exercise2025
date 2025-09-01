package org.example.service;

import org.example.entities.enemies.Enemy;
import org.example.entities.Player;

import java.util.Scanner;

public class CombatService {
    private final DisplayService displayService;
    private final ItemService itemService;
    private final LootService lootService;

    public CombatService(DisplayService displayService, ItemService itemService) {
        this.displayService = displayService;
        this.itemService = itemService;
        this.lootService = new LootService();
    }

    public void startCombat(Player player, Enemy enemy, Scanner scanner) {

        while (!player.isDead() && !enemy.isDead()) {

            displayService.displayCombatStatus(player, enemy);
            if (!playerTurn(player, enemy, scanner)) {
                System.out.println("You fled from combat!");
                return;
            }

            if (enemy.isDead()) {
                enemy.dropLoot(player, lootService);
                return;
            }

            enemyTurn(player, enemy);

            if (player.isDead()) {
                System.out.println("You have been defeated by " + enemy.getName());
                return;
            }
        }
    }

    private boolean playerTurn(Player player, Enemy enemy, Scanner scanner) {
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
                case 1 -> {
                    player.attack(enemy);
                    return true;
                }
                case 2 -> {
                    if (itemService.useItemFromInventory(player, scanner, displayService)) {
                        return true;
                    } else {
                        return playerTurn(player, enemy, scanner);
                    }
                }
                case 3 -> {
                    return false; // Flee
                }
                default -> {
                    System.out.println("Invalid choice.");
                    return playerTurn(player, enemy, scanner);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return playerTurn(player, enemy, scanner);
        }
    }

    private void enemyTurn(Player player, Enemy enemy) {
        System.out.println("\n--- " + enemy.getName() + "'s turn ---");
        enemy.attack(player);
    }
}
