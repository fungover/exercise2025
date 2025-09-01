package org.example.map;

import org.example.entities.Enemy;

public class Tile {
    private Enemy enemy;

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public enum Type { WALL, PATH, ENEMY, ITEM, PLAYER_START }

    private Type type;
    private boolean discovered;

    public Tile(Type type) {
        this.type = type;
        this.discovered = false;
    }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
}

