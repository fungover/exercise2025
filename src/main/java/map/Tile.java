package map;

import entities.Item;
import entities.Enemy;

public class Tile {
    private char symbol;
    private String description;
    private Item item;
    private Enemy enemy;

    public Tile(char symbol, String description) {
        this.symbol = symbol;
        this.description = description;
        this.item = null;
        this.enemy = null;
    }

    // === OBJECT HANDLING ===
    public void placeItem(Item item) {
        this.item = item;
    }

    public Item removeItem() {
        Item removedItem = this.item;
        this.item = null;
        return removedItem;
    }

    public boolean hasItem() {
        return item != null;
    }

    public Item getItem() {
        return item;
    }

    // === ENEMY MANAGEMENT ===
    public void placeEnemy(Enemy enemy) {
        this.enemy = enemy;
        if (enemy != null) {
            // Set the enemy's position to this tile's coordinates
        }
    }

    public Enemy removeEnemy() {
        Enemy removedEnemy = this.enemy;
        this.enemy = null;
        return removedEnemy;
    }

    public boolean hasEnemy() {
        return enemy != null && enemy.isAlive();
    }

    public Enemy getEnemy() {
        return enemy;
    }

    // === DISPLAY AND NAVIGATION ===
    public char getDisplaySymbol() {
        // Priority order: Enemy > Item > Normal symbol
        if (hasEnemy()) {
            return enemy.getSymbol();
        } else if (hasItem()) {
            return item.getSymbol();
        }
        return symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getFullDescription() {
        StringBuilder desc = new StringBuilder(description);

        if (hasEnemy()) {
            desc.append(" ").append(enemy.examine());
        } else if (hasItem()) {
            desc.append(" Du ser ").append(item.getName()).append(" här.");
        }

        return desc.toString();
    }

    public String getDescription() {
        return description;
    }

    // The player can go here if there is no living enemy
    public boolean canWalkOn() {
        return symbol == ' ' && !hasEnemy();
    }

    // Can place items/enemies here
    public boolean canPlaceThings() {
        return symbol == ' ';
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        info.append("Tile[").append(symbol).append(": ").append(description);

        if (hasEnemy()) {
            info.append(" | Enemy: ").append(enemy.getName());
        }
        if (hasItem()) {
            info.append(" | Item: ").append(item.getName());
        }

        info.append("]");
        return info.toString();
    }
}