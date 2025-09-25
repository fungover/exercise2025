package org.example.map;

import org.example.entities.Item;
import org.example.entities.Enemy;

public class Tiles {
    private int x;
    private int y;
    private boolean isWall;
    private Enemy enemy;
    private Item item;

    public Tiles(int x, int y, boolean isWall) {
        this.x = x;
        this.y = y;
        this.isWall = isWall;
    }
    // Getters & setters
    public boolean isWall() {
        return isWall;
    }
}
