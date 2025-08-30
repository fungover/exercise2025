package org.example.service;

import org.example.entities.Enemy;
import org.example.entities.Player;

import java.util.Scanner;

public class CombatService {
    private final DisplayService displayService;

    public CombatService(DisplayService displayService) {
        this.displayService = displayService;
    }

    public void startCombat(Player player, Enemy enemy, Scanner scanner) {
        System.out.println("=== Combat started with " + enemy.getName() + " ===");

        while (!player.isDead() && !enemy.isDead()) {

            displayService.displayCombatStatus(player, enemy);
            if (!playerTurn(player, enemy, scanner)) {
                System.out.println("You fled from combat!");
                return;
            }

            if (enemy.isDead()) {
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
                    System.out.println("Using item...Soon to be implemented");
                    return true;
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
