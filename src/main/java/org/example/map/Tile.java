package org.example.map;

import org.example.entities.Enemy;
import org.example.entities.Item;
import org.example.entities.Position;

public class Tile {
    private TileType type;
    private Enemy enemy;
    private Item item;

    public Tile(Position position, TileType type) {
        this.type = type;
    }

    public TileType getType() { return type;}
    public void setType(TileType type) { this.type = type; }

    public Enemy getEnemy() { return enemy; }
    public void setEnemy(Enemy enemy) { this.enemy = enemy;}

    public Item getItem() { return item; }
    public void setItem(Item item) {this.item = item; }
    public boolean hasItem() { return item != null; }

    public boolean isWalkable() { return type != TileType.WALL; }

    @Override
    public String toString() {
        String symbol = ".";
        switch (type) {
            case WALL -> symbol = "#";
            case FLOOR -> symbol = ".";
        }
        if (enemy != null && item != null) return "E/I";
        if (enemy != null) return "E";
        if (item != null) return "I";
        return symbol;
    }
}