package org.example.service;

import org.example.entities.Enemy;
import org.example.entities.Player;
import org.example.utils.Enemies;
import org.example.entities.items.Inventory;
import org.example.entities.items.Item;
import org.example.map.Dungeon;
import org.example.map.Tile;
import org.example.utils.ItemsOnFloor;

import java.util.Scanner;

public class GameLogic {
    // All tiles in the game
    private final char playerTile = Tile.PLAYER.getTile();
    private final char floorTile = Tile.FLOOR.getTile();
    private final char wallTile = Tile.WALL.getTile();
    private final char itemTile = Tile.ITEM.getTile();
    private final char enemyTile = Tile.ENEMY.getTile();
    private final char doorTile = Tile.DOOR.getTile();

    public void moveInput(Dungeon d, Player p, String userInput) {
        userInput = userInput.toLowerCase();

        int x = p.getX();
        int y = p.getY();

        switch (userInput) {
            case "u":
                if (isWalkable(d, y - 1, x)) {
                    p.setPosition(x, y - 1);
                    System.out.println(p.getName() + " moved up.");
                } else if (isWall(d, y - 1, x)) {
                    wallMessage(p);
                }
                break;
            case "d":
                if (isWalkable(d, y + 1, x)) {
                    p.setPosition(x, y + 1);
                    System.out.println(p.getName() + " moved down.");
                } else if (isWall(d, y + 1, x)) {
                    wallMessage(p);
                }
                break;
            case "r":
                if (isWalkable(d, y, x + 1)) {
                    p.setPosition(x + 1, y);
                    System.out.println(p.getName() + " moved to the right.");
                } else if (isWall(d, y, x + 1)) {
                    wallMessage(p);
                }
                break;
            case "l":
                if (isWalkable(d, y, x - 1)) {
                    p.setPosition(x - 1, y);
                    System.out.println(p.getName() + " moved to the left.");
                } else if (isWall(d, y, x - 1)) {
                    wallMessage(p);
                }
                break;
            default:
                System.out.println("Invalid input, only type what is displayed.");
                break;
        }
    }

    private boolean isWalkable(Dungeon d, int y, int x) {
        return inBounds(d, y, x) && (d.getTile(y, x) == floorTile || d.getTile(y, x) == doorTile);
    }

    private boolean isWall(Dungeon d, int y, int x) {
        return !inBounds(d, y, x) && d.getTile(y, x) == wallTile;
    }

    public boolean isDoor(Dungeon d, int y, int x) {
        return inBounds(d, y, x) && d.getTile(y, x) == doorTile;
    }

    private boolean inBounds(Dungeon d, int y, int x) {
        return y >= 0 && y < d.getRows() && x >= 0 && x < d.getColumns();
    }

    private void wallMessage(Player p) {
        System.out.println("There is a wall blocking " + p.getName() + " from going further.");
    }

    public Enemy getEnemyFromList(Dungeon d, Player p, Enemies enemies) {
        int x = p.getX();
        int y = p.getY();

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] dxy : dirs) {
            int ny = y + dxy[0], nx = x + dxy[1];
            if (inBounds(d, ny, nx) && d.getTile(ny, nx) == enemyTile) {
                return enemies.getEnemyOnFloor(nx, ny);
            }
        }
        System.out.println("There is no enemy here.");
        return null;
    }

    public Item getItemFromList(Dungeon d, Player p, ItemsOnFloor items) {
        int x = p.getX();
        int y = p.getY();

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] dxy : dirs) {
            int ny = y + dxy[0], nx = x + dxy[1];
            if (inBounds(d, ny, nx) && d.getTile(ny, nx) == itemTile) {
                return items.getItemOnFloor(nx, ny);
            }
        }
        System.out.println("There is no item here.");
        return null;
    }

    public boolean wishToFightEnemy(Dungeon d, Player p, Inventory i, Scanner scan) {
        if (hasAdjacent(d, p, enemyTile)) {
            System.out.println("There's an enemy here!");
            System.out.println("Do you wish to fight it, yes or no?");
            return validAnswer(scan);
        }
        return false;
    }

    public boolean wishToPickUpItem(Dungeon d, Player p, Inventory i, Scanner scan) {
        if (hasAdjacent(d, p, itemTile)) {
            System.out.println("There's an item here!");
            System.out.println("Do you wish to pick it up, yes or no?");
            return validAnswer(scan);
        }
        return false;
    }

    private boolean hasAdjacent(Dungeon d, Player p, char targetTile) {
        int x = p.getX(), y = p.getY();
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] dxy : dirs) {
            int ny = y + dxy[0], nx = x + dxy[1];
            if (inBounds(d, ny, nx) && d.getTile(ny, nx) == targetTile) {
                return true;
            }
        }
        return false;
    }

    private boolean validAnswer(Scanner scan) {
        while (true) {
            System.out.print("Y/N: ");
            String userInput = scan.nextLine();
            if (userInput.equalsIgnoreCase("y")) {
                System.out.println();
                return true;
            } else if (userInput.equalsIgnoreCase("n")) {
                return false;
            } else {
                System.out.println("Invalid input, only type what is displayed.");
            }
        }
    }

    public void pickUpItem(Item newItem, Inventory inventory, Class<? extends Item> itemClass) {
        Item pickedUpItem;
        if (inventory.getItem(itemClass) != null) {
            pickedUpItem = inventory.getItem(itemClass);
        } else {
            pickedUpItem = newItem;
        }
        inventory.addItem(pickedUpItem);
        System.out.println("A " + pickedUpItem.getType() + " has been picked up!");
    }

}
