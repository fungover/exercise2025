package org.example.game;

import org.example.entities.Item;
import org.example.entities.Player;
import org.example.entities.Position;
import org.example.service.Direction;
import org.example.service.GameService;

import java.util.List;
import java.util.Scanner;

public class GameController {
    private final Player player;
    private final GameService gameService;
    private final Scanner scanner;
    private boolean gameRunning;

    public GameController() {
        this.player = new Player("Rikardo", 100, new Position(1, 1));
        this.gameService = new GameService();
        this.scanner = new Scanner(System.in);
        this.gameRunning = true;
    }

    public void startGame() {
        System.out.println("==================================");
        System.out.println("=== WELCOME TO DUNGEON CRAWLER ===");
        System.out.println("==================================");

        gameService.displayGameState(player);

        while (gameRunning) {
            System.out.print("Enter command: ");
            System.out.println();
            String input = scanner.nextLine().trim().toLowerCase();
            handleInput(input);
        }
        System.out.println("Thanks for playing!");
        scanner.close();
    }

    public void handleInput(String input) {
        switch (input) {
            case "north", "n" -> handleMovement(Direction.NORTH);
            case "south", "s" -> handleMovement(Direction.SOUTH);
            case "east", "e" -> handleMovement(Direction.EAST);
            case "west", "w" -> handleMovement(Direction.WEST);
            case "pickup", "p" -> handlePickup();
            case "inventory", "i" -> handleInventory();
            case "use", "u" -> handleUseItem();
            case "fight", "f" -> handleFight();
            case "quit", "q" -> gameRunning = false;
            default -> {
                System.out.println("Invalid command.");
                return;
            }
        }

        if (gameRunning) {
            gameService.displayGameState(player);
        }
    }

    private void handleMovement(Direction direction) {
        gameService.handleMovement(player, direction);
    }

    private void handlePickup() {
        gameService.handlePickup(player);
    }

    private void handleInventory() {
        gameService.displayInventory(player);
    }

    private void handleFight() {
        gameService.handleFight(player, scanner);
    }

    private void handleUseItem() {
        List<Item> playerInventory = player.getInventory();
        if (playerInventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }

        gameService.displayInventory(player);
        System.out.print("Use item (enter number): ");

        try {
            int itemIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (itemIndex >= 0 && itemIndex < playerInventory.size()) {
                Item item = playerInventory.get(itemIndex);
                player.useItem(item.getName());
                System.out.println("Used: " + item.getName());
            } else {
                System.out.println("Invalid number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
}
