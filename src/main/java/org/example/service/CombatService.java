package org.example.service;

import org.example.entities.Enemy;
import org.example.entities.Player;
import org.example.map.FarmageddonMap;
import org.example.map.Tile;

public class CombatService {
    public static void attack(Player player, FarmageddonMap map) {
        Tile tile = map.getTile(player.getX(), player.getY());
        Enemy enemy = tile.getEnemy();

        if (tile.getType() == Tile.Type.ENEMY && enemy != null) {
            enemy.attack(player);

            if (!player.isAlive()) return;

            System.out.println("You fight back!");
            enemy.takeDamage(10); //TODO Fix to item damage

            if (!enemy.isAlive()) {
                enemy.defeat(tile);
            } else {
                System.out.println("Enemy HP: " + enemy.getHealth());
            }
        } else {
            System.out.println("There's nothing to attack here.");
        }
    }
}
