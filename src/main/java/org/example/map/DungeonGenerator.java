package org.example.map;

import org.example.entities.Tile;

public class DungeonGenerator {

    public static Tile[][] generateDungeon(int rows, int cols) {
        Tile[][] dungeon = new Tile[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dungeon[i][j] = new Tile(true);
            }
        }
        return dungeon;
    }
}