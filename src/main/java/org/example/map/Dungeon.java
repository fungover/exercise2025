package org.example.map;

/*
 * Dungeon = Spelplanen. Här skapas en fast karta med vägg runt om spelplanen.
 * Använder grid[row][col] = grid[y][x]
 */

import org.example.entities.Enemy;

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

    public Dungeon() {

        // Första index motsvarar rader (HEIGHT) och andra index kolumner (WIDTH)
        grid = new Tile[HEIGHT][WIDTH];

        // Bygger upp kartan med väggar på kanterna och tomt inuti
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                boolean edge = (row == 0 || col == 0 || row == HEIGHT -1 || col == WIDTH -1);

                TileType type;
                if (edge) {
                    type = TileType.WALL;
                } else {
                    type = TileType.EMPTY;
                }

                grid[row][col] = new Tile(type);
            }
        }

        // Lägg till en test fiende på ruta (4,4)
        Enemy enemy = new Enemy("Enemy", 20, 5);
        grid[4][4].setEnemy(enemy);

        // Placerar EXIT längst ner till höger på kartan
        grid[EXIT_Y][EXIT_X] = new Tile(TileType.EXIT);
    }

    // Kollar om en koordinat (x=kolumn, y=rad) ligger inom kartan
    public boolean inBounds(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    // kollar om en ruta går att gå på (inom kartan och inte är vägg)
    public boolean isWalkable(int x, int y) {
        return inBounds(x, y) && grid[y][x].getType() != TileType.WALL;
    }

    // Hämtar rutan på koodinat (x=kolumn, y=rad)
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
