package org.example.map;

import org.example.entities.Enemy;

public class Tile {
    private TileType type;
    private Enemy enemy;

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
}
