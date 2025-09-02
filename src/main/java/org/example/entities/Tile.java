package org.example.entities;

public class Tile {
    /**
     this will be our tiles and each tile can have different options
     it could be a wall (terrain we don't want our player to move over)
     it can have a player or enemy or an item, but not multiple at once
     */

    private boolean isWall;
    private boolean hasPlayer;
    private Enemy enemy;
    private Item item;

    public Tile() {
        this.isWall = false;
        this.hasPlayer = false;
        this.enemy = null;
        this.item = null;
    }

    public Tile(boolean isWall) {
        this.isWall = isWall;
        this.hasPlayer = false;
        this.enemy = null;
        this.item = null;
    }

    // getters

    public boolean isWall() {return isWall;}

    public boolean hasPlayer() {return hasPlayer;}

    public Enemy getEnemy() {return enemy;}

    public Item getItem() {return item;}

    // setters

    public void setWall(boolean wall) {this.isWall = wall;}

    public void setHasPlayer(boolean hasPlayer) {this.hasPlayer = hasPlayer;}

    public void setEnemy(Enemy enemy) {this.enemy = enemy;}

    public void setItem(Item item) {this.item = item;}


    // methods
    public boolean isEmpty() {
        return !isWall && enemy == null && item == null;
    }

    public boolean hasEnemy() {return enemy != null;}

    public boolean hasItem() {return item != null;}

    public String describe() {
        if (isWall) return "█";
        if (hasPlayer) return "@";
        if (hasEnemy()) return "E";
        if (hasItem()) return "I";
        return ".";
    }


}
