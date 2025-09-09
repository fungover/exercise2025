package org.example.map;

/*
 * Dungeon = Spelplanen. Här skapas en fast karta med vägg runt om spelplanen.
 * Använder grid[row][col] = grid[y][x]
 */

import org.example.utils.RandomUtils;
import java.util.Random;
import org.example.entities.*;

public class Dungeon {
    // 10 x 10 karta där ytterkanten är vägg
    // Spelbara rutor är 8 x 8
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;

    // EXIT/STAIRS placeras längst ner till höger i spelområdet (8,8)
    private static final int EXIT_X = WIDTH - 2;
    private static final int EXIT_Y = HEIGHT - 2;

    // 2D array med Tile objekt [rad][kolumn] = [y][x]
    private final Tile[][] grid;
    private static final java.util.Random RNG = new java.util.Random();

    public Dungeon(int floor) {
        grid = new Tile[HEIGHT][WIDTH];

        // Bygger upp kartan med väggar på kanterna och tomma rutor inuti
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                boolean edge = (row == 0 || col == 0 || row == HEIGHT - 1 || col == WIDTH - 1);
                TileType type = edge ? TileType.WALL : TileType.EMPTY;
                grid[row][col] = new Tile(type);
            }
        }

        // === Viktigt: placera STAIRS eller EXIT först ===
        if (floor == 1) {
            grid[EXIT_Y][EXIT_X] = new Tile(TileType.STAIRS);
        } else {
            grid[EXIT_Y][EXIT_X] = new Tile(TileType.EXIT);
        }

        // Slumpa fiender och items (vanliga tiles) + miniboss/boss
        placeEnemiesAndItems(floor);
    }

    /**
     * Slumpar ut vanliga fiender och items på dungeon-golvet.
     * Lägger dessutom en miniboss på STAIRS och en boss på EXIT.
     */
    private void placeEnemiesAndItems(int floor) {
        for (int y = 1; y < HEIGHT - 1; y++) {
            for (int x = 1; x < WIDTH - 1; x++) {
                // Hoppa över startpositionen
                if (x == 1 && y == 1) continue;

                Tile tile = grid[y][x];

                if (tile.getType() == TileType.EMPTY) {
                    if (RandomUtils.chance(0.20)) {
                        // 20% chans att tile får en fiende
                        tile.setEnemy(RandomUtils.randomEnemy(floor));

                    } else if (RandomUtils.chance(0.25)) {
                        // 25% av 80% dvs 20% chans (om ingen fiende) att tile får ett item
                        int roll = RNG.nextInt(3);
                        switch (roll) {
                            case 0 -> tile.setItem(RandomUtils.randomWeapon());
                            case 1 -> tile.setItem(RandomUtils.randomArmor());
                            case 2 -> tile.setItem(RandomUtils.randomPotion());
                        }
                    }
                }
            }
        }

        // Sätter Dragon på STAIRS
        Tile stairsTile = findTile(TileType.STAIRS);
        if (stairsTile != null) {
            stairsTile.setEnemy(new Dragon());
        }

        // Sätter boss på EXIT
        Tile exitTile = findTile(TileType.EXIT);
        if (exitTile != null) {
            exitTile.setEnemy(new FinalBoss());
        }
    }

    // Hjälpfunktion för att hitta en tile av en viss typ (exempelvis STAIRS/EXIT).
    private Tile findTile(TileType type) {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (grid[y][x].getType() == type) {
                    return grid[y][x];
                }
            }
        }
        return null;
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