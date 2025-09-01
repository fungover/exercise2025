package org.example.map;

import org.example.entities.enemies.Enemy;
import org.example.entities.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    private TileType type;
    private boolean blocked;
    private boolean explored;
    private boolean hasPlayer;
    private Enemy enemy;
    private List<Item> items;

    public enum TileType {
        WALL,
        FLOOR,
        EMPTY,
        DOOR,
        EXIT;
    }

    public Tile(TileType type) {
        this.type = type;
        this.items = new ArrayList<>();
    }

    //Getters
    public TileType getType() {
        return type;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public boolean isExplored() {
        return explored;
    }

    public boolean isHasPlayer() {
        return hasPlayer;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public List<Item> getItems() {
        return items;
    }

    //Setters
    public void setType(TileType type) {
        this.type = type;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public void setHasPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
