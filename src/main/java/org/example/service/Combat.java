package org.example.service;

import org.example.entities.Boss;
import org.example.entities.Enemy;
import org.example.entities.Inventory;
import org.example.entities.Player;
import org.example.map.TileEnum;
import org.example.map.Tile;

import java.util.Random;
import java.util.Scanner;

public class Combat {

    public static void Fight(Player player, Enemy enemy, Tile tile) {
        Inventory userInventory = player.getInventory();

        while (enemy.getHealth() > 0 && player.getHealth() > 0) {
            System.out.println("Your turn do you wish to (attack or heal) Your current health: " + player.getHealth());

            Scanner scanner = new Scanner(System.in);
            String userChoice = scanner.nextLine().trim();

            if (userChoice.contains("attack")) {
                enemy.setHealth(enemy.getHealth() - player.getDamage());

                if (enemy.getHealth() <= 0) {
                    System.out.println("You killed the " + enemy.getType());
                } else {
                    System.out.println("You attacked! " + enemy.getType() + "'s health is now " + enemy.getHealth());
                }
            } else if (userChoice.contains("heal")) {
                if (userInventory.hasItem("Health Potion")) {
                    if (player.getHealth() >= player.getMaxHealth()) {
                        System.out.println("You already have maximum health!");

                        return;
                    }

                    int totalHealing = player.getMaxHealth() - player.getHealth();
                    player.setHealth(player.getHealth() + totalHealing);
                    System.out.println("You used a health potion. You now have " + player.getHealth() + " health!");
                    userInventory.removeItem("Health Potion");
                }
            }

            if (enemy.getHealth() > 0 && player.getHealth() > 0) {
                if (enemy instanceof Boss boss) {
                    Random rand = new Random();
                    double roll = rand.nextDouble();

                    if (roll <= 0.2) {
                        boss.useSpecialMove(player);
                    } else {
                        boss.Attack(player);
                    }
                } else {
                    enemy.Attack(player);
                }

                System.out.println("You now have " + player.getHealth() + " health!");
            } else {
                String result = (player.getHealth() > 0 ) ? "You won!" : "You lost :(";
                System.out.println("Battle is over! " + result);
                tile.setType(TileEnum.EMPTY);
            }
        }
    }
}
