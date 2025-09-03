package org.SpinalGlitter.exercise2.service;

import org.SpinalGlitter.exercise2.entities.Enemy;
import org.SpinalGlitter.exercise2.entities.Player;
import org.SpinalGlitter.exercise2.entities.Position;

import java.util.Map;
import java.util.Scanner;

public class CombatService {
    private int battleCount = 0;
    private final Map<Position, Enemy> enemies;
    private final Scanner scanner = new Scanner(System.in);

    public CombatService(Map<Position, Enemy> enemies) {
        this.enemies = enemies;
    }

    public void startCombat(Player player, Enemy enemy) {
        System.out.println("⚔️ You have entered combat with " + enemy.getName() + "!");

        while (player.isAlive() && enemy.isAlive()) {
            // Player turn
            System.out.println("\nChoose your action: (1) Attack (2) Heal");
            String input = scanner.nextLine();

            if ("1".equals(input)) {
                System.out.println("👉 You attack " + enemy.getName() + " for " + player.getDamage() + " damage.");
                enemy.setHp(player.getDamage());
            } else if ("2".equals(input)) {
                System.out.println("🧪 You used a potion!");
                player.heal(20);
            } else {
                System.out.println("❌ Invalid choice, try again.");
                continue; // jump to the next iteration
            }


            // Check if the enemy died
            if (!enemy.isAlive()) {
                this.battleCount++;
                System.out.println("🎉 You defeated " + enemy.getName() + "!");
                System.out.println("You have defeated " + battleCount + " enemies so far.");
                enemies.remove(enemy.getPosition());

                // Check if the player has won
                if (haveWon()) {
                    System.out.println("🏆 You have defeated all enemies! You win! 🏆");
                    System.out.println("Press Q to quit or press any other key to continue playing.");
                }
                break;
            }


            // Enemy turn
            System.out.println("⚡ " + enemy.getName() + " attacks you for " + enemy.getDamage() + " damage!");
            player.takeDamage(enemy.getDamage());


            // Check if the player has lost
            if (!player.isAlive()) {
                System.out.println("💀 You were slain by " + enemy.getName() + "...");
                break;
            }

            // Show round status
            System.out.println("📊 Status: " + player.getName() + " HP=" + player.getCurrentHealth() +
                    " | " + enemy.getName() + " HP=" + enemy.getHp());
        }
    }

    public boolean haveWon() {
        System.out.println("Enemies remaining: " + enemies.size());
        return enemies.isEmpty();
    }
}
