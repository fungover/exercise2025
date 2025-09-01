package org.example.map;

public class Tile {
    private TileType type;

    public enum TileType {
        WALL,
        FLOOR,
        EMPTY,
        DOOR,
        EXIT;
    }

    public Tile(TileType type) {
        this.type = type;
    }

    //Getters
    public TileType getType() {
        return type;
    }

    //Setters
    public void setType(TileType type) {
        this.type = type;
    }
}
