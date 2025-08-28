package org.example.map;

public class Tile {

    private final TileType type;

    public Tile(TileType type) {
        this.type = type;
    }

    public TileType getType() {
        return type;
    }

    public char getSymbol() {
        return type.getSymbol();
    }

    public boolean isWalkable() {
        return type.isWalkable();
    }

    @Override
    public String toString() {
        return String.valueOf(getSymbol());
    }
}
