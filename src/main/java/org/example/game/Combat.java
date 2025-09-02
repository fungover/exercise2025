package org.example.game;

import org.example.entities.Enemy;
import org.example.entities.Player;

public class Combat {
    public void startCombat(Player player, Enemy enemy) {
        //Print out information about the fight
        System.out.println("\nThe fight against " + enemy.getName() + " begins!");
        //While-loop continues while both are alive
        while (player.isAlive() && enemy.isAlive()) {
            //Player attacks then enemy attacks
            int playerDamage = player.attack();
            enemy.takeDamage(playerDamage);
            System.out.println("\nYou attack for " + playerDamage + " damage!");
            System.out.println(enemy.getName() + " has " + enemy.getHealth() + " health left.");

            if (enemy.isAlive()) {

                int enemyDamage = enemy.attack();
                player.takeDamage(enemyDamage);
                System.out.println("\n" + enemy.getName() + " attack you for " + enemyDamage + " damage!");
                System.out.println("You have " + player.getHealth() + " health left.");
            }
        }
        //Check who won
        if (player.isAlive()) {
            System.out.println("\nYou won the fight!");
        } else {
            System.out.println("\nYou lost the fight and died!");
        }
    }
}
