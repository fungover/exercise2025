package org.example.service;

import org.example.entities.Player;
import org.example.map.DungeonGenerator;

import java.util.Locale;
import java.util.Objects;

public class MovementService {

    public static boolean movePlayer(Player player, String direction,
                                     DungeonGenerator dungeon) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(dungeon, "dungeon");
        if (direction == null || direction.isBlank()) {
            System.out.println(
              "Invalid direction! Use: north, south, east, west (or n, s, e, w).");
            return false;
        }
        int newX = player.getX();
        int newY = player.getY();
        String fullDirection;

        final String dir = direction.toLowerCase(Locale.ROOT);
        switch (dir) {
            case "north":
            case "n":
                newY--;
                fullDirection = "North";
                break;
            case "south":
            case "s":
                newY++;
                fullDirection = "South";
                break;
            case "east":
            case "e":
                newX++;
                fullDirection = "East";
                break;
            case "west":
            case "w":
                newX--;
                fullDirection = "West";
                break;
            default:
                System.out.println(
                  "Invalid direction! Use: north, south, east, west (or n, s, e, " +
                    "w).");
                return false;
        }
        if (dungeon.isValidPosition(newX, newY)) {
            player.setPosition(newX, newY);
            System.out.println("You moved " + fullDirection + ".");
            return true;
        } else {
            System.out.println("You can't move there! There's a wall in the way.");
            return false;
        }
    }
}
