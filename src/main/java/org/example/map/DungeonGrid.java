package org.example.map;

import org.example.utils.RandomUtils;

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

        int[] doorPosition = placeOnPerimeter(grid, width, height, Tile.TileType.DOOR);
        int[] exitPosition;
        do {
            exitPosition = placeOnPerimeter(grid, width, height, Tile.TileType.EXIT);
        } while (doorPosition[0] == exitPosition[0] && doorPosition[1] == exitPosition[1]);

        return grid;
    }

    private static int[] placeOnPerimeter(DungeonGrid grid, int width, int height, Tile.TileType tileType) {
        int x, y;
        // 0 = Top, 1 = Right, 2 = Bottom, 3 = Left
        int side = RandomUtils.getRandomNumber(0, 3);

        switch (side) {
            case 0:
                x = RandomUtils.getRandomNumber(1, width - 2);
                y = 0;
                break;
            case 1:
                x = width - 1;
                y = RandomUtils.getRandomNumber(1, height - 2);
                break;
            case 2:
                x = RandomUtils.getRandomNumber(1, width - 2);
                y = height - 1;
                break;
            case 3:
                x = 0;
                y = RandomUtils.getRandomNumber(1, height - 2);
                break;
            default:
                x = 1;
                y = 0;
        }

        grid.tiles[x][y].setType(tileType);
        return new int[]{x, y};
    }

}
