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
    public void setEnemy(Enemy enemy) { this.type = enemy == null ? TileType.EMPTY : TileType.ENEMY; }

    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; this.type = item == null ? TileType.EMPTY : TileType.ITEM; }

    public boolean isWalkable() { return type != TileType.WALL; }

    @Override
    public String toString() {
        if (enemy != null) return "E";
        if (item != null) return "I";
        return switch(type) {
            case WALL -> "#";
            case EMPTY -> ".";
            case EXIT -> "⌂";
            default -> "?";
        };
    }
}