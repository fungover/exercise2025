package Map;

import Enemy.Enemy;
import Enemy.EnemyRandom;
import Entities.Item;
import Entities.LootRandom;
import Entities.Player;

import java.util.Random;

public class DungeonMap {
    public static final int MAX_WIDTH = 50;
    public static final int MAX_HEIGHT = 50;

    private final int w, h;
    private final Tile[][] grid;
    private final Random rng = new Random();


    private int startX = 1, startY = 1;

    public DungeonMap(int w, int h) {
        if (w <= 0 || h <= 0) throw new IllegalArgumentException("Map size must be > 0");
        if (w < 3 || h < 3) {
            throw new IllegalArgumentException("Map must be at least 3x3 to have an interior floor.");
        }
        if (w > MAX_WIDTH || h > MAX_HEIGHT) {
            throw new IllegalArgumentException("Map too large. Max: " + MAX_WIDTH + "x" + MAX_HEIGHT);
        }
        this.w = w;
        this.h = h;
        this.grid = new Tile[h][w];
        generateBasicLayout();
    }

    public void generateBasicLayout() {
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                boolean border = (x == 0 || y == 0 || x == w - 1 || y == h - 1);
                grid[y][x] = new Tile(border ? TileType.WALL : TileType.FLOOR);
            }
        }
    }

    public void placePlayerAtStart(Player p) {
        this.startX = 1;
        this.startY = 1;
        if (!inBounds(startX, startY) || !tile(startX, startY).isWalkable()) {
            throw new IllegalStateException("Start position must be within bounds and walkable. Ensure w,h >= 3 and layout generated.");
        }
        p.setPos(startX, startY);
        System.out.println("You start at (" + startX + "," + startY + ").");
    }
    public void describeCurrentTile(Player p) {
        Tile t = tile(p.getX(), p.getY());
        System.out.println("You are at (" + p.getX() + "," + p.getY() + ").");

        if (t.getEnemy() != null) {
            System.out.println("There's an enemy here: " + t.getEnemy());
        }
        if (t.getItem() != null) {
            System.out.println("You see an item: " + t.getItem());
        }
        if (!t.getType().isWalkable()) {
            System.out.println("It's a wall... how did you get here?");
        }
    }

    public void populateRandom(double enemyProb, double lootProb, int maxEnemies, int maxLoot) {
        if (enemyProb < 0 || enemyProb > 1 || lootProb < 0 || lootProb > 1) {
            throw new IllegalArgumentException("Probabilities must be 0..1");
        }
        if (maxEnemies < 0 || maxLoot < 0) {
            throw new IllegalArgumentException("maxEnemies and maxLoot must be >= 0");
        }
        int enemiesPlaced = 0;
        int lootPlaced = 0;


        int total = w * h;
        int[] order = new int[total];
        for (int i = 0; i < total; i++) order[i] = i;
        for (int i = total - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            int tmp = order[i]; order[i] = order[j]; order[j] = tmp;
        }

        for (int idx : order) {
            int x = idx % w;
            int y = idx / w;

            if (x == startX && y == startY) continue;

            Tile t = grid[y][x];

            if (!t.isWalkable()) continue;
            if (!t.isEmptyContent()) continue;

            if (enemiesPlaced < maxEnemies && rng.nextDouble() < enemyProb) {
                Enemy e = EnemyRandom.randomAt(x, y);
                t.setEnemy(e);
                enemiesPlaced++;
                continue;
            }

            if (lootPlaced < maxLoot && rng.nextDouble() < lootProb) {
                Item item = LootRandom.randomLoot();
                t.setItem(item);
                lootPlaced++;
            }
        }
        System.out.println("Spawnade " + enemiesPlaced + " enemy's and " + lootPlaced + " loot.");
    }

    public boolean inBounds(int x, int y) { return x >= 0 && y >= 0 && x < w && y < h; }
    public Tile tile(int x, int y) {
        if (!inBounds(x, y)) {
            throw new IndexOutOfBoundsException("Tile out of bounds: (" + x + "," + y + ")");
        }
        return grid[y][x];
    }

}
