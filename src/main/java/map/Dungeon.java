package map;

import java.util.Random;

public class Dungeon {

    private final int width, height;
    private final Tile[][] map;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        map = new Tile[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map[y][x] = new Tile(TileType.FLOOR);
            }
        }

        // Walls around the map
        for (int x = 0; x < width; x++) {
            map[0][x] = new Tile(TileType.WALL);
            map[height - 1][x] = new Tile(TileType.WALL);
        }
        for (int y = 0; y < height; y++) {
            map[y][0] = new Tile(TileType.WALL);
            map[y][width - 1] = new Tile(TileType.WALL);
        }



        printDungeon();
    }


    //If you want to print the map
    private void printDungeon() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++)
                System.out.print(map[y][x]);
            System.out.println();
        }
    }

}


