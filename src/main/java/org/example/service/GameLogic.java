package org.example.service;

import org.example.entities.Player;
import org.example.map.Dungeon;
import org.example.map.Tile;

import java.util.Scanner;

public class GameLogic {
    // All tiles in the game
    private final char player = Tile.PLAYER.getTile();
    private final char floor = Tile.FLOOR.getTile();
    private final char wall = Tile.WALL.getTile();
    private final char item = Tile.ITEM.getTile();
    private final char enemy = Tile.ENEMY.getTile();
    private final char door = Tile.DOOR.getTile();

    public void renderPlayerPosition(Dungeon d, Player p) {
        // Here grid[i][j] == grid[y][x] from Room class
        for (int i = 0; i < d.getRows(); i++) {
            for (int j = 0; j < d.getColumns(); j++) {
                if (d.getTile(i, j) == player) {
                    d.setTile(i, j, floor);
                }
            }
        }
        d.setTile(p.getY(), p.getX(), player);
    }

    public void moveInput(Dungeon d, Player p, String userInput) {
        userInput = userInput.toLowerCase();

        int x = p.getX();
        int y = p.getY();

        switch (userInput) {
            case "u":
                if (isWalkable(d, y - 1, x)) {
                    p.setPosition(x, y - 1);
                    System.out.println(p.getName() + " moved up.");
                }
                break;
            case "d":
                if (isWalkable(d, y + 1, x)) {
                    p.setPosition(x, y + 1);
                    System.out.println(p.getName() + " moved down.");
                }
                break;
            case "r":
                if (isWalkable(d, y, x + 1)) {
                    p.setPosition(x + 1, y);
                    System.out.println(p.getName() + " moved to the right.");
                }
                break;
            case "l":
                if (isWalkable(d, y, x - 1)) {
                    p.setPosition(x - 1, y);
                    System.out.println(p.getName() + " moved to the left.");
                }
                break;
            default:
                System.out.println("Invalid input, only type what is displayed.");
                break;
        }
    }

    private boolean isWalkable(Dungeon d, int y, int x) {
        return d.getTile(y, x) == floor || d.getTile(y, x) == item;
    }

    public boolean wishToFightEnemy(Dungeon d, Player p, Scanner scan) {
        if (d.getTile(p.getY(), p.getX() + 1) == enemy ||
                d.getTile(p.getY(), p.getX() - 1) == enemy ||
                d.getTile(p.getY() + 1, p.getX()) == enemy ||
                d.getTile(p.getY() - 1, p.getX()) == enemy) {
            System.out.println("There's an enemy here!");
            System.out.println("Do you wish to fight it, yes or no?");
            return validAnswer(scan);
        }
        return false;
    }

    public boolean wishToPickUpItem(Dungeon d, Player p, Scanner scan) {
        if (d.getTile(p.getY(), p.getX() + 1) == item ||
                d.getTile(p.getY(), p.getX() - 1) == item ||
                d.getTile(p.getY() + 1, p.getX()) == item ||
                d.getTile(p.getY() - 1, p.getX()) == item) {
            System.out.println("There's an item here!");
            System.out.println("Do you wish to pick it up, yes or no?");
            return validAnswer(scan);
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

}
