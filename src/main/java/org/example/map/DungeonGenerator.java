package org.example.map;

import org.example.entities.*;
import org.example.utils.RandomGenerator;

public class DungeonGenerator {
    /*
    here we will create the map,
    Tile[][] will give us a 2d array like
    [0][0] ,[0][1] where [height][width]
    [1][0] .[1][1]
     */
    private Tile[][] grid;
    private int width;
    private int height;
    private int playerStartX, playerStartY;

    public DungeonGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Tile[height][width];
        //method to generate the map
        generateDungeon();
    }

    // Getter
    public int getWidth() {return width;}

    public int getHeight() {return height;}

    public int getPlayerStartX() {return playerStartX;}

    public int getPlayerStartY() {return playerStartY;}

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null;
        }
        return grid[y][x];
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height && !grid[y][x].isWall();
    }

    // setter

    // method

    protected void generateDungeon() {
        //creates empty tiles
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new Tile();
            }
        }

        //create walls around the border
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
                    grid[y][x].setWall(true);
                }
            }
        }

        // random walls, so it feels less empty
        for (int i = 0; i < (width * height) / 10; i++) {
            int x = RandomGenerator.nextInt(1, width - 2);
            int y = RandomGenerator.nextInt(1, height - 2);
            grid[y][x].setWall(true);
        }

        //Set player start position
        do {
            playerStartX = RandomGenerator.nextInt(1, width - 2);
            playerStartY = RandomGenerator.nextInt(1, height - 2);
        } while (!grid[playerStartY][playerStartX].isEmpty());

        // add enemies to the dungeon
        addEnemies();
        // add items to the dungeon
        addItems();
    }

    private void addEnemies() {
        //how many enemies we want to spawn between 3-6
        int numEnemies = RandomGenerator.nextInt(3, 6);
        for (int i = 0; i < numEnemies; i++) {
            int x, y;
            do {
                x = RandomGenerator.nextInt(1, width - 2);
                y = RandomGenerator.nextInt(1, height - 2);
            } while (!grid[y][x].isEmpty() || (x == playerStartX && y == playerStartY));

            Enemy enemy = RandomGenerator.nextInt(0, 2) == 0 ? new Goblin() : new Orc();
            enemy.setPosition(x, y);
            grid[y][x].setEnemy(enemy);

        }
    }

    private void addItems() {
        int numItems = RandomGenerator.nextInt(2, 6);
        for (int i = 0; i < numItems; i++) {
            int x, y;
            do {
                x = RandomGenerator.nextInt(1, width - 2);
                y = RandomGenerator.nextInt(1, height - 2);
            } while (!grid[y][x].isEmpty() || (x == playerStartX && y == playerStartY));

            Item item;
            int itemType = RandomGenerator.nextInt(0, 3);
            switch (itemType) {
                case 0:
                    item = new HealthPotion();
                    break;
                case 1:
                    item = new Sword();
                    break;
                default:
                    item = new Dagger();
                    break;
            }
            grid[y][x].setItem(item);
        }
    }

    public void display(Player player) {
        System.out.println("\n=== Dungeon Map ===");

        //Clear player marker
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x].setHasPlayer(false);
            }
        }

        // set current player pos
        grid[player.getY()][player.getX()].setHasPlayer(true);

        // display the map
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(grid[y][x].describe() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
