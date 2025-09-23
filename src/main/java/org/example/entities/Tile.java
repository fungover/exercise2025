package org.example.entities;

public class Tile {
    private String type;
    private boolean walkable;
    private Item item;
    private Enemy enemy;
    private String symbol;

    public Tile(String type, boolean walkable, String symbol) {
        this.type = type;
        this.walkable = walkable;
        this.symbol = symbol;
        this.item = null;
        this.enemy = null;
    }

    // Getters
    public String getType() {
        return type;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public String getSymbol() {
        return symbol;
    }

    public Item getItem() {
        return item;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    // Setters
    public void setItem(Item item) {
        this.item = item;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public void removeItem() {
        this.item = null;
    }

    public void removeEnemy() {
        this.enemy = null;
    }

    // Tile type checks
    public boolean hasItem() {
        return item != null;
    }

    public boolean hasEnemy() {
        return enemy != null && enemy.isAlive();
    }

    public boolean isEmpty() {
        return !hasItem() && !hasEnemy();
    }

    // Static factory methods for common tiles
    public static Tile createEmpty() {
        return new Tile("empty", true, ".");
    }

    public static Tile createWall() {
        return new Tile("wall", false, "#");
    }

    public static Tile createFloor() {
        return new Tile("floor", true, " ");
    }

    @Override
    public String toString() {
        if (hasEnemy()) return "E";
        if (hasItem()) return "I";
        return symbol;
    }
}