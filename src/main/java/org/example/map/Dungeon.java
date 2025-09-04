package org.example.map;

import java.util.stream.IntStream;

public class Dungeon {

    private Tile[][] tiles;

    public Dungeon(int rows, int cols) {
        tiles = new Tile[rows][cols];

        IntStream.range(0, rows).forEach(i ->
                IntStream.range(0, cols).forEach(j ->
                        tiles[i][j] = new Tile("Empty")
                )
        );
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    public int getRows() {
        return tiles.length;
    }

    public int getCols() {
        return tiles[0].length;
    }

}
