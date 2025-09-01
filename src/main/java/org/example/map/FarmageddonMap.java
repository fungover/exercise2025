package org.example.map;

import org.example.entities.Enemy;
import org.example.entities.DroolingDog;
import org.example.entities.GiantHeadlessChicken;
import java.util.Random;

public class FarmageddonMap {
    private Tile[][] grid;
    private int width;
    private int height;
    private final Random rand = new Random();

    public FarmageddonMap(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Tile[height][width];
        generateMap();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    private void generateMap() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new Tile(Tile.Type.PATH); // Default to PATH
                if (rand.nextDouble() < 0.2) {
                    grid[y][x].setType(Tile.Type.WALL);
                }
            }
        }

        // Set player start at a safe location
        grid[0][0].setType(Tile.Type.PLAYER_START);

        // Place enemies
        placeEnemy(new DroolingDog(2, 2));
        placeEnemy(new GiantHeadlessChicken(4, 3));
        placeEnemy(new DroolingDog(6, 1));
        placeEnemy(new GiantHeadlessChicken(1, 5));

        // Place items
        placeItem(3, 3);
        placeItem(5, 2);
    }

    private void placeEnemy(Enemy enemy) {
        int x = enemy.getX();
        int y = enemy.getY();
        if (isValidTile(x, y)) {
            grid[y][x].setType(Tile.Type.ENEMY);
        }
    }

    private void placeItem(int x, int y) {
        if (isValidTile(x, y)) {
            grid[y][x].setType(Tile.Type.ITEM);
        }
    }

    private boolean isValidTile(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height && grid[y][x].getType() == Tile.Type.PATH;
    }

    public Tile getTile(int x, int y) {
        return grid[y][x];
    }
}
