package org.example.game;

import org.example.entities.Enemy;
import org.example.entities.Item;
import org.example.entities.Player;
import org.example.entities.Tile;
import org.example.map.DungeonGenerator;
import org.example.service.CombatService;
import org.example.service.MovementService;

import java.util.Scanner;

public class GameLoop {
    private Player player;
    private DungeonGenerator dungeon;
    private Scanner scanner;
    private boolean gameRunning;

    public GameLoop() {
        scanner = new Scanner(System.in);
        gameRunning = true;
    }

    public void start() {
        System.out.println("=== Welcome to the Dungeon! ===");
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();

        //create our player with the input name
        player = new Player(playerName);
        dungeon = new DungeonGenerator(10, 8);//10x8 dungeon

        //set player starting pos
        player.setPosition(dungeon.getPlayerStartX(), dungeon.getPlayerStartY());

        System.out.println("Are you ready " + playerName + "? your adventure begins...");
        displayHelp();

        //Main game loop
        while (gameRunning && player.isAlive()) {
            dungeon.display(player);
            checkCurrentTile();

            if (!player.isAlive()) {break;}

            player.displayStats();
            System.out.print("\nWhat would you like to do? ");
            String input = scanner.nextLine().trim().toLowerCase();

            processCommand(input);
        }
        if (!player.isAlive()) {
            System.out.println("\n=== GAME OVER ===");
        } else {
            System.out.println("\nThanks for playing!");
        }

        scanner.close();

    }

    private void processCommand(String input) {
        String[] parts = input.split(" ");
        String command = parts[0];

        switch (command) {
            case "move":
            case "go":
                if (parts.length > 1) {
                    MovementService.movePlayer(player, parts[1], dungeon);
                } else {
                    System.out.println("Move where? (north, south, east, west)");
                }
                break;

            case "north":
            case "n":
            case "south":
            case "s":
            case "east":
            case "e":
            case "west":
            case "w":
                MovementService.movePlayer(player, command, dungeon);
                break;

            case "inventory":
            case "inv":
                player.displayInventory();
                break;

            case "use":
                if (parts.length > 1) {
                    useItem(parts[1]);
                } else {
                    System.out.println("Use what? type 'use [item number]' (check inventory first)");
                }
                break;

            case "look":
                lookAround();
                break;

            case "help":
                displayHelp();
                break;

            case "quit":
            case "exit":
                gameRunning = false;
                break;
            default:
                System.out.println("Unknown command. Type 'help' for a list of commands.");
        }
    }


    private void checkCurrentTile() {
        Tile currentTile = dungeon.getTile(player.getX(), player.getY());

        if (currentTile.hasEnemy()) {
            Enemy enemy = currentTile.getEnemy();
            boolean enemyDefeated = CombatService.combat(player, enemy, scanner);

            if (enemyDefeated) {
                currentTile.setEnemy(null);//this removes the defeated enemey
            }//if player flees the enemy stays on the tile


        } else if (currentTile.hasItem()) {
            Item item = currentTile.getItem();
            System.out.println("You found a " + item.getName() + ".");
            System.out.println("Description: " + item.getDescription());
            player.addItem(item); //adds item to player inventory
            currentTile.setItem(null);//remove picked up item from tile
        }
    }

    private void useItem(String itemNumber) {
        try {
            int index = Integer.parseInt(itemNumber) - 1;
            if (index >= 0 && index < player.getInventory().size()) {
                Item item = player.getInventory().get(index);
                item.use(player);

                /*remove potions after use (but not weapon,
                maybe we add some weapons that are better against some enemy's
                 */
                if (item.getType() == Item.ItemType.Potion) {
                    player.removeItem(item);
                }
            } else {
                System.out.println("Invalid item number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a vlid item number.");
        }
    }

    private void lookAround() {
        Tile currentTile = dungeon.getTile(player.getX(), player.getY());
        System.out.println("You look around...");

        if (currentTile.hasEnemy()) {
            Enemy enemy = currentTile.getEnemy();
            System.out.println("There's a " + enemy.getName() + " here! (HP: " + enemy.getHealth() + ")");
        } else if (currentTile.hasItem()) {
            Item item = currentTile.getItem();
            System.out.println("There's a " + item.getName() + " here: " + item.getDescription());

        } else {
            System.out.println("This area is empty.");
        }

        //looks at all tiles around player
        checkAdjacentTile(player.getX(), player.getY() - 1, "North");
        checkAdjacentTile(player.getX(), player.getY() + 1, "South");
        checkAdjacentTile(player.getX() + 1, player.getY(), "East");
        checkAdjacentTile(player.getX() - 1, player.getY(), "West");

    }

    private void checkAdjacentTile(int x, int y, String direction) {
        Tile tile = dungeon.getTile(x, y);
        if (tile != null) {
            if (tile.isWall()) {
                System.out.println(direction + ": wall");
            } else if (tile.hasEnemy()) {
                System.out.println(direction + ": Enemy (" + tile.getEnemy().getName() + ")");
            } else if (tile.hasItem()) {
                System.out.println(direction + ": Item (" + tile.getItem().getName() + ")");
            } else {
                System.out.println(direction + ": Empty path");
            }
        }
    }

    //shows commands
    private void displayHelp() {
        System.out.println("\n=== COMMANDS ===");
        System.out.println("Movement: north/n, south/s, east/e, west/w");
        System.out.println("  or: move north, move south, etc.");
        System.out.println("inventory/inv - View your items");
        System.out.println("use [number] - Use an item from inventory");
        System.out.println("look - Examine your surroundings");
        System.out.println("help - Show this help");
        System.out.println("quit/exit - End the game");
        System.out.println("\nMap symbols: @ = You, E = Enemy, I = Item, █ = Wall, . = Empty");
        System.out.println("\nCOMBAT: when you encounter an enemy:");
        System.out.println("- Choose to Attack, Use item, or Flee");
        System.out.println("- Weapons automatically increase your damage when picked up");
        System.out.println("- Health potions can be used in/out of combat to restore HP");
    }
}
