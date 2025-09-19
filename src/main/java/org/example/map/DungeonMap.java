package org.example.map;

/** 2D grid of Tile objects representing the dungeon. */
public final class DungeonMap {
    private final Tile[][] tiles;

    public DungeonMap(int mapWidth, int mapHeight) {
        if (mapWidth <= 0 || mapHeight <= 0) {
            throw new IllegalArgumentException("Map dimensions must be positive");
        }
        tiles = new Tile[mapHeight][mapWidth];
        for (int row = 0; row < mapHeight; row++) {
            for (int column = 0; column < mapWidth; column++) {
                tiles[row][column] = new Tile(TileType.WALL);
            }
        }
    }

    public int width() {
        return tiles[0].length;
    }

    public int height() {
        return tiles.length;
    }

    public boolean isInside(int x, int y) {
        return x >= 0 && y >= 0 && x < width() && y < height();
    }

    public Tile tileAt(int x, int y) {
        return tiles[y][x];
    }
}
