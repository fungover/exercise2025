package org.example.service;

import org.example.entities.Character;
import java.util.Scanner;

public class Combat {
    public static void start(Scanner scanner, Character player, Character enemy) {
        System.out.println("Combat started vs " + enemy.getName() + "!");
        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("Your HP: " + player.getHealth() + "|"
                    + enemy.getName() + " HP: " + enemy.getHealth());
            System.out.print("Command(attack/run): "); // Item (use potion as choice maybe)
            String cmd = scanner.nextLine().toLowerCase().trim();

            if (cmd.equals("attack")) {
                int playerDamage = player.getAttackDamage();
                enemy.receiveDamage(playerDamage);
                System.out.println("You hit the " + enemy.getName() + " for "
                        + playerDamage + " damage!");
                // Check if Enemy is defeated
                if (!enemy.isAlive()) {
                    System.out.println("Enemy defeated!");
                    break;
                }

                enemy.takeTurn();
                int enemyDamage = enemy.getAttackDamage();
                player.receiveDamage(enemyDamage);
                System.out.println(enemy.getName() + " hits you for "
                        + enemyDamage + " damage!");
            } else if (cmd.equals("run")) {
                System.out.println("You fled!");
                break;
            } else {
                System.out.println("Invalid command!");
            }


        }
    }
    // For initiate attack in test without writing "attack" in CLI.
    public static void doAttack(Character attacker, Character defender) {
        defender.receiveDamage(attacker.getAttackDamage());

    }
}

