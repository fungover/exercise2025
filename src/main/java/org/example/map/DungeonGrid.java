package org.example.map;

public class DungeonGrid {
    private int width;
    private int height;
    private Tile[][] tiles;

    public DungeonGrid(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
    }

    //Getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    //Setters
    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    //Methods
    public static DungeonGrid createDungeonGrid(int width, int height) {
        DungeonGrid grid = new DungeonGrid(width, height);

        for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    //Generate Walls
                    if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                        grid.tiles[x][y] = new Tile(Tile.TileType.WALL);
                    }
                    //Generate Floors
                    else {
                        grid.tiles[x][y] = new Tile(Tile.TileType.FLOOR);
                    }
                }
            }

        grid.tiles[1][0].setType(Tile.TileType.DOOR);
        grid.tiles[width - 1][height - 1].setType(Tile.TileType.EXIT);

        return grid;
    }

}
