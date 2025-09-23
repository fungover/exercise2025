package org.example.map;

import org.example.entities.Boss;
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

                if (roll < 0.25) {
                    grid[x][y] = new Tile(TileEnum.CHEST, null, new Item("Health Potion", "health_potion"));
                } else if (roll < 0.6) {
                    grid[x][y] = new Tile(TileEnum.ENEMY, new Enemy(getRandomEnemy(), 20, 10, x, y), null);
                } else {
                    grid[x][y] = new Tile(TileEnum.EMPTY, null, null);
                }

            }
        }

        grid[0][0] =  new Tile(TileEnum.EMPTY, null, null);
        grid[5][0] =  new Tile(TileEnum.ENEMY, new Boss("Elder Dragon", 50, 20, 0, 1, "Flaming Breath"), null);
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
