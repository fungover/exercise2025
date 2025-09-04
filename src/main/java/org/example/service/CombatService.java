package org.example.service;

import org.example.entities.characters.Player;
import org.example.entities.enemies.Enemy;
import org.example.entities.items.Potion;
import org.example.utils.Utils;

public class CombatService {
    public void battleEnemy(Player player, Enemy enemy){
        InputService inputservice = new InputService();
        System.out.println("You are now fighting " + enemy.getName() + "!");

        while (player.getHealth() > 0 && enemy.getHealth() > 0) {
            int playerDamage = player.getDamage();
            int enemyDamage = enemy.getDamage();
            System.out.println("\nYour HP: " + player.getHealth() + " | Enemy HP: " + enemy.getHealth());
            System.out.println("Choose action:");
            System.out.println("1. Attack");
            System.out.println("2. Use Health Potion");

            String input = inputservice.readLine();

            if (!input.equals("1")  && !input.equals("2")) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1:
                    enemy.takeDamage(playerDamage);
                    System.out.println(player.getAttackMessage(enemy));
                    Utils.newRow();
                    break;

                case 2:
                    Potion potion = player.getInventory().getFirstPotion();
                    if (potion != null) {
                        player.usePotion(potion);
                        Utils.newRow();
                    } else {
                        System.out.println("You don't have any health potions.");
                        Utils.newRow();
                        continue;
                    }
                    break;
            }

            if (enemy.getHealth() <= 0) {
                System.out.println(enemy.getDeathMessage());
                Utils.newRow();
                break;
            }

            // Enemy attacks if still alive
            player.takeDamage(enemyDamage);
            System.out.println(enemy.getAttackMessage());
            Utils.newRow();


        }
    }
}
