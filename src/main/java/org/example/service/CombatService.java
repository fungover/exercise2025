package org.example.service;

import org.example.entities.Enemy;
import org.example.entities.Player;

public class CombatService {

    public String attack(Player player, Enemy enemy) {
        enemy.takeDamage(20); // spelaren gör alltid 20 dmg (kan utvecklas senare)
        if (enemy.getHp() <= 0) {
            return "Enemy has been killed!";
        }

        player.takeDamage(enemy.getDamage());
        if (player.getHp() <= 0) {
            return "You have been defeated!";
        }

        return "Ongoing";
    }

    public String defend(Player player, Enemy enemy) {
        int reducedDamage = enemy.getDamage() / 2;
        player.takeDamage(reducedDamage);

        if (player.getHp() <= 0) {
            return "You have been defeated!";
        }

        return "Ongoing";
    }

    public String retreat(Player player, int oldX, int oldY) {
        player.moveTo(oldX, oldY);
        return "You have retreated!";
    }
}