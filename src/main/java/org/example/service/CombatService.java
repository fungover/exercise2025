package org.example.service;

import org.example.entities.Enemy;
import org.example.entities.Item;
import org.example.entities.Player;
import org.example.utils.RandomGenerator;

import java.util.Scanner;

public class CombatService {
    public static boolean combat(Player player, Enemy enemy, Scanner scanner) {
        System.out.println("\n=== COMBAT ===");
        System.out.println("You encounter a " + enemy.getName() + "!");
        System.out.println(enemy.getName() + " HP: " + enemy.getHealth());

        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("\n--- Your Turn ---");
            System.out.println("Your HP: " + player.getHealth() + "/" + player.getMaxHealth());
            System.out.println(enemy.getName() + " HP: " + enemy.getHealth() + "/" + enemy.getMaxHealth());

            System.out.println("\nWhat do you want to do?");
            System.out.println("1. Attack");
            System.out.println("2. Use Item");
            System.out.println("3. Flee");
            System.out.print("Choose (1-3): ");

            String choice = scanner.nextLine().trim();

            boolean playerTurnComplete = false;
            switch (choice) {
                case "1":
                case "attack":
                    playerAttack(player, enemy);
                    playerTurnComplete = true;
                    break;

                case "2":
                case "use":
                case "item":
                    if (useItemInCombat(player, scanner)) {
                        playerTurnComplete = true;
                    }
                    //if no item was used, play again
                    break;

                case "3":
                case "flee":
                    if (attemptFlee()) {
                        System.out.println("You have fled from combat!");
                        return false; //combat ended, enemy alive
                    } else {
                        System.out.println("You couldn't escape! You must fight!");
                        playerTurnComplete = true;
                    }
                    break;
                default:
                    System.out.println("Invalid choice! Please choose 1, 2 or 3.");
                    continue;
            }

            //enemy's turn
            if (playerTurnComplete && enemy.isAlive() && player.isAlive()) {
                System.out.println("\n--- Enemy Turn ---");
                enemyAttack(player, enemy);

                //this will give us a small pause
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        if (player.isAlive() && !enemy.isAlive()) {
            System.out.println("\n" + enemy.getDeathMessage());
            System.out.println("Victory! You defeated the " + enemy.getName() + "!");
            return true;
        } else if (!player.isAlive()) {
            System.out.println("\nYou have been defeated!");
            System.out.println("Game Over");
        }
        return !enemy.isAlive(); //will return true if enemy is dead


    }

    static void playerAttack(Player player, Enemy enemy) {
        int damage = player.getDamage() + RandomGenerator.nextInt(-2, 3);//gives us some random dmg
        damage = Math.max(1, damage); //sets min dmg to 1

        enemy.takeDamage(damage);
        System.out.println("You attack the " + enemy.getName() + " for " + damage + " damage!");

        if (enemy.isAlive()) {
            System.out.println(enemy.getName() + " HP: " + enemy.getHealth() + "/" + enemy.getMaxHealth());
        }
    }

    static void enemyAttack(Player player, Enemy enemy) {
        int damage = enemy.getDamage() + RandomGenerator.nextInt(-2, 3);
        damage = Math.max(1, damage);

        System.out.println(enemy.getAttackMessage());
        player.takeDamage(damage);
        System.out.println("The " + enemy.getName() + " deals " + damage + " damage to you!");
        System.out.println("Your HP: " + player.getHealth() + "/" + player.getMaxHealth());

        if (!player.isAlive()) {
            System.out.println("You have fallen!");
        }
    }

    private static boolean useItemInCombat(Player player, Scanner scanner) {
        if (player.getInventory().isEmpty()) {
            System.out.println("You have no items to use");
            return false;
        }

        System.out.println("\n=== Usable Items ===");
        boolean hasUsableItems = false;
        for (int i = 0; i < player.getInventory().size(); i++) {
            Item item = player.getInventory().get(i);
            if (item.getType() == Item.ItemType.Potion) {
                System.out.println((i + 1) + ", " + item.getName() + " - " + item.getDescription());
                hasUsableItems = true;
            }
        }
        if (!hasUsableItems) {
            System.out.println("You have no items to use in combat!");
            return false;
        }

        System.out.println("Which item do you want to use? (number or 0 to cancel)");
        String input = scanner.nextLine().trim();
        if (input.equals("0")) {
            return false;
        }

        try {
            int index = Integer.parseInt(input) - 1;
            if (index >= 0 && index < player.getInventory().size()) {
                Item item = player.getInventory().get(index);
                if (item.getType() == Item.ItemType.Potion) {
                    item.use(player);
                    player.removeItem(item);
                    return true;
                } else {
                    System.out.println("You can't use that item in combat!");
                    return false;
                }
            } else {
                System.out.println("Invalid item number!");
                return false;
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number!");
            return false;
        }
    }

    private static boolean attemptFlee() {
        //50% to flee
        return RandomGenerator.nextInt(0, 2) == 0;
    }

}
