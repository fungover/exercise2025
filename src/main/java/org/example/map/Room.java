package org.example.map;

import org.example.utils.RandomGenerator;

public class Room {
    private final RandomGenerator rand = new RandomGenerator();
    private final int rows;
    private final int columns;
    private final char[][] grid;
    private boolean printedPlayer = false;

    public Room() {
        this.rows = 7;
        this.columns = rand.generateNumber(10, 20);
        this.grid = new char[rows][columns];
        generateRoom();
    }

    private void generateRoom() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {

                if (x == 0 || x == columns - 1 || y == 0 || y == rows - 1) {
                    grid[y][x] = Tile.WALL.getTile();
                } else if (x < columns - 2 && y % 2 == 0
                        && rand.generateNumber(1, 10) < 7) {
                    grid[y][x] = Tile.WALL.getTile();
                } else {
                    grid[y][x] = getRandomProp();
                }
            }
        }
        grid[rows / 2][columns - 1] = Tile.DOOR.getTile();
    }

    private char getRandomProp() {
        int randomNum = rand.generateNumber(3, 10);
        if (randomNum == 6) {
            return Tile.ITEM.getTile();
        } else if (!printedPlayer) {
            printedPlayer = true;
            return Tile.PLAYER.getTile();
        } else if (randomNum > 3) {
            return Tile.FLOOR.getTile();
        } else {
            return Tile.ENEMY.getTile();
        }
    }

    public void printRoom() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                System.out.print(grid[y][x]);
            }
            System.out.println(); // Line break for every row
        }
    }
}
