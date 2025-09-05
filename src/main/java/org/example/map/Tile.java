package org.example.map;

import org.example.entities.Enemy;
import org.example.entities.Item;

public class Tile {
    private Enemy enemy;
    private Item item;

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
        if (enemy != null) {
            this.type = Type.ENEMY;
            } else if (this.type == Type.ENEMY) {
            this.type = Type.PATH;
        }
    }


    public void setItem(Item item) {
        this.item = item;
        this.type = Type.ITEM;
    }
    public Item getItem() { return item; }

    public void removeItem() {
        this.item = null;
        this.type = Type.PATH;
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

