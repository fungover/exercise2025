package org.example.entities;

public class Tile {

    private boolean walkable;
    private Item item;
    private Enemy enemy;
    private boolean visited;

    //Constructor
    public Tile(boolean walkable) {
        this.walkable = walkable;
        this.visited = false;
    }

    //Getters
    public boolean isWalkable() {
        return walkable;
    }

    public Item getItem() {
        return item;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public boolean isVisited() {
        return visited;
    }

    //Setters
    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
