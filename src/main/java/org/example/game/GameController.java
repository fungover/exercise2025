package org.example.game;

import org.example.entities.Player;
import org.example.entities.Position;
import org.example.map.Dungeon;
import org.example.service.Direction;
import org.example.service.MovementService;

import java.util.Scanner;

public class GameController {
    private Player player;
    private Dungeon dungeon;
    private MovementService movementService;
    private Scanner scanner;
    private boolean gameRunning;

    public GameController() {
        this.player = new Player("Rikardo", 100, new Position(1,1));
        this.dungeon = new Dungeon(8,10);
        this.movementService = new MovementService();
        this.scanner = new Scanner(System.in);
        this.gameRunning = true;
    }

    public void startGame() { // Main game loop
        System.out.println("=======================================");
        System.out.println("=== WELCOME TO THE DUNGEON CRAWLER ===");
        System.out.println("=======================================");

        showGameState(); // Show initial game state

        while (gameRunning) { // Game loop
            System.out.print("\nEnter command: "); // Prompt for user input
            String input = scanner.nextLine().trim().toLowerCase(); // Read and normalize input

            handleInput(input); // Process the input
        }

        System.out.println("Thanks for playing!"); // if the game loop ends, we print a goodbye message
        scanner.close(); // Close the scanner to avoid resource leaks
    }

    public void handleInput(String input) {
        switch (input) {
            case "north", "n" -> movePlayer(Direction.NORTH);
            case "south", "s" -> movePlayer(Direction.SOUTH);
            case "east", "e" -> movePlayer(Direction.EAST);
            case "west", "w" -> movePlayer(Direction.WEST);
            case "quit", "q" -> {
                gameRunning = false;
                return;
            }
            default -> {
                System.out.println("Invalid command. Use north, south, east, west, or quit.");
                return;
            }
        }
        showGameState();
    }

    private void movePlayer(Direction direction) {
        boolean moved = movementService.movePlayer(player, direction, dungeon);
        if (moved) {
            System.out.println("Moving " + direction.toString().toLowerCase() + "...");
        } else {
            System.out.println("Can't move " + direction.toString().toLowerCase() + " - obstacle in the way!");
        }
    }

    private void showGameState() {
        System.out.println();
        dungeon.printMapWithPlayer(player);
        System.out.println(player);
        System.out.println("Commands: North (N), South (S), East (E), West (W), Quit (Q)");
    }
}
