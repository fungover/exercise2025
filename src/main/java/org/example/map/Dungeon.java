package org.example.map;

import org.example.entities.Player;
import org.example.entities.items.Inventory;
import org.example.utils.RandomGenerator;

public class Dungeon {
    private final RandomGenerator rand = new RandomGenerator();
    private final int rows;
    private final int columns;
    private final char[][] grid;
    private boolean printedPlayer = false;

    public Dungeon(Player player) {
        this.rows = 7;
        this.columns = rand.generateNumber(10, 20);
        this.grid = new char[rows][columns];
        generateDungeon(player);
    }

    private void generateDungeon(Player player) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {

                if (x == 0 || x == columns - 1 || y == 0 || y == rows - 1) {
                    grid[y][x] = Tile.WALL.getTile();
                } else if (x < columns - 2 && y % 2 == 0
                        && rand.generateNumber(1, 10) < 7) {
                    grid[y][x] = Tile.WALL.getTile();
                } else {
                    grid[y][x] = getRandomProp(player, x, y);
                }
            }
        }
        grid[rows / 2][columns - 1] = Tile.DOOR.getTile();
    }

    private char getRandomProp(Player player, int x, int y) {
        int randomNum = rand.generateNumber(3, 10);
        if (randomNum == 6) {
            return Tile.ITEM.getTile();
        } else if (!printedPlayer) {
            printedPlayer = true;
            player.setPosition(x, y);
            return Tile.PLAYER.getTile();
        } else if (randomNum > 3) {
            return Tile.FLOOR.getTile();
        } else {
            return Tile.ENEMY.getTile();
        }
    }

    public void printDungeon(Inventory inventory) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                System.out.print(grid[y][x]);
            }
            System.out.println(); // Line break for every row
        }
        System.out.println("Move Up (U)");
        System.out.println("Move Down (D)");
        System.out.println("Move Right (R)");
        System.out.println("Move Left (L)");
        inventory.displayInventory();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public char getTile(int y, int x) {
        return grid[y][x];
    }

    public void setTile(int y, int x, char tile) {
        this.grid[y][x] = tile;
    }
}
