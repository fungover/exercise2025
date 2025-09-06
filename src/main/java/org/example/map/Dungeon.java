
package org.example.map;
import org.example.entities.Position;

import java.util.Random;

public class Dungeon {
    private Tile[][] grid;
    private int width;
    private int height;

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Tile[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new Tile(new Position(x, y), TileType.FLOOR);
            }
        }
    }

    // A new constructor to make sure the player does not get stuck behind walls as they start. Kept the old one for backward compatibility.
    public Dungeon(int width, int height, Position playerStart) {
        this.width = width;
        this.height = height;
        grid = new Tile[height][width];
        Random rand = new Random();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new Tile(new Position(x, y), TileType.FLOOR);
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Position pos = new Position(x, y);
                if (rand.nextDouble() < 0.2 && !isInSafeZone(pos, playerStart)) {
                    grid[y][x].setType(TileType.WALL);
                }
            }
        }
    }

    private boolean isInSafeZone(Position pos, Position playerStart) {
        return Math.abs(pos.x() - playerStart.x()) <= 1 &&
                Math.abs(pos.y() - playerStart.y()) <= 1;
    }

    public Tile getTile (Position pos) {
        if (pos.x() < 0 || pos.x() >= width || pos.y() < 0 || pos.y() >= height)
            return null;
        return grid[pos.y()][pos.x()];
    }


    public void printDungeon(Position playerPos) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = grid[y][x];

                if (playerPos.x() == x && playerPos.y() == y) {
                    System.out.print("P ");
                } else if (tile.getEnemy() != null && tile.getEnemy().isAlive()) {
                    System.out.print("E ");
                } else if (tile.getItem() != null) {
                    System.out.print("I ");
                } else if (tile.getType() == TileType.WALL) {
                    System.out.print("# ");
                } else if (tile.getType() == TileType.FLOOR) {
                    System.out.print(". ");
                } else {
                    System.out.print("? ");
                }
            }
            System.out.println();
        }
    }
}