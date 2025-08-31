package org.example.map;

import org.example.entities.Tile;

public class DungeonGenerator {
    /*
    here we will create the map,
    Tile[][] will give us a 2d array like
    [0][0] ,[0][1] where [height][width]
    [1][0] .[1][1]
     */
    private Tile[][] grid;
    private int width;
    private int height;
    private int playerStartX, playerStartY;

    public DungeonGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Tile[height][width];
        //method to generate the map
    }

    // Getter
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPlayerStartX() {
        return playerStartX;
    }

    public int getPlayerStartY() {
        return playerStartY;
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null;
        }
        return grid[y][x];
    }


}
