package map;


import entities.Enemy;
import entities.Item;
import entities.Player;

import java.util.List;

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

        //Random placed walls
        for (int x = 2; x < 7; x++) {
            map[3][x] = new Tile(TileType.WALL);
        }
        for (int x = 5; x < 9; x++) {
            map[6][x] = new Tile(TileType.WALL);
        }
        map[7][3] = new Tile(TileType.WALL);
        map[7][2] = new Tile(TileType.WALL);
        map[5][2] = new Tile(TileType.WALL);
        map[5][1] = new Tile(TileType.WALL);
        map[1][4] = new Tile(TileType.WALL);

    }

    public int getHeight() {
        return this.height;
    }

    public void printDungeon(Player player, List<Enemy> enemies) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                if (player.getX() == x && player.getY() == y) {
                    System.out.print(player + "  ");
                    continue;
                }

                boolean enemyFound = false;
                for (Enemy e : enemies) {
                    if (e.getX() == x && e.getY() == y) {
                        System.out.print(e + "  ");
                        enemyFound = true;
                        break;
                    }
                }
                if (enemyFound) continue;

                if (map[y][x].hasItem()) {
                    System.out.print("I  ");
                    continue;
                }

                System.out.print(map[y][x] + "  ");
            }
            System.out.println();
        }
    }

    public boolean isWalkable(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }
        return map[y][x].getType() == TileType.FLOOR;
    }

    public void placeItem(int x, int y, Item item) {
        if (map[y][x].getType() == TileType.FLOOR && !map[y][x].hasItem()) {
            map[y][x].setItem(item);
        }
    }

    public Item getItemAt(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null;
        }
        return map[y][x].getItem();
    }

    public void removeItemAt(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return;
        }
        map[y][x].setItem(null);
    }

    public int getWidth() {
        return this.width;
    }
}


