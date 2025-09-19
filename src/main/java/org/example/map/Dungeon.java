package org.example.map;

import org.example.entities.Enemy;
import org.example.entities.Player;
import org.example.entities.items.HealthPotion;
import org.example.utils.Enemies;
import org.example.entities.items.Inventory;
import org.example.entities.items.Item;
import org.example.utils.ItemsOnFloor;
import org.example.utils.RandomGenerator;

public class Dungeon {
    private final RandomGenerator rand = new RandomGenerator();
    private final int rows;
    private final int columns;
    private final char[][] grid;
    private boolean printedPlayer = false;

    public Dungeon(Player p, Enemies e, ItemsOnFloor i) {
        this.rows = 7;
        this.columns = rand.generateNumber(10, 20);
        this.grid = new char[rows][columns];
        generateDungeon(p, e, i);
    }

    private void generateDungeon(Player p, Enemies e, ItemsOnFloor i) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {

                if (x == 0 || x == columns - 1 || y == 0 || y == rows - 1) {
                    grid[y][x] = Tile.WALL.getTile();
                } else if (x < columns - 2 && y % 2 == 0
                        && rand.generateNumber(1, 10) < 7) {
                    grid[y][x] = Tile.WALL.getTile();
                } else {
                    grid[y][x] = setRandomProp(p, e, i, x, y);
                }
            }
        }
        grid[rows / 2][columns - 1] = Tile.DOOR.getTile();
    }

    private char setRandomProp(Player p, Enemies e, ItemsOnFloor items, int x, int y) {
        int randomNum = rand.generateNumber(3, 10);
        if (!printedPlayer) {
            printedPlayer = true;
            p.setPosition(x, y);
            return Tile.PLAYER.getTile();
        } else if (randomNum == 6) {
            Item item = new HealthPotion(1);
            item.setPosition(x, y);
            items.addToList(item);
            return Tile.ITEM.getTile();
        } else if (randomNum > 3) {
            return Tile.FLOOR.getTile();
        } else {
            Enemy enemy = rand.randomEnemy(rand);
            enemy.setPosition(x, y);
            e.addToList(enemy);
            return Tile.ENEMY.getTile();
        }
    }

    public void printDungeon(Inventory i) {
        // "Clear" the terminal
        for (int n = 0; n < 50; n++) {
            System.out.println();
        }

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
        i.displayInventory();
    }

    public void setPlayerPosition(Player p) {
        char playerTile = Tile.PLAYER.getTile();
        char floorTile = Tile.FLOOR.getTile();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (getTile(i, j) == playerTile) {
                    setTile(i, j, floorTile);
                }
            }
        }
        setTile(p.getY(), p.getX(), playerTile);
    }

    public void removeEnemyTile(Enemy e) {
        setTile(e.getY(), e.getX(), Tile.FLOOR.getTile());
    }

    public void removeItemTile(Item i) {
        setTile(i.getY(), i.getX(), Tile.FLOOR.getTile());
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
