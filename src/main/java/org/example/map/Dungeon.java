package org.example.map;

import org.example.entities.Position;

public class Dungeon {
    private Tile[][] grid;
    private int width;
    private int height;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Tile[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new Tile(new Position(x, y), TileType.FLOOR);
            }
        }
    }

    public Tile getTile (Position pos) {
        if (pos.x() < 0 || pos.x() >= width || pos.y() < 0 || pos.y() >= height)
            return null;
        return grid[pos.y()][pos.x()];
    }


    public void printDungeon(Position playerPos) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (playerPos.x() == x && playerPos.y() == y) System.out.print("P ");
                else System.out.print(grid[y][x].getType() == TileType.FLOOR ? ". " : "# ");
            }
            System.out.println();
        }

    }
}