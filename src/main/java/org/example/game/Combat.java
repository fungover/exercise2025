package org.example.game;

import org.example.entities.Enemy;
import org.example.entities.Player;

import static org.example.utils.Colors.*;

public class Combat {
    public void startCombat(Player player, Enemy enemy) {
        // Special dialog for Thade
        if (enemy.getName().equals("Thade")) {
            System.out.println(purple("\nThade: -" + player.getName() + "! You found me!"));
            System.out.println(purple("Thade: -But... something is wrong... I can't control myself!"));
            System.out.println(purple("Thade: -The darkness... it's taking over! RUN!"));
            System.out.println(purple("Thade: -I... must... DESTROY YOU!"));
        }

        //Print out information about the fight
        System.out.println(bold("\nThe fight against " + enemy.getName() + " begins!"));
        //While-loop continues while both are alive
        while (player.isAlive() && enemy.isAlive()) {
            //Player attacks then enemy attacks
            int playerDamage = player.attack();
            enemy.takeDamage(playerDamage);
            System.out.println(green("\nYou attack for " + playerDamage + " damage!"));
            System.out.println(enemy.getName() + " has " + enemy.getHealth() + " health left.");

            if (enemy.isAlive()) {

                int enemyDamage = enemy.attack();
                player.takeDamage(enemyDamage);
                System.out.println(red("\n" + enemy.getName() + " attack you for " + enemyDamage + " damage!"));
                System.out.println("You have " + player.getHealth() + " health left.");
            }
        }
        //Check who won
        if (player.isAlive()) {
            System.out.println(bold(green("\nYou won the fight!")));
            System.out.println("You have " + player.getHealth() + " health left.");
            if (enemy.getName().equals("Thade")) {
                System.out.print(purple("\nThade: -Well played, "));
                System.out.print(cyan(player.getName()));
                System.out.print(purple(".."));
                System.out.println(purple("..well played..my friend.. "));
                System.out.print("*Thade slowly dies*");
            }
        } else {
            if (enemy.getName().equals("Thade")) {
                System.out.println(purple("\nThade: -Oh.. "));
                System.out.print(cyan(player.getName()));
                System.out.print(purple(".. don't you see it?"));
                System.out.println(purple("My real name is not Thade.."));
                System.out.println(purple("..Thade.. dThad.. deTha.."));
                System.out.println(bold(purple("it's.. DEATH!")));
            }
            System.out.println(red("\nYou lost the fight and died!"));

        }
    }
}
