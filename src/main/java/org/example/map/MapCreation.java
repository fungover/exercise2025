package org.example.map;

import org.example.entities.Enemy;
import org.example.entities.Item;
import java.util.Random;

public class MapCreation {
    private final Tile[][] grid;

    public MapCreation(int width, int height) {
        Random rand = new Random();
        grid = new Tile[width][height];
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                double roll = rand.nextDouble();

                if (roll < 0.20) {
                    grid[x][y] = new Tile(TileEnum.CHEST, null, new Item("Health potion", "health_potion"));
                } else if (roll < 0.40) {
                    grid[x][y] = new Tile(TileEnum.ENEMY, new Enemy(getRandomEnemy(), 20, 5, x, y), null);
                } else {
                    grid[x][y] = new Tile(TileEnum.EMPTY, null, null);
                }

            }
        }
    }

    public Tile getTile(int x, int y) {
        return grid[x][y];
    }

    public boolean insideMap(int x, int y) {
        if (x < 0 || x > grid.length) {
            return false;
        } else if (y < 0 || y > grid[0].length) {
            return false;
        }

        return true;
    }

    private String getRandomEnemy() {
        String[] Enemies = {"Skeleton", "Knight", "Orc"};

        int index = new Random().nextInt(Enemies.length);
        return Enemies[index];
    }
}
