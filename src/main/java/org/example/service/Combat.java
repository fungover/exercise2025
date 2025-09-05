package org.example.service;

import org.example.entities.Character;
import java.util.Scanner;

public class Combat {
    public static void start(Character player, Character enemy) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Combat started vs " + enemy.getName() + "!");

        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("Your HP: " + player.getHealth() + "|"
                    + enemy.getName() + " HP: " + enemy.getHealth());
            System.out.print("Command(attack/run): "); // Item (use potion as choice maybe)
            String cmd = sc.nextLine().toLowerCase().trim();

            if (cmd.equals("attack")) {
                // enemy.setHealth(enemy.getHealth() - player.getAttackDamage());
                enemy.receiveDamage(player.getAttackDamage());
                System.out.println("You hit the " + enemy.getName() + " for "
                        + player.getAttackDamage() + " damage!");
                // Check if Enemy is defeated
                if (!enemy.isAlive()) {
                    System.out.println("Enemy defeated!");
                    break;
                }

                enemy.takeTurn();
                player.receiveDamage(enemy.getAttackDamage());
                System.out.println(enemy.getName() + " hits you for "
                        + enemy.getAttackDamage() + " damage!");
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
