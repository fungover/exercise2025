package org.example.game;

import org.example.entities.Enemy;
import org.example.entities.Item;
import org.example.entities.Player;
import org.example.map.Dungeon;
import org.example.map.Tile;
import org.example.map.TileType;
import org.example.service.CombatResult;
import org.example.service.MovementService;
import org.example.service.FloorService;
import org.example.service.CombatService;

import java.util.Locale;
import java.util.Scanner;

/*
    - Ansvarar för huvudloop och spellogik
    - Skapar Player, Dungeon, MovementService, CombatService
    - Läser in kommandon av användaren
    - Anropar services och skriver feedback
 */
public class Game {

    private final Scanner scanner = new Scanner(System.in);
    private Dungeon dungeon = new Dungeon(1);
    private final FloorService floors = new FloorService(2);
    private boolean gameOver = false;
    private final Player player;
    private final MovementService movement = new MovementService();
    private final CombatService combat = new CombatService(); // <-- Ny service för strider
    private int previousX;
    private int previousY;

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
            } else if (cmd.equals("inventory")) {
                player.printInventory();
            } else if (cmd.startsWith("use ")) {
                String itemName = cmd.substring("use ".length()).trim();
                handleUseItem(itemName);
            } else if (cmd.equals("look")) {
                look();
            } else {
                System.out.println("Unknown command. Write 'help' for a list of commands.");
            }
        }
    }

    // Försöker flytta spelaren i angiven riktning
    private void handleMove(String direction) {
        // Spara spelarens gamla position innan flytten
        previousX = player.getX();
        previousY = player.getY();

        // Försök flytta
        boolean moved = movement.movePlayer(player, dungeon, direction);

        if (!moved) {
            System.out.println("You can't move in that direction.");
            return;
        }

        Tile currentTile = dungeon.get(player.getX(), player.getY());

        // Fiende-möte → starta strid
        if (currentTile.hasEnemy()) {
            startCombat(currentTile.getEnemy());
            return;
        }

        // Item-ruta
        if (currentTile.hasItem()) {
            Item item = currentTile.getItem();
            player.addToInventory(item);
            System.out.println("You picked up: " + item.getName() + " (" + item.getType() + ")");

            currentTile.setItem(null);
        }

        // Exit-ruta
        if (currentTile.getType() == TileType.STAIRS) {
            System.out.println("You found the stairs! Descending to floor "
                    + (floors.getCurrentFloor() + 1) + "...");
            dungeon = floors.advance();
            player.moveTo(1, 1);
            map();
            return;
        }

        if (currentTile.getType() == TileType.EXIT) {
            System.out.println("🎉 You found the EXIT and escaped the dungeon!");
            gameOver = true;
            return;
        }

        // Vanlig flytt
        System.out.println("You moved " + direction + ".");
        map();
    }

    // === STRIDSSYSTEM ===
    private void startCombat(Enemy enemy) {
        System.out.println("\n=== ENEMY ENCOUNTER ===");
        System.out.println(enemy.getName());
        System.out.println("HP: " + enemy.getHp() + " | Damage: " + enemy.getDamage());
        System.out.println("=======================");
        System.out.println("Type 'attack', 'defend', or 'retreat'.");

        boolean inCombat = true;

        while (inCombat && !gameOver) {
            System.out.print("Combat > ");
            String cmd = scanner.nextLine().toLowerCase();

            CombatResult result;
            switch (cmd) {
                case "attack" -> result = combat.attack(player, enemy);
                case "defend" -> result = combat.defend(player, enemy);
                case "retreat" -> {
                    result = combat.retreat(player, previousX, previousY);
                    inCombat = false;
                    map();
                    System.out.println(result.getLog());
                    continue;
                }
                default -> {
                    System.out.println("Unknown combat command.");
                    continue;
                }
            }

            // Skriv alltid loggen från CombatService
            System.out.println(result.getLog());

            switch (result.getStatus()) {
                case "EnemyDefeated" -> {
                    dungeon.get(player.getX(), player.getY()).removeEnemy();
                    inCombat = false;
                }
                case "PlayerDefeated" -> {
                    gameOver = true;
                    return;
                }
            }
        }
    }


    // === Inventory-kommandon ===
    private void handleUseItem(String itemName) {
        Item found = null;
        for (Item item : player.getInventory()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                found = item;
                break;
            }
        }

        if (found == null) {
            System.out.println("No such item in your inventory: " + itemName);
            return;
        }

        player.useItem(found);
    }

    // === Kart och hjälpfunktioner ===

    private void map() {
        System.out.println("=== Floor " + floors.getCurrentFloor() + " ===");
        System.out.println("Your current location is at marker: p");
        printMapWithPlayer();
        printAvailableDirections();
    }
    private void look() {
        System.out.println("You look around...");

        checkTile("north", player.getX(), player.getY() - 1);
        checkTile("east",   player.getX() + 1, player.getY());
        checkTile("south", player.getX(), player.getY() + 1);
        checkTile("west",  player.getX() - 1, player.getY());
    }

    private void checkTile(String direction, int x, int y) {
        if (!dungeon.inBounds(x, y)) {
            System.out.println("To the " + direction + ": Out of bounds");
            return;
        }

        Tile tile = dungeon.get(x, y);
        switch (tile.getType()) {
            case WALL -> System.out.println("To the " + direction + ": Wall");
            case EMPTY -> System.out.println("To the " + direction + ": Empty");
            case ENEMY -> System.out.println("To the " + direction + ": Enemy (" + tile.getEnemy().getName() + ")");
            case ITEM -> System.out.println("To the " + direction + ": Item (" + tile.getItem().getName() + ")");
            case EXIT -> System.out.println("To the " + direction + ": Exit");
        }
    }

    private void printStats() {
        System.out.println("=== Player Stats ===");
        System.out.println("Name    : " + player.getName());
        System.out.println("Level   : " + player.getLevel());
        System.out.println("HP      : " + player.getHp());
        System.out.println("Attack  : " + player.getAttackDamage() + " (Weapon: " + player.getEquippedWeapon().getName() + ")");
        System.out.println("Defense : " + player.getDefense() + " (Armor: " + player.getEquippedArmor().getName() + ")");
        System.out.println("====================");
    }

    private void printHelp() {
        System.out.println("Commands:");
        System.out.println("  help - Show this helppanel");
        System.out.println("  map - Show the map");
        System.out.println("  move north|south|east|west - Try to move in direction");
        System.out.println("  n|s|e|w - Short version of 'move <direction>'");
        System.out.println("  stats - Show your current stats (name, HP, Attack, Defense)");
        System.out.println("  inventory - Show your items");
        System.out.println("  use <itemName> - Use or equip an item from your inventory");
        System.out.println("  look - Inspect nearby tiles");
        System.out.println("  quit|exit - Quit the game");
    }

    private void printMapWithPlayer() {
        for (int y = 0; y < dungeon.getHeight(); y++) {
            for (int x = 0; x < dungeon.getWidth(); x++) {
                if (x == player.getX() && y == player.getY()) {
                    System.out.print("P ");
                } else {
                    TileType type = dungeon.get(x, y).getType();
                    System.out.print(symbolFor(type) + " ");
                }
            }
            System.out.println();
        }
    }

    private String symbolFor(TileType type) {
        return switch (type) {
            case WALL -> "#";
            case EMPTY -> ".";
            case ENEMY -> "E";
            case ITEM -> "I";
            case STAIRS -> "S";
            case EXIT -> "X";
        };
    }

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

    private boolean isShortDirection(String cmd) {
        return cmd.equals("n") || cmd.equals("e") || cmd.equals("s") || cmd.equals("w");
    }

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