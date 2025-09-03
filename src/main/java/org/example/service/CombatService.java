package org.example.service;

import org.example.entities.Enemy;
import org.example.entities.Player;

public class CombatService {

    public String attack(Player player, Enemy enemy) {
        // Spelaren attackerar fienden
        enemy.takeDamage(player.getAttackDamage());

        if (enemy.getHp() <= 0) {
            return "EnemyDefeated";
        }

        // Fienden attackerar tillbaka
        player.takeDamage(enemy.getDamage());
        if (player.isDead()) {
            return "PlayerDefeated";
        }

        return "Ongoing";
    }

    public String defend(Player player, Enemy enemy) {
        // Spelaren försvarar -> tar halva skadan
        int reducedDamage = enemy.getDamage() / 2;
        player.takeDamage(reducedDamage);

        if (player.isDead()) {
            return "PlayerDefeated";
        }

        return "Ongoing";
    }

    public String retreat(Player player, int oldX, int oldY) {
        player.moveTo(oldX, oldY);
        return "Retreated";
    }
}