package org.example.game;

import org.example.entities.Player;
import org.example.entities.Enemy;
import org.example.entities.Character;
import org.example.map.DungeonGrid;

import java.util.Scanner;

public class GameLoop {
    public void start() {
        System.out.println("Game starting!");

        Character player = new Player("Dragon Slayer", 100, 0, 0);
        Character dragon = new Enemy("Dragon", 100, 0, 0, 10);

        player.takeTurn();
        dragon.takeTurn();

        DungeonGrid grid = new DungeonGrid(5, 5);

        // Starting position
        int playerX = 2;
        int playerY = 2;

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            grid.printMap(playerX, playerY);
            System.out.print("Where are you headed?: (north/south/east/west/quit): ");
            String command = scanner.nextLine().toLowerCase().trim();

            switch (command) {
                case "north":
                    if (!grid.getTiles(playerX, playerY - 1).isWall()) {
                        playerY--;
                    } else {
                        System.out.println("You can't go that way!");
                    }
                    break;
                    case "south":
                    if (!grid.getTiles(playerX, playerY + 1).isWall()) {
                        playerY++;
                    } else {
                        System.out.println("You can't go that way!");
                    }
                    break;
                    case "east":
                    if (!grid.getTiles(playerX + 1, playerY).isWall()) {
                        playerX++;
                    } else {
                        System.out.println("You can't go that way!");
                    }
                    break;
                    case "west":
                        if (!grid.getTiles(playerX - 1, playerY).isWall()) {
                            playerX--;
                        } else {
                            System.out.println("You can't go that way!");
                        }
                        break;
                        case "quit":
                            running = false;
                            break;
                default:
                    System.out.println("Invalid command!");
            }
        }
    scanner.close();

    }
    }