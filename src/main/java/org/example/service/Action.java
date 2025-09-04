package org.example.service;

import org.example.map.Room;
import org.example.map.Tile;

import java.util.Scanner;

public class Action {
    public void fightEnemy(Room room, int y, int x) {
        Scanner input = new Scanner(System.in);
        if (room.getGrid(y, x) == Tile.ENEMY.getTile()) {
            System.out.println("There's an enemy here!");
            System.out.println("Do you wish to fight it, yes or no?");
            System.out.print("Y/N: ");
            String answer = input.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                //startFight()
            }
        }
    }

    public boolean walkable(Room room, int y, int x) {
        return room.getGrid(y, x) == Tile.FLOOR.getTile();
    }

    public void pickItem(Room room, int y, int x) {
        Scanner input = new Scanner(System.in);
        if (room.getGrid(y, x) == Tile.ITEM.getTile()) {
            System.out.println("There's an item here!");
            System.out.println("Do you wish to pick it up, yes or no?");
            System.out.print("Y/N: ");
            String answer = input.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                //pickUpItem()
            }
        }
    }

    public void useItem() {}
}
