package org.example.map;

import org.example.entities.Enemy;
import org.example.entities.Item;

public class Tile {
    private TileType type;
    private Enemy enemy;
    private Item item;

    public Tile(TileType type) {
        this.type = type;
        this.enemy = null;
    }

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
        if (enemy != null) {
            this.type = TileType.ENEMY;
        }
    }

    public boolean hasEnemy() {
        return enemy != null && enemy.isAlive();
    }

    public void removeEnemy() {
        this.enemy = null;
        this.type = TileType.EMPTY;
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
        if (item != null) {
            this.type = TileType.ITEM;
        } else {
            this.type = TileType.EMPTY;
        }
    }




}
