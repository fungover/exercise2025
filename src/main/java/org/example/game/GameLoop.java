package org.example.game;

import org.example.entities.Player;
import org.example.entities.Enemy;
import org.example.entities.Character;
import org.example.map.DungeonGrid;
import org.example.service.GameLogic;

import java.util.Scanner;

public class GameLoop {
    public void start() {
        System.out.println("Game starting!");

        Character player = new Player("Dragon Slayer", 100, 2, 2);
        Character dragon = new Enemy("Dragon", 100, 0, 0, 10);

        player.takeTurn();
        dragon.takeTurn();

        DungeonGrid grid = new DungeonGrid(10, 5);

        // Starting position
        //From Character abstract class
        //int[] playerPos = {2, 2};

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            grid.printMap(player.getX(), player.getY());
            System.out.print("Where are you headed?: (north/south/east/west/quit): ");
            String command = scanner.nextLine().toLowerCase().trim();

            if (command.equals("quit")) {
                running = false;
            } else {
                GameLogic.handleCommand(command, grid, player);
            }
        }

    scanner.close();

        }
    }