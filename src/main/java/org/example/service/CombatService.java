package org.example.service;

import org.example.entities.Enemy;
import org.example.entities.Player;

public class CombatService {

    public CombatResult attack(Player player, Enemy enemy) {
        StringBuilder log = new StringBuilder();

        // Spelaren attackerar fienden
        enemy.takeDamage(player.getAttackDamage());
        log.append("You attack ").append(enemy.getName())
                .append(" for ").append(player.getAttackDamage())
                .append(" damage. (").append(enemy.getHp()).append(" HP left)\n");

        if (enemy.getHp() <= 0) {
            log.append(enemy.getName()).append(" is defeated!\n");

            // Ge spelaren XP
            int xpGained = enemy.getXpReward();
            player.gainXp(xpGained);
            log.append("You gained ").append(xpGained).append(" XP!");

            return new CombatResult("EnemyDefeated", log.toString());
        }

        // Fienden attackerar tillbaka
        int actualDamage = player.takeDamage(enemy.getDamage());
        log.append(enemy.getName()).append(" attacks you for ")
                .append(actualDamage).append(" damage. (")
                .append(player.getHp()).append(" HP left)\n");

        if (player.isDead()) {
            log.append("You have been slain...");
            return new CombatResult("PlayerDefeated", log.toString());
        }

        return new CombatResult("Ongoing", log.toString());
    }

    public CombatResult defend(Player player, Enemy enemy) {
        StringBuilder log = new StringBuilder();

        int reducedDamage = enemy.getDamage() / 2;
        player.takeDamage(reducedDamage);
        log.append("You defend and reduce the damage! ")
                .append(enemy.getName()).append(" deals ")
                .append(reducedDamage).append(" damage. (")
                .append(player.getHp()).append(" HP left)\n");

        if (player.isDead()) {
            log.append("You have been slain...");
            return new CombatResult("PlayerDefeated", log.toString());
        }

        return new CombatResult("Ongoing", log.toString());
    }

    public CombatResult retreat(Player player, int oldX, int oldY) {
        player.moveTo(oldX, oldY);
        return new CombatResult("Retreated", "You retreat to your previous position.");
    }
}