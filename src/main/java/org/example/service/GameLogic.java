package org.example.service;

import org.example.map.DungeonGrid;
import org.example.entities.Character;

public class GameLogic {
    public static void handleCommand(String command, DungeonGrid grid, Character player) {
        switch (command) {
            case "north":
                Movement.move(grid, player, 0, -1); {
                }
                break;
                case "south":
                 Movement.move(grid, player, 0, 1); {
                }
                break;
                case "east":
                 Movement.move(grid, player, 1,0); {
                }
                break;
                case "west":
                 Movement.move(grid, player, -1,0); {
                }
                break;
            default:
                System.out.println("Invalid command!");
        }
    }
}
