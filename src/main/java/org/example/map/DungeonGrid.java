package org.example.map;

import org.example.entities.Character;
import org.example.map.Tiles;
import org.example.utils.Helpers;
import org.example.utils.ItemOnMap;
import java.util.List;

public class DungeonGrid {
    private Tiles[][] tiles;

    public DungeonGrid(int width, int height) {
        tiles = new Tiles[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                boolean wall = (x == 0 || y == 0 || x == width - 1 || y == height - 1);
                tiles[x][y] = new Tiles(x, y, wall);
            }
        }
    }
    public Tiles getTiles(int x, int y) {

        return tiles[x][y];
    }
    public int getWidth() {
        return tiles.length;
    }
    public int getHeight() {
        return tiles[0].length;
    }
    public void printMap(int playerX, int playerY,  List<Character> enemies, List<ItemOnMap> items) {
        for (int y = 0; y < tiles[0].length; y++) {
            for (int x = 0; x < tiles.length; x++) {

                if (x == playerX && y == playerY) {
                    System.out.print("P"); // Player on map
                }
                else if (Helpers.containsEnemyAt(x, y, enemies)) {
                    System.out.print("E"); // Enemy on map
                }
                else if (Helpers.containsItemAt(x, y, items)) {
                    System.out.print("I"); // Item on map

                } else if (tiles[x][y].isWall()) {
                    System.out.print("#");
                } else {
                    System.out.print(".");

                }

            }
            System.out.println();
        }
    }
}
