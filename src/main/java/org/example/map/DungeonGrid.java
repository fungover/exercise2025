package org.example.map;

import org.example.map.Tiles;

public class DungeonGrid {
    private Tiles[][] tiles;

    public DungeonGrid(int width, int height) {
        tiles = new Tiles[width][height];
        // Initialize grid with walls
    }
    public Tiles getTiles(int x, int y) {
        return tiles[x][y];
    }
}
