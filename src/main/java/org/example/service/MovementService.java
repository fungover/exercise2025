package org.example.service;

import org.example.entities.Player;
import org.example.map.DungeonGenerator;

public class MovementService {

    public static boolean movePlayer(Player player, String direction,
                                     DungeonGenerator dungeon) {
        int newX = player.getX();
        int newY = player.getY();

        switch (direction.toLowerCase()) {
            case "north":
            case "n":
                newY--;
                break;
            case "south":
            case "s":
                newY++;
                break;
            case "east":
            case "e":
                newX++;
                break;
            case "west":
            case "w":
                newX--;
                break;
            default:
                System.out.println(
                  "Invalid direction! Use: north, south, east, west (or n, s, e, w");
                return false;
        }
        if (dungeon.isValidPosition(newX, newY)) {
            player.setPosition(newX, newY);
            System.out.println("You moved " + direction + ".");
            return true;
        } else {
            System.out.println("You can't move there! There's a wall in the way.");
            return false;
        }
    }
}
