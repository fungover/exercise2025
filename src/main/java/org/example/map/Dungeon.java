package org.example.map;

public class Dungeon {
    private final int rows;
    private final int columns;
    private final Tile[][] grid;

    public Dungeon(int rows, int columns) { //the number of rows and columns in the dungeon
        this.rows = rows;
        this.columns = columns;
        this.grid = new Tile[rows][columns]; //2D array of Tile objects
        generateDungeon(); //method to populate the grid with walls and floors
    }

    private void generateDungeon() {

        for (int y = 0; y < rows; y++) { // loop through each row
            for (int x = 0; x < columns; x++) {  // loop through each column

                if (x == 0 || x == columns - 1 || y == 0 || y == rows - 1) { // create walls around the edges
                    grid[y][x] = new Tile(TileType.WALL); // outer walls
                } else if ((x == 3 && y > 1 && y < 5) || (x == 6 && y > 3 && y < 7)) { // create some internal walls
                    grid[y][x] = new Tile(TileType.WALL); // internal walls
                } else {
                    grid[y][x] = new Tile(TileType.FLOOR); // fill the rest with floors
                }
            }
        }
    }

    public void printMap() {
        for (int y = 0; y < rows; y++) { // loop through each row
            for (int x = 0; x < columns; x++) { // loop through each column
                System.out.print(grid[y][x].getSymbol()); // print the symbol of each tile
            }
            System.out.println(); // new line after each row
        }
    }

    public Tile getTile(int x, int y) { // method to get a tile at specific coordinates
        if (x >= 0 && x < columns && y >= 0 && y < rows) { // check bounds
            return grid[y][x]; // return the tile if within bounds
        }
        return null; // return null if out of bounds
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }
}
