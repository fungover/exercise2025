package org.example.map;

import org.example.entities.DroolingDog;
import org.example.entities.Enemy;
import org.example.entities.GiantHeadlessChicken;
import org.example.entities.HealingMilk;
import org.example.entities.Item;
import org.example.entities.Manifesto;
import org.example.entities.Pitchfork;
import org.example.utils.RandomUtils;

public class FarmageddonMap {
    private Tile[][] grid;
    private int width;
    private int height;

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
                if (RandomUtils.chance(0.2)) {
                    grid[y][x].setType(Tile.Type.WALL);
                }
            }
        }

        // Set player start at a safe location
        grid[0][0].setType(Tile.Type.PLAYER_START);

        // Ensure fixed coords are PATH before placing fixed entities
        ensurePath(2,2);
        ensurePath(4, 3);
        ensurePath(6, 1);
        ensurePath(1, 5);
        ensurePath(3, 4);
        ensurePath(5, 2);
        ensurePath(1, 1);
        ensurePath(7, 4);

        // Place enemies
        placeEnemy(new DroolingDog(2, 2));
        placeEnemy(new GiantHeadlessChicken(4, 3));
        placeEnemy(new DroolingDog(6, 1));
        placeEnemy(new GiantHeadlessChicken(1, 5));

        // Place items
        placeItem(new HealingMilk(3, 4));
        placeItem(new HealingMilk(5, 2));
        placeItem(new Pitchfork(1, 1));
        placeItem(new Pitchfork(7, 4));

        int[] coords = getRandomValidCoordinates();
        placeItem(new Manifesto(coords[0], coords[1]));
    }

    private void placeEnemy(Enemy enemy) {
        int x = enemy.getX();
        int y = enemy.getY();
        if (isValidTile(x, y)) {
            grid[y][x].setType(Tile.Type.ENEMY);
            grid[y][x].setEnemy(enemy);
        }
    }

    private void placeItem(Item item) {
        int x = item.getX();
        int y = item.getY();
        if (isValidTile(x, y)) {
            grid[y][x].setType(Tile.Type.ITEM);
            grid[y][x].setItem(item);
        }
    }

    boolean isValidTile(int x, int y) {
        return x >= 0 && x < width
            && y >= 0 && y < height
            && grid[y][x].getType() == Tile.Type.PATH;
    }

    private void ensurePath(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            grid[y][x].setType(Tile.Type.PATH);
        }
    }

    private int[] getRandomValidCoordinates() {
            for (int i = 0; i < 100; i++) {
                int x = RandomUtils.randomInt(0, width);
                int y = RandomUtils.randomInt(0, height);
                   if (isValidTile(x, y)) return new int[]{x, y};
            }
         // Fallback: scan deterministically
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (isValidTile(x, y)) return new int[]{x, y};
            }
        }
           throw new RuntimeException("No valid PATH tile found for Manifesto placement.");
    }

    public Tile getTile(int x, int y) {
        return grid[y][x];
    }
}
