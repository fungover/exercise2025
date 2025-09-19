package org.example.map;

public enum Tile {
    WALL('#'),
    FLOOR(' '),
    PLAYER('@'),
    ENEMY('E'),
    ITEM('I'),
    DOOR('D');

    private final char tile;

    Tile(char tile) {
        this.tile = tile;
    }

    public char getTile() {
        return tile;
    }
}
