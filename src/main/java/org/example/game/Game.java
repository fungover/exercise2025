package org.example.game;

import org.example.map.Dungeon;
import org.example.entities.Player;

import java.util.Scanner;

public class Game {
    private final Dungeon dungeon;
    private final CommandParser parser;
    private final Scanner scanner;
    private boolean isRunning;

    public Game(Dungeon dungeon, CommandParser parser) {
        if (dungeon == null || parser == null) {
            throw new IllegalArgumentException("Dependencies cannot be null");
        }
        this.dungeon = dungeon;
        this.parser = parser;
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
        System.out.println("Game initialized for dungeon with dimensions " + dungeon.getWidth() + "x" + dungeon.getHeight());
    }

    public void run() {
        System.out.println("Welcome to The Dungeon! Commands: move <direction>, attack <x> <y>, pickup <x> <y>, quit or exit");
        displayState();
        while (isRunning && !isGameOver()) {
            System.out.print("Enter command: ");
            if (!scanner.hasNextLine()) {
                System.out.println("\nNo input. Exiting...");
                break;
            }
            String command = scanner.nextLine();
            String trimmed = command.trim();
            if (trimmed.equalsIgnoreCase("quit") || trimmed.equalsIgnoreCase("exit")) {
                isRunning = false;
                System.out.println("Quitting game...");
                break;
            }
            String result = parser.parseCommand(command);
            System.out.println(result);
            displayState();
        }
        if (isGameOver()) {
            System.out.println("Game over: player died");
        } else {
            System.out.println("Game ended, thanks for playing!");
        }
        // Intentionally not closing System.in-backed scanner to avoid impacting host process.
    }

    private void displayState() {
        System.out.println("Current map:\n" + dungeon);
        System.out.println(getStatus());
    }

    private String getStatus() {
        Player player = dungeon.getPlayer();
        return "Player: " + player.getName() + " at (" + player.getX() + ", " +
                player.getY() + "), Health: " + player.getHealth() + ", Inventory: " + player.getInventory();
    }

    private boolean isGameOver() {
        return dungeon.getPlayer().getHealth() <= 0;
    }
}
