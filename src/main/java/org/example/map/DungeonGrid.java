package org.example.map;

import org.example.utils.RandomUtils;

public class DungeonGrid {
    private int width;
    private int height;
    private Tile[][] tiles;
    private int[] doorPosition;
    private int[] exitPosition;
    private int[] optimalStartPosition;

    public DungeonGrid(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
    }

    // Getters
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Tile[][] getTiles() { return tiles; }
    public int[] getDoorPosition() { return doorPosition; }
    public int[] getExitPosition() { return exitPosition; }
    public int[] getOptimalStartPosition() { return optimalStartPosition; }

    // Methods
    public static DungeonGrid createDungeonGrid(int width, int height) {
        DungeonGrid grid = new DungeonGrid(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    grid.tiles[x][y] = new Tile(Tile.TileType.WALL);
                } else {
                    grid.tiles[x][y] = new Tile(Tile.TileType.FLOOR);
                }
            }
        }

        grid.doorPosition = grid.placeOnPerimeter(Tile.TileType.DOOR);

        do {
            grid.exitPosition = grid.placeOnPerimeter(Tile.TileType.EXIT);
        } while (grid.doorPosition[0] == grid.exitPosition[0] && grid.doorPosition[1] == grid.exitPosition[1]);

        return grid;
    }

    private int[] placeOnPerimeter(Tile.TileType tileType) {
        int x, y;
        int side = RandomUtils.getRandomNumber(0, 3);

        switch (side) {
            case 0:
                x = RandomUtils.getRandomNumber(1, width - 2);
                y = 0;
                if (tileType == Tile.TileType.DOOR) {
                    optimalStartPosition = new int[]{x, y + 1};
                }

                break;
            case 1:
                x = width - 1;
                y = RandomUtils.getRandomNumber(1, height - 2);
                if (tileType == Tile.TileType.DOOR) {
                    optimalStartPosition = new int[]{x - 2, y};
                }
                break;
            case 2:
                x = RandomUtils.getRandomNumber(1, width - 2);
                y = height - 1;
                if (tileType == Tile.TileType.DOOR) {
                    optimalStartPosition = new int[]{x, y - 2};
                }
                break;
            case 3:
                x = 0;
                y = RandomUtils.getRandomNumber(1, height - 2);
                if (tileType == Tile.TileType.DOOR) {
                    optimalStartPosition = new int[]{x + 2, y};
                }
                break;
            default:
                x = 1; y = 0;
        }

        this.tiles[x][y].setType(tileType);
        return new int[]{x, y};
    }
}