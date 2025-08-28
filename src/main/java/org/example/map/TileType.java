package org.example.map;

public enum TileType {
    WALL('#', false),
    FLOOR('.', true),
    PLAYER('P', true),
    ENEMY('E', true),
    ITEM('I', true);

    private final char symbol;
    private final boolean walkable;

    TileType(char symbol, boolean walkable) {
        this.symbol = symbol;
        this.walkable = walkable;
    }

    public char getSymbol() {
        return symbol;
    }
    public boolean isWalkable() {
        return walkable;
    }
}
