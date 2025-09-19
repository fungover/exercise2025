package dev.ronja.dungeon.map;

/**
 * Description of what type of tiles I will have in my dungeon map
 * - WALL, FLOOR (CONSTANTS)
 **/

public enum TileType {
    WALL('#', false),
    FLOOR('.', true);

    private final char glyph;
    private final boolean walkable;

    /**
     * Values sent to the constructor:
     * - glyph, walkable. They are stored in the fields
     **/
    TileType(char glyph, boolean walkable) {
        this.glyph = glyph;
        this.walkable = walkable;
    }

    /**
     * Getters to get info when working on the map.
     * Are the tiles walkable or just a wall?
     **/
    public char glyph() {
        return glyph;
    }

    public boolean isWalkable() {
        return walkable;
    }
}

