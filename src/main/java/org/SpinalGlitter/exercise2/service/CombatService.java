package org.SpinalGlitter.exercise2.service;

import org.SpinalGlitter.exercise2.entities.Enemy;
import org.SpinalGlitter.exercise2.entities.Player;

import java.util.Scanner;

public class CombatService {
    private int battleCount = 0;
    Scanner scanner = new Scanner(System.in);
    /*private final Player player;
    */

    public void startCombat(Player player, Enemy enemy) {
        battleCount++;
        System.out.println("You have entered combat with " + enemy.getName());

        while(player.isAlive() && enemy.isAlive()) {
            System.out.println("Choose your action: (1) Attack (2) Heal");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    System.out.println("You chose to attack!");
/*
                        attackEnemy();
*/
                    break;
                case "2":
                    System.out.println("You chose to defend!");
                    player.heal(20);
                    // Implement defend logic here
                    break;

                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }

            // Enemy turn logic here

            // Check if player or enemy is dead and break the loop if so

            // Display status for enemy and player
        }
    }


    /**
     * startCombat
     * player turn
     * enemy turn
     * check if player or enemy is dead
     *
     * Display status for enemy and player
     */
}
