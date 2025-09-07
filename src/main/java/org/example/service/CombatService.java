package org.example.service;

import org.example.entities.Enemy;
import org.example.entities.Player;
import org.example.entities.Tile;
import org.example.entities.TileType;
import org.example.map.Dungeon;
import org.example.utils.InputValidator;

public class CombatService {
    private final Dungeon dungeon;
    private final InputValidator validator;

    public CombatService(Dungeon dungeon, InputValidator validator) {
        if (dungeon == null) {
            throw new IllegalArgumentException("Dungeon cannot be null");
        }
        if (validator == null) {
            throw new IllegalArgumentException("Input validator cannot be null");
        }
        this.dungeon = dungeon;
        this.validator = validator;
        System.out.println();
    }

    public void engageCombat(int x, int y, Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        System.out.println("Attempting to engage combat at " + x + ", " + y + " from player at " + player.getX() + ", " + player.getY());
        if (!validator.isValidInteraction(x, y, player)) {
            throw new IllegalArgumentException("Invalid interaction");
        }
        Tile tile = dungeon.getTile(x, y);
        System.out.println("Checking tile at (" + x + ", " + y + "): " + tile);
        Enemy enemy = tile.getEnemy();
        if (tile.getType() != TileType.ENEMY || enemy == null || !enemy.isAlive()) {
            System.out.println("Cannot engage combat at ("+ x + ", " + y + "): no valid enemy");
            return;
        }
        System.out.println("Engaging enemy: " + enemy);
        player.attack(enemy);
        if (!enemy.isAlive()) {
            System.out.println("Enemy defeated: " + enemy);
            tile.clearContents();
        }
    }
}
