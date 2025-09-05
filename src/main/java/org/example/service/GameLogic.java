package org.example.service;

import org.example.entities.Player;
import org.example.map.Dungeon;
import org.example.map.Tile;

public class GameLogic {

    public void renderPlayerPos(Dungeon d, int x, int y) {
        // Here grid[i][j] == grid[y][x] from Room class
        for (int i = 0; i < d.getRows(); i++) {
            for (int j = 0; j < d.getColumns(); j++) {
                if (d.getTile(i, j) == Tile.PLAYER.getTile()) {
                    d.setTile(i, j, Tile.FLOOR.getTile());
                }
            }
        }
        d.setTile(y, x, Tile.PLAYER.getTile());
    }

    public void moveInput(Dungeon d, Player p, String userInput) {
        userInput = userInput.toLowerCase();

        int x = p.getX();
        int y = p.getY();

        switch (userInput) {
            case "move north", "n":
                if (isWalkable(d, y - 1, x)) {
                    p.setPosition(x, y-1);
                    System.out.println("You moved north.");
                }
                break;
            case "move south", "s":
                if (isWalkable(d, y + 1, x)) {
                    p.setPosition(x, y+1);
                    System.out.println("You moved south.");
                }
                break;
            case "move east", "e":
                if (isWalkable(d, y, x + 1)) {
                    p.setPosition(x+1, y);
                    System.out.println("You moved east.");
                }
                break;
            case "move west", "w":
                if (isWalkable(d, y, x - 1)) {
                    p.setPosition(x-1, y);
                    System.out.println("You moved west.");
                }
                break;
            default:
                System.out.println("Invalid input, only type what is displayed.");
                break;
        }
    }

    private boolean isWalkable(Dungeon d, int y, int x) {
        return d.getTile(y, x) == Tile.FLOOR.getTile();
    }

    public boolean wishToFightEnemy(Dungeon d, int y, int x, String userInput) {
        if (d.getTile(y, x) == Tile.ENEMY.getTile()) {
            System.out.println("There's an enemy here!");
            System.out.println("Do you wish to fight it, yes or no?");
            System.out.print("Y/N: ");
            return userInput.equalsIgnoreCase("y");
        }
        return false;
    }

    public boolean wishToPickUpItem(Dungeon d, int y, int x, String userInput) {
        if (d.getTile(y, x) == Tile.ITEM.getTile()) {
            System.out.println("There's an item here!");
            System.out.println("Do you wish to pick it up, yes or no?");
            System.out.print("Y/N: ");
            return userInput.equalsIgnoreCase("y");
        }
        return false;
    }

}
