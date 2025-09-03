package org.example.game;


import org.example.entities.Enemy;
import org.example.entities.Player;
import org.example.map.Dungeon;
import org.example.map.Tile;
import org.example.map.TileType;
import org.example.service.MovementService;
import org.example.service.FloorService;

import java.util.Locale;
import java.util.Scanner;

/*
    - Ansvarar för huvudloop och spellogik
    - Skapar Player, Dungeon, MovementService
    - Läser in kommandon av användaren
    - Anropar services och skriver feedback
 */
public class Game {

    private final Scanner scanner = new Scanner(System.in);
    private Dungeon dungeon = new Dungeon();
    private final FloorService floors = new FloorService(2);
    private boolean gameOver = false;
    private final Player player;
    private final MovementService movement = new MovementService();

    public Game() {
        System.out.println("Welcome to Dungeon Crawler!");
        System.out.println("What is your name, adventurer? ");
        String name = scanner.nextLine();
        this.player = new Player(name);
        System.out.println("Your adventure begins now, " + player.getName() + "!");
    }

    public void run() {
        System.out.println();
        map();

        while (!gameOver) {
            System.out.print("\nCommand > ");
            String line = scanner.nextLine();

            if (line.isEmpty()) {
                continue;
            }

            // Gör att kommandon som "MOVE EAST" gör samma sak som "move east"
            String cmd = line.toLowerCase(Locale.ROOT);

            // Spelkommandon
            if (cmd.equals("quit") || cmd.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            } else if (cmd.equals("help")) {
                printHelp();
            } else if (cmd.equals("map")) {
                map();
            } else if (cmd.startsWith("move ")) {
                String arg = cmd.substring("move ".length()).trim();

                if (arg.equals("north") || arg.equals("south") || arg.equals("east") || arg.equals("west")) {
                    handleMove(arg);
                } else {
                    System.out.println("Invalid 'move' usage. Use: move north|south|east|west");
                    System.out.println("Tip: use short commands without 'move' → n | e | s | w");
                }
            } else if (isShortDirection(cmd)) {
                handleMove(shortToLong(cmd));
            } else if (cmd.equals("stats")) {
                printStats();
            } else {
                System.out.println("Unknown command. Write 'help' for a list of commands.");
            }
        }
    }

    private void printStats() {
        System.out.println("=== Player Stats ===");
        System.out.println("Name : " + player.getName());
        System.out.println("Level : " + player.getLevel());
        System.out.println("HP : " + player.getHp());
        System.out.println("====================");
    }

    // Försöker flytta spelaren i angiven riktning
    private void handleMove(String direction) {
        boolean moved = movement.movePlayer(player, dungeon, direction);

        if (!moved) {
            System.out.println("You can't move in that direction.");
            return;
        }

        Tile currentTile = dungeon.get(player.getX(), player.getY());

        // Fiende-möte
        if (currentTile.hasEnemy()) {
            Enemy enemy = currentTile.getEnemy();
            System.out.println("\n=== ENEMY ENCOUNTER ===");
            System.out.println(enemy.getName());
            System.out.println("HP: " + enemy.getHp() + " | Damage: " + enemy.getDamage());
            System.out.println("=======================\n");
            return;
        }

        // Exit-ruta
        if (currentTile.getType() == TileType.EXIT) {
            if (!floors.isFinalFloor()) {
                System.out.println("You found the stairs! Descending to floor "
                        + (floors.getCurrentFloor() + 1) + "...");
                dungeon = floors.advance();
                player.moveTo(1, 1);
                map();
            } else {
                System.out.println("🎉 You found the EXIT on floor 2 and escaped the dungeon!");
                gameOver = true;
            }
            return;
        }

        // Vanlig flytt
        System.out.println("You moved " + direction + ".");
        map();
    }

    // Beskriver var spelaren står och visar en ASCII karta:
    // 'p' markerar spelaren. '#' är vägg. '.' är en tom ruta.
    private void map() {
        System.out.println("=== Floor " + floors.getCurrentFloor() + " ===");
        System.out.println("Your current location is at marker: p");
        printMapWithPlayer();
        printAvailableDirections();
    }

    // Skriver ut kort manual för kommandon
    private void printHelp() {
        System.out.println("Commands:");
        System.out.println("  help - Show this helppanel");
        System.out.println("  map - Show the map");
        System.out.println("  move north|south|east|west - Try to move in direction");
        System.out.println("  n|s|e|w - Short version of 'move <direction>'");
        System.out.println("  stats - Show your current stats (name, HP)");
        System.out.println("  quit|exit - Quit the game");
    }

    // Ritar en ASCII karta med spelaren inritad som 'p'.
    private void printMapWithPlayer() {
        for (int y = 0; y < dungeon.getHeight(); y++) {
            for (int x = 0; x < dungeon.getWidth(); x++) {
                if (x == player.getX() && y == player.getY()) {
                    System.out.print("p ");
                } else {
                    TileType type = dungeon.get(x, y).getType();
                    System.out.print(symbolFor(type) + " ");
                }
            }
            System.out.println();
        }
    }

    // Översätter en TileType till en enkel symbol
    private String symbolFor(TileType type) {
        return switch (type) {
            case WALL -> "#";
            case EMPTY -> ".";
            case ENEMY -> "E";
            case ITEM -> "I";
            case EXIT -> "X";
        };
    }

    // Skriver ut vilka riktningar som är tillgängliga från spelarens position
    private void printAvailableDirections () {
        java.util.List<String> dirs = new java.util.ArrayList<>();

        if (dungeon.isWalkable(player.getX(), player.getY() - 1)) {
            dirs.add("north");
        }

        if (dungeon.isWalkable(player.getX(), player.getY() + 1)) {
            dirs.add("south");
        }

        if (dungeon.isWalkable(player.getX() - 1, player.getY())) {
            dirs.add("west");
        }

        if (dungeon.isWalkable(player.getX() + 1, player.getY())) {
            dirs.add("east");
        }

        if (dirs.isEmpty()) {
            System.out.println("You can't move in any direction.");
        } else {
            System.out.println("You can move in these directions: " + String.join(", ", dirs));
        }
    }

    // Tillåt kortkommandon
    private boolean isShortDirection(String cmd) {
        return cmd.equals("n") || cmd.equals("e") || cmd.equals("s") || cmd.equals("w");
    }


    // Översätter kortkommandon till fulla ord
    private String shortToLong(String cmd) {
        return switch (cmd) {
            case "n" -> "north";
            case "e" -> "east";
            case "s" -> "south";
            case "w" -> "west";
            default -> cmd;
        };
    }
}
