package org.example.map;

import org.example.entities.*;
import java.util.Random;

/*
 * Dungeon = Spelplanen. Här skapas en karta med vägg runt om spelplanen.
 * Inuti slumpas fiender och items beroende på vilket floor man är på.
 */
public class Dungeon {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;

    // EXIT placeras längst ner till höger i spelområdet (8,8)
    private static final int EXIT_X = WIDTH - 2;
    private static final int EXIT_Y = HEIGHT - 2;

    private final Tile[][] grid;
    private final Random random = new Random();

    public Dungeon(int floor) {
        grid = new Tile[HEIGHT][WIDTH];

        // Bygger upp kartan: vägg runtom, tomma rutor inuti
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                boolean edge = (row == 0 || col == 0 || row == HEIGHT - 1 || col == WIDTH - 1);
                grid[row][col] = new Tile(edge ? TileType.WALL : TileType.EMPTY);
            }
        }

        // Slumpa ut items & enemies baserat på vilket floor vi är på
        spawnItems(floor);
        spawnEnemies(floor);

        // Lägg EXIT alltid på sista rutan
        grid[EXIT_Y][EXIT_X] = new Tile(TileType.EXIT);
    }

    // === Slumpa ut Items ===
    private void spawnItems(int floor) {
        int itemCount = (floor == 1 ? 3 : 4); // lite fler på floor 2

        for (int i = 0; i < itemCount; i++) {
            int x, y;
            do {
                x = 1 + random.nextInt(WIDTH - 2);
                y = 1 + random.nextInt(HEIGHT - 2);
            } while (grid[y][x].getType() != TileType.EMPTY || grid[y][x].hasItem());

            Item item;
            if (floor == 1) {
                item = pickRandom(new Item[]{
                        new Item("Small Potion", Item.ItemType.CONSUMABLE, 30),
                        new Item("Iron Sword", Item.ItemType.WEAPON, 10),
                        new Item("Leather Armor", Item.ItemType.ARMOR, 5)
                });
            } else {
                item = pickRandom(new Item[]{
                        new Item("Large Potion", Item.ItemType.CONSUMABLE, 60),
                        new Item("Battle Axe", Item.ItemType.WEAPON, 15),
                        new Item("Magic Sword", Item.ItemType.WEAPON, 20),
                        new Item("Chainmail", Item.ItemType.ARMOR, 10),
                        new Item("Dragon Scale", Item.ItemType.ARMOR, 15)
                });
            }

            grid[y][x].setItem(item);
        }
    }

    // === Slumpa ut Enemies ===
    private void spawnEnemies(int floor) {
        int enemyCount = (floor == 1 ? 3 : 5);

        for (int i = 0; i < enemyCount; i++) {
            int x, y;
            do {
                x = 1 + random.nextInt(WIDTH - 2);
                y = 1 + random.nextInt(HEIGHT - 2);
            } while (grid[y][x].getType() != TileType.EMPTY || grid[y][x].hasEnemy());

            Enemy enemy;
            if (floor == 1) {
                enemy = new Goblin(); // bara goblins på första
            } else {
                int roll = random.nextInt(3);
                if (roll == 0) {
                    enemy = new Goblin();
                } else if (roll == 1) {
                    enemy = new Orc();
                } else {
                    enemy = new Dragon();
                }
            }

            grid[y][x].setEnemy(enemy);
        }
    }

    // === Hjälpmetod: välj slumpmässigt item från array ===
    private Item pickRandom(Item[] items) {
        return items[random.nextInt(items.length)];
    }

    // === Getters / Utility ===
    public boolean inBounds(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    public boolean isWalkable(int x, int y) {
        return inBounds(x, y) && grid[y][x].getType() != TileType.WALL;
    }

    public Tile get(int x, int y) {
        return grid[y][x];
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
}
