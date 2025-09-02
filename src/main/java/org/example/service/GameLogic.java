package org.example.service;

import org.example.map.DungeonGrid;
import org.example.entities.Character;

public class GameLogic {
    public static void handleCommand(String command, DungeonGrid grid, Character player) {
        switch (command) {
            case "north":
                Movement.moveNorth(grid, player); {
                }
                break;
                case "south":
                 Movement.moveSouth(grid, player); {
                }
                break;
                case "east":
                 Movement.moveEast(grid, player); {
                }
                break;
                case "west":
                 Movement.moveWest(grid, player); {
                }
                break;
            default:
                System.out.println("Invalid command!");
        }
    }
}
