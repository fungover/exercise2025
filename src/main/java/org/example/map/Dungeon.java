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

    public void printDungeon() {
        System.out.println("~~~~~~~~~~");
        System.out.println(" DUNGEON");
        System.out.println("~~~~~~~~~~");
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                Tile tile = tiles[i][j];
                char symbol;

                if (tile.getEnemy() != null) {
                    symbol = 'E';
                } else if (tile.getItem() != null) {
                    symbol = 'I';
                } else {
                    symbol = '.';
                }
                System.out.print(symbol + " ");
            }
            System.out.println();
        }
    }
}
