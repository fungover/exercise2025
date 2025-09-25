package org.example.utils;

import org.example.entities.Enemy;
import org.example.entities.Item;
import org.example.entities.Player;
import org.example.entities.Tile;

import java.util.Random;

public class RandomUtils {
    private static final Random random = new Random();

    //method to place all random objects
    public static void placeAllObjects(Tile[][] tiles, int wallCount, int itemCount, int enemyCount, Player player) {
        placeWalls(tiles, wallCount, player);
        placeItems(tiles, itemCount, player);
        placeEnemies(tiles, enemyCount, player);
    }

    //method to place walls
    public static void placeWalls(Tile[][] tiles, int wallCount, Player player) {
        int rows = tiles.length;
        int cols = tiles[0].length;

        for (int i = 0; i < wallCount; i++) {
            int x, y;
            do {
                x = random.nextInt(rows);
                y = random.nextInt(cols);
            } while ((x == player.getX()) && (y == player.getY()));

            tiles[x][y].setWalkable(false);
        }
    }

    // method to place items
    public static void placeItems(Tile[][] tiles, int itemCount, Player player) {
        int rows = tiles.length;
        int cols = tiles[0].length;

        for (int i = 0; i < itemCount; i++) {
            int x, y;
            do {
                x = random.nextInt(rows);
                y = random.nextInt(cols);
            } while (!tiles[x][y].isWalkable() || tiles[x][y].getItem() != null || tiles[x][y].getEnemy() !=null || (x == player.getX() && y == player.getY()));

            tiles[x][y].setItem(new Item("Potion", "potion", 30));
        }

        //Place one unique map item
        int mapX, mapY;
        do {
            mapX = random.nextInt(rows);
            mapY = random.nextInt(cols);
        } while (!tiles[mapX][mapY].isWalkable()
                || tiles[mapX][mapY].getItem() != null
                || tiles[mapX][mapY].getEnemy() != null
                || (mapX == player.getX() && mapY == player.getY()));

        tiles[mapX][mapY].setItem(new Item("Dungeon Map", "map", 0));
    }

    public static void placeEnemies(Tile[][] tiles, int enemyCount, Player player) {
        int rows = tiles.length;
        int cols = tiles[0].length;

        for (int i = 0; i < enemyCount; i++) {
            int x, y;
            do {
                x = random.nextInt(rows);
                y = random.nextInt(cols);
            } while (!tiles[x][y].isWalkable() || tiles[x][y].getEnemy() != null || tiles[x][y].getItem() != null || (x == player.getX() && y == player.getY()));

            tiles[x][y].setEnemy(new Enemy("Goblin", 30, 15, x, y));
        }
    }
}
