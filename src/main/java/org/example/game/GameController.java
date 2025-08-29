package org.example.game;

import org.example.entities.Item;
import org.example.entities.Player;
import org.example.entities.Position;
import org.example.map.Room;
import org.example.map.TileType;
import org.example.service.Direction;
import org.example.service.ItemService;
import org.example.service.MovementService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameController {
    private Player player;
    private List<Room> rooms;
    private int currentRoomIndex;
    private MovementService movementService;
    private ItemService itemService;
    private Scanner scanner;
    private boolean gameRunning;

    public GameController() {
        this.player = new Player("Rikardo", 100, new Position(1, 1));

        this.rooms = new ArrayList<>();
        rooms.add(new Room("Dungeon Entrance", 8, 10));
        rooms.add(new Room("Treasure Room", 6, 8));
        this.currentRoomIndex = 0;

        this.movementService = new MovementService();
        this.itemService = new ItemService();
        this.scanner = new Scanner(System.in);
        this.gameRunning = true;

        itemService.placeRandomItems(getCurrentRoom().getDungeon()); // Placing items in the dungeon randomly
    }

    public void startGame() { // Main game loop
        System.out.println("=======================================");
        System.out.println("=== WELCOME TO THE DUNGEON CRAWLER ===");
        System.out.println("=======================================");

        showGameState(); // Show initial game state

        while (gameRunning) { // Game loop
            System.out.print("Enter command: "); // Prompt for user input
            System.out.println();
            String input = scanner.nextLine().trim().toLowerCase(); // Read and normalize input

            handleInput(input); // Process the input
        }

        System.out.println("Thanks for playing!"); // if the game loop ends, we print a goodbye message
        scanner.close(); // Close the scanner to avoid resource leaks
    }

    private Room getCurrentRoom() {
        return rooms.get(currentRoomIndex);
    }

    public void handleInput(String input) {
        switch (input) {
            case "north", "n" -> movePlayer(Direction.NORTH);
            case "south", "s" -> movePlayer(Direction.SOUTH);
            case "east", "e" -> movePlayer(Direction.EAST);
            case "west", "w" -> movePlayer(Direction.WEST);
            case "pickup", "p" -> pickupItem();
            case "inventory", "i" -> showInventory();
            case "use", "u" -> useItem();
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

    private void printMapWithPlayer() {
        Position playerPos = player.getPosition();

        for (int y = 0; y < getCurrentRoom().getDungeon().getRows(); y++) {
            for (int x = 0; x < getCurrentRoom().getDungeon().getColumns(); x++) {
                Position currentPos = new Position(x, y);

                if (x == playerPos.getX() && y == playerPos.getY()) {
                    System.out.print(TileType.PLAYER.getSymbol());
                } else if (itemService.getItemAt(currentPos) != null) {
                    System.out.print(TileType.ITEM.getSymbol());
                } else {
                    System.out.print(getCurrentRoom().getDungeon().getTile(x, y).getSymbol());
                }
            }
            System.out.println();
        }
    }

    private void movePlayer(Direction direction) {
        boolean moved = movementService.movePlayer(player, direction, getCurrentRoom().getDungeon());
        if (moved) {
            System.out.println("Moving " + direction.toString().toLowerCase() + "...");
            checkForDoor();
        } else {
            System.out.println("Can't move " + direction.toString().toLowerCase() + " - obstacle in the way!");
        }
    }

    private void checkForDoor() {
        Position playerPos = player.getPosition();
        TileType currentTile = getCurrentRoom().getDungeon().getTile(playerPos.getX(), playerPos.getY()).getType();

        if (currentTile == TileType.DOOR) {

            currentRoomIndex = (currentRoomIndex + 1) % rooms.size();
            player.setPosition(new Position(1, 1));
            itemService = new ItemService();
            itemService.placeRandomItems(getCurrentRoom().getDungeon());
            System.out.println("You entered: " + getCurrentRoom().getName());
        }
    }

    private void showGameState() {
        System.out.println();
        System.out.println("Current Room: " + getCurrentRoom().getName());
        printMapWithPlayer();
        System.out.println(player);
        System.out.println("Movement: North (N), South (S), East (E), West (W), Quit (Q)");
        System.out.println("Actions: Pickup (P), Inventory (I), Use (U)");
    }

    private void pickupItem() {
        Position playerPos = player.getPosition();
        Item item = itemService.getItemAt(playerPos);

        if (item != null) {
            player.addToInventory(item);
            itemService.removeItem(item);
            System.out.println("Picked up: " + item.getName());
        } else {
            System.out.println("No item to pick up here.");
        }
    }

    private void showInventory() {
        List<Item> playerInventory = player.getInventory();
        if (playerInventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("Inventory:");
            for (int i = 0; i < playerInventory.size(); i++) {
                System.out.println((i + 1) + ". " + playerInventory.get(i).getName());
            }
        }
    }

    private void useItem() {
        List<Item> playerInventory = player.getInventory();
        if (playerInventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }

        showInventory();
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
