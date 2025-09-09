package org.example.map;

import org.example.entities.Enemy;
import org.example.entities.Item;

public class Tile {
    private TileType type;  // Basen av Dungeon (WALL, EMPTY, STAIRS, EXIT)
    private Enemy enemy;
    private Item item;

    public Tile(TileType type) {
        this.type = type;
    }

    // ==== TYPE ====
    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    // ==== ENEMY ====
    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public boolean hasEnemy() {
        return enemy != null && enemy.isAlive();
    }

    public void removeEnemy() {
        this.enemy = null;
    }

    // ==== ITEM ====
    public boolean hasItem() {
        return item != null;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
