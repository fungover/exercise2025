package org.example.map;

import org.example.map.Tiles;

public class DungeonGrid {
    private Tiles[][] tiles;

    public DungeonGrid(int width, int height) {
        tiles = new Tiles[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                boolean wall = (x == 0 || y == 0 || x == width - 1 || y == height - 1);
                tiles[x][y] = new Tiles(x, y, wall);
            }
        }
    }
    public Tiles getTiles(int x, int y) {

        return tiles[x][y];
    }
    public void printMap(int playerX, int playerY) {
        for (int y = 0; y < tiles[0].length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                if (x == playerX && y == playerY) {
                    System.out.print("P");
                } else if (tiles[x][y].isWall()) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }
}
