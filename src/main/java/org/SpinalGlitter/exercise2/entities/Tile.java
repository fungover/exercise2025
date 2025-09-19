package org.SpinalGlitter.exercise2.entities;

public class Tile {
    private final boolean walkable;
    private final String symbol;

    public Tile(boolean walkable, String symbol) {
        this.walkable = walkable;
        this.symbol = symbol;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public String getSymbol() {
        return symbol;
    }
}
