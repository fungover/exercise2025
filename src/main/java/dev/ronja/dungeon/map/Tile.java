package dev.ronja.dungeon.map;

/**
 * One Tile in the map, dynamic and scalable
 **/
public class Tile {
    private TileType type;

    public Tile(TileType type) {
        this.type = type;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public boolean isWalkable() {
        return type.isWalkable();
    }

    public char glyph() {
        return type.glyph();
    }
}
