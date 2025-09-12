package org.example.map;

import org.example.entities.Enemy;

public class MapCreation {
    private final Tile[][] grid;

    public MapCreation(int width, int height) {
        grid = new Tile[width][height];
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                grid[x][y] = new Tile(TileEnum.EMPTY, null);
            }
        }

        grid[2][5] = new Tile(TileEnum.ENEMY, new Enemy("Skeleton", 20, 4, 2, 2));
    }

    public Tile getTile(int x, int y) {
        return grid[x][y];
    }

    public boolean insideMap(int x, int y) {
        if (x < 0 || x > grid.length) {
            return false;
        } else if (y < 0 || y > grid[0].length) {
            return false;
        }

        return true;
    }
}
