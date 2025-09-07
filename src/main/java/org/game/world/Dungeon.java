package org.game.world;

import org.game.utils.Colors;
import org.game.utils.RandomGenerator;

public class Dungeon {
    private Tile [][] map;
    private int width;
    private int height;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        map = new Tile[width][height];
        generateDungeon();
    }


    private void generateDungeon() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x== 0 || y == 0 || x == width - 1 || y == height - 1) {
                    map[x][y] = new Tile (false,"[#]");
                } else  {
                    Tile tile = new Tile (true,"[ ]");

                    if (RandomGenerator.chance(20)) {tile.setRoom(new Room("enemy"));}
                    else if (RandomGenerator.chance(10)) {tile.setRoom(new Room("treasure"));
                    }else if (RandomGenerator.chance(5)) {tile.setRoom(new Room("boss"));
                    }

                    map[x][y] = tile;

                }
            }
        }
    }

    public void printDungeon(int playerX, int playerY) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == playerX && y == playerY) {
                    System.out.print(Colors.cyanColor + "[P]" + Colors.resetColor);
                } else {
                    System.out.print(map[x][y].getSymbol());
                }
            }
            System.out.println();
        }
    }


    public boolean isWalkable(int x, int y) {
        if (x < 0 || y < 0 || x > width - 1 || y > height - 1) return false;
        return map[x][y].isWalkable();
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x > width - 1 || y > height - 1) return null;
        return map[x][y];
    }


}