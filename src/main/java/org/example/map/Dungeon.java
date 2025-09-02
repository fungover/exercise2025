package org.example.map;

/*
 * Dungeon = Spelplanen. Här skapas en fast karta med vägg runt om spelplanen.
 * Använder grid[row][col] = grid[y][x]
 */

public class Dungeon {
    // 10 x 10 karta där ytterkanten är vägg
    // Spelbara rutor är 8 x 8
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;

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
    }

    // Kollar om en koordinat (x=kolumn, y=rad) ligger inom kartan
    public boolean inBounds(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
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
