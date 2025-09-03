package org.SpinalGlitter.exercise2.service;

import org.SpinalGlitter.exercise2.entities.Enemy;
import org.SpinalGlitter.exercise2.entities.Player;
import org.SpinalGlitter.exercise2.entities.Position;
import org.SpinalGlitter.exercise2.map.DungeonMap;

import java.util.Map;
import java.util.Scanner;

public class CombatService {
    private int battleCount = 0;
    private Map<Position, Enemy> enemies;
    private final Scanner scanner = new Scanner(System.in);

    public CombatService(Map<Position, Enemy> enemies) {
        this.enemies = enemies;
    }

    public boolean startCombat(Player player, Enemy enemy) {
        battleCount++;
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
                continue; // hoppa över till nästa runda
            }

            // Check if enemy died
            if (!enemy.isAlive()) {
                System.out.println("🎉 You defeated " + enemy.getName() + "!");
                enemies.remove(enemy.getPosition());
                break;
            }

            // Enemy turn
            System.out.println("⚡ " + enemy.getName() + " attacks you for " + enemy.getDamage() + " damage!");
            player.takeDamage(enemy.getDamage());

            if (!player.isAlive()) {
                System.out.println("💀 You were slain by " + enemy.getName() + "...");
                break;
            }

            // Show round status
            System.out.println("📊 Status: " + player.getName() + " HP=" + player.getCurrentHealth() +
                    " | " + enemy.getName() + " HP=" + enemy.getHp());
        }

        return player.isAlive(); // true om spelaren vann, false annars
    }
}




/*    public void enemyAttacks() {
      System.out.println(enemy.getName() + " attacks you for " + enemy.getDamage() + " damage!");
      player.setHp(player.getHp() - enemy.getDamage());
      System.out.println("You now have " + player.getHp() + " HP left.");
}*/


    /**
     * startCombat
     * player turn
     * enemy turn
     * check if player or enemy is dead
     *
     * Display status for enemy and player
     */
