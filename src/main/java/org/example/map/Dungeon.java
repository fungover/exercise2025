package org.example.map;

/*
 * Dungeon = Spelplanen. Här skapas en fast karta med vägg runt om spelplanen.
 * Använder grid[row][col] = grid[y][x]
 */

import org.example.entities.Enemy;
import org.example.entities.Item;
import org.example.utils.RandomUtils;

public class Dungeon {
    // 10 x 10 karta där ytterkanten är vägg
    // Spelbara rutor är 8 x 8
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;

    // EXIT placeras längst ner till höger i spelområdet (8,8)
    private static final int EXIT_X = WIDTH - 2;
    private static final int EXIT_Y = HEIGHT - 2;

    // 2D array med Tile objekt [rad][kolumn] = [y][x]
    private final Tile[][] grid;

    public Dungeon(int floor) {
        grid = new Tile[HEIGHT][WIDTH];

        // Bygger upp kartan med väggar på kanterna och tomt inuti
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                boolean edge = (row == 0 || col == 0 || row == HEIGHT - 1 || col == WIDTH - 1);

                TileType type = edge ? TileType.WALL : TileType.EMPTY;
                grid[row][col] = new Tile(type);
            }
        }

        // Slumpa ut fiender och items beroende på floor
        placeEnemiesAndItems(floor);

        // Placerar STAIRS på floor 1 och EXIT på sista floor
        if (floor == 1) {
            grid[EXIT_Y][EXIT_X] = new Tile(TileType.STAIRS);
        } else {
            grid[EXIT_Y][EXIT_X] = new Tile(TileType.EXIT);
        }
    }

    private void placeEnemiesAndItems(int floor) {
        for (int y = 1; y < HEIGHT - 1; y++) {
            for (int x = 1; x < WIDTH - 1; x++) {
                // Hoppa över startpositionen
                if (x == 1 && y == 1) continue;

                // Fiender – större chans på senare floor
                if (RandomUtils.chance(floor == 1 ? 0.10 : 0.20)) {
                    grid[y][x].setEnemy(RandomUtils.randomEnemy());
                }

                // Items
                if (RandomUtils.chance(0.05)) {
                    grid[y][x].setItem(RandomUtils.randomWeapon());
                } else if (RandomUtils.chance(0.05)) {
                    grid[y][x].setItem(RandomUtils.randomArmor());
                } else if (RandomUtils.chance(0.05)) {
                    grid[y][x].setItem(RandomUtils.randomPotion());
                }
            }
        }
    }

    // Kollar om en koordinat (x=kolumn, y=rad) ligger inom kartan
    public boolean inBounds(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    // Kollar om en ruta går att gå på (inom kartan och inte är vägg)
    public boolean isWalkable(int x, int y) {
        return inBounds(x, y) && grid[y][x].getType() != TileType.WALL;
    }

    // Hämtar rutan på koordinat (x=kolumn, y=rad)
    public Tile get(int x, int y) {
        return grid[y][x];
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
}