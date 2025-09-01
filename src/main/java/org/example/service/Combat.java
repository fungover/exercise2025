package org.example.service;

import org.example.entities.Enemy;
import org.example.entities.Player;

public class Combat {
    public void damageTaken(Player player, Enemy enemy) {
        int damageReceived = enemy.getDamage();
        player.setHealth(damageReceived);
    }

    public void damageGiven(Enemy enemy, int damage) {
        int damageDealt = enemy.getHealth() - damage;
        enemy.setHealth(damageDealt);
    }
}
