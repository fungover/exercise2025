package map;

import entities.Enemy;
import entities.Item;

/**
 * En mycket enkel 2D-karta lagrad som grid[y][x].
 */
public class Dungeon {
    private final int width;
    private final int height;
    private final Tile[][] grid;

    public Dungeon(int width, int height) {
        if (width < 3 || height < 3) {
            throw new IllegalArgumentException("Dungeon måste vara minst 3x3 för ytterväggar.");
        }
        this.width = width;
        this.height = height;
        this.grid = new Tile[height][width];

        // Init: väggar runt kanterna, tomt inuti
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                boolean border = (x == 0 || y == 0 || x == width - 1 || y == height - 1);
                grid[y][x] = new Tile(border ? TileType.WALL : TileType.EMPTY);
            }
        }
    }

    public int getWidth()  { return width; }
    public int getHeight() { return height; }

    /** Returnerar rutan, eller null om utanför kartan */
    public Tile getTile(int x, int y) {
        if (!isWithinBounds(x, y)) return null;
        return grid[y][x];
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public boolean isWalkable(int x, int y) {
        Tile t = getTile(x, y);
        return t != null && !t.isBlocking();
    }

    public void setTileType(int x, int y, String type) {
        Tile t = getTile(x, y);
        if (t != null) {
            t.setType(type);
        }
    }

    public void placeEnemy(int x, int y, Enemy enemy) {
        Tile t = getTile(x, y);
        if (t != null && !t.isBlocking()) {
            t.setEnemy(enemy);
        }
    }

    public void removeEnemy(int x, int y) {
        Tile t = getTile(x, y);
        if (t != null) {
            t.removeEnemy();
        }
    }

    public void placeItem(int x, int y, Item item) {
        Tile t = getTile(x, y);
        if (t != null && !t.isBlocking()) {
            t.setItem(item);
        }
    }

    /** Sätter en start- eller exit-ruta. */
    public void markStart(int x, int y) {
        if (isWithinBounds(x, y)) {
            grid[y][x].setType(TileType.START);
        }
    }

    public void markExit(int x, int y) {
        if (isWithinBounds(x, y)) {
            grid[y][x].setType(TileType.EXIT);
        }
    }
}
