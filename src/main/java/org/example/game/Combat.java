package org.example.game;

import org.example.entities.Enemy;
import org.example.entities.Player;

public class Combat {
    public void startCombat(Player player, Enemy enemy) {
        // Special dialog for Thade
        if (enemy.getName().equals("Thade")) {
            System.out.println("\nThade: -" + player.getName() + "! You found me!");
            System.out.println("Thade: -But... something is wrong... I can't control myself!");
            System.out.println("Thade: -The darkness... it's taking over! RUN!");
            System.out.println("Thade: -I... must... DESTROY YOU!");
        }

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
            if (enemy.getName().equals("Thade")) {
                System.out.println("\nThade: -Well played, " + player.getName() + "!");
                System.out.println("..well played.. *slowly dies*");
            }
        } else {
            if (enemy.getName().equals("Thade")) {
                System.out.println("\nThade: -Oh.." + player.getName() + ".. Don't you see..?");
                System.out.println("My real name is not Thade..");
                System.out.println("..Thade.. dThad.. deTha..");
                System.out.println("it's.. DEATH!");
            }
            System.out.println("\nYou lost the fight and died!");

        }
    }
}
