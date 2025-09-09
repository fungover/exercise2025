package org.example.game;

import org.example.entities.*;
import org.example.utils.GameText;

import java.util.Scanner;

import static org.example.utils.Colors.*;

public class Game {
    private final Scanner scanner;
    private Player player;
    private Room currentRoom;
    private boolean gameRunning;
    private long startTime;

    public Game() {
        scanner = new Scanner(System.in);
        gameRunning = true;
    }

    public void start() {
        startTime = System.currentTimeMillis();
        showIntroduction();
        String playerName = getPlayerName();
        player = new Player(playerName);

        continueStory();
        getPlayerAttention();
        GameInstructions();
        currentRoom = WorldBuilder.createWorld();
        gameLoop();
    }

    private void showIntroduction() {
        System.out.println(GameText.GAME_TITLE);
        System.out.println(GameText.CAVE_INTRO);
        System.out.println(GameText.THADE_GREETING);
        System.out.println(GameText.SPINE_SHIVER);
        System.out.println(GameText.THADE_REASSURANCE);
        System.out.println(GameText.THADE_INTRODUCTION);
        System.out.println(GameText.NAME_PROMPT);
    }

    private void continueStory() {
        System.out.println(GameText.LISTEN_CAREFULLY);
        System.out.print(purple("Hey, "));
        System.out.print(cyan(player.getName()));
        System.out.print(purple("! "));
        System.out.println(GameText.ATTENTION_CHECK);
    }

    private void GameInstructions() {
        System.out.println(GameText.FIND_KEY_INSTRUCTION);
        System.out.println(GameText.DANGER_WARNING);
        System.out.println(GameText.ITEMS_ADVICE);
        System.out.print(purple("And "));
        System.out.print(cyan(player.getName()));
        System.out.println(GameText.FIND_ITEMS_FIRST);
        System.out.println(GameText.TROLL_WARNING);
        System.out.print(purple("\nGood luck, hehe.. "));
        System.out.print(cyan(player.getName()));
        System.out.print(GameText.GOOD_LUCK);
        System.out.println(GameText.HELP_PROMPT);
    }

    private String getPlayerName() {
        String name = scanner.nextLine();
        System.out.print(purple("\n-"));
        System.out.print(cyan(name));
        System.out.print(GameText.NAME_INTERESTING);
        System.out.print(GameText.NAME_OKAY);
        System.out.print(cyan(name));
        System.out.println(GameText.NAME_DOTS);
        return name;
    }

    private void getPlayerAttention() {
        boolean answerYes = false;
        while (!answerYes) {
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                answerYes = true;
                System.out.println(GameText.ATTENTION_RESPONSE_GOOD);
            } else {
                System.out.println(GameText.ATTENTION_RESPONSE_WAIT);
            }
        }
    }

    private void gameLoop() {
        currentRoom.look();
        while (gameRunning && player.isAlive()) {
            System.out.println(GameText.WHAT_TO_DO);
            String command = scanner.nextLine().toLowerCase();
            handleCommand(command);
        }
    }

    private void handleCommand(String command) {
        String normalized = command == null ? "" : command.trim();
        if (normalized.isEmpty()) {
            System.out.println(GameText.TYPE_COMMAND);
            return;
        }
        String[] words = normalized.split("\\s+");
        String action = words[0];

        switch (action) {
            case "go":
            case "move":
                if (words.length > 1) {
                    movePlayer(words[1]);
                } else {
                    System.out.println(GameText.GO_WHERE);
                }
                break;

            case "look":
            case "l":
                currentRoom.look();
                break;

            case "take":
                if (words.length > 1) {
                    int idx = command.indexOf(' ');
                    String itemName = idx >= 0 ? command.substring(idx + 1).trim() : "";
                    takeItem(itemName);
                } else {
                    System.out.println(GameText.TAKE_WHAT);
                }
                break;

            case "inventory":
            case "i":
                player.showInventory();
                break;

            case "stats":
            case "status":
                player.showStats();
                break;

            case "use":
                if (words.length > 1) {
                    int idx = command.indexOf(' ');
                    String itemName = idx >= 0 ? command.substring(idx + 1).trim() : "";
                    useItem(itemName);
                } else {
                    System.out.println(GameText.USE_WHAT);
                }
                break;

            case "equip":
                if (words.length > 1) {
                    int idx = command.indexOf(' ');
                    String itemName = idx >= 0 ? command.substring(idx + 1).trim() : "";
                    equipItem(itemName);
                } else {
                    System.out.println(GameText.EQUIP_WHAT);
                }
                break;

            case "unlock":
            case "open":
                if (words.length > 1 && words[1].equalsIgnoreCase("door")) {
                    unlockDoor();
                } else {
                    System.out.println(GameText.UNLOCK_WHAT);
                }
                break;

            case "attack":
            case "fight":
                attackEnemy();
                break;

            case "help":
            case "h":
                showHelp();
                break;

            case "stuck":
            case "hint":
                System.out.println(GameText.TIPS_HEADER);
                System.out.println(GameText.TIP_PICKUP);
                System.out.println(GameText.TIP_TAKE);
                System.out.println(GameText.TIP_USE);
                System.out.println(GameText.TIP_EQUIP);
                System.out.println(GameText.TIP_COMBAT);
                System.out.println(GameText.TIP_PREPARE);
                System.out.println(GameText.TIP_NAVIGATION);
                System.out.println(GameText.TIP_HELP);
                break;


            case "quit":
                gameRunning = false;
                System.out.println(GameText.THANKS_FOR_PLAYING);
                break;

            case "exit":
                System.out.println(GameText.QUIT_INSTRUCTION);
                break;

            default:
                System.out.println(GameText.UNKNOWN_COMMAND);
        }
    }

    private void movePlayer(String direction) {
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom != null) {
            if (currentRoom.hasAliveEnemies()) {
                System.out.println(GameText.ENEMIES_BLOCKING);
                return;
            }
            currentRoom = nextRoom;
            currentRoom.look();
        } else {
            System.out.println(GameText.NO_EXIT);
        }
    }

    private void takeItem(String itemName) {
        Item item = currentRoom.findItem(itemName);
        if (item != null) {
            if (player.addItem(item)) {
                currentRoom.removeItem(item);
                System.out.printf(GameText.ITEM_PICKED_UP + "%n", item.getName());
            } else {
                System.out.println(GameText.INVENTORY_FULL);
            }
        } else {
            System.out.printf(GameText.NO_ITEM_HERE + "%n", itemName);
            System.out.println(GameText.ITEMS_IN_ROOM);
            for (Item roomItem : currentRoom.getItems()) {
                System.out.println("- " + roomItem.getName());
            }
        }
    }

    private void useItem(String itemName) {
        Item item = player.findItem(itemName);
        if (item instanceof Usable) {
            Usable usableItem = (Usable) item;
            if (usableItem.canUse()) {
                usableItem.use(player);
                player.removeItem(item);
            }
        } else {
            System.out.println(GameText.CANT_USE_ITEM);
        }
    }

    private void equipItem(String itemName) {
        Item item = player.findItem(itemName);
        if (item instanceof Weapon) {
            player.equipWeapon(itemName);
            player.removeItem(item);
        } else {
            System.out.println(GameText.CANT_EQUIP_ITEM);
        }
    }

    private void attackEnemy() {
        Enemy enemy = currentRoom.getAliveEnemy();
        if (enemy != null) {
            Combat combat = new Combat();
            combat.startCombat(player, enemy);

            //Remove dead enemies from the room
            if (!enemy.isAlive()) {
                currentRoom.removeEnemy(enemy);
            }

            //Check if the player died
            if (!player.isAlive()) {
                System.out.println(GameText.GAME_OVER);
                gameRunning = false;
            }
        } else {
            System.out.println(GameText.NO_ENEMIES);
        }
    }

    private void unlockDoor() {
        long endTime = System.currentTimeMillis();
        long gameTimeMs = endTime - startTime;
        long seconds = gameTimeMs / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;

        if (currentRoom.getName().equals("Troll's Lair")) {
            if (player.findItem("Golden Key") != null) {
                if (!currentRoom.hasAliveEnemies()) {
                    System.out.println(GameText.DOOR_UNLOCK_START);
                    System.out.println(GameText.DOOR_UNLOCK_CLICK);
                    System.out.println(GameText.DOOR_OPENS);
                    System.out.println(GameText.VICTORY_CONGRATULATIONS);
                    System.out.printf(GameText.VICTORY_TIME + "%n", minutes, seconds);
                    System.out.println(GameText.THADE_FREED);
                    System.out.printf(GameText.THANKS_FOR_PLAYING + "%n", player.getName());
                    gameRunning = false;
                } else {
                    System.out.println(GameText.TROLL_STILL_ALIVE);
                }
            } else {
                System.out.println(GameText.NEED_GOLDEN_KEY);
            }
        } else {
            System.out.println(GameText.NO_DOOR_HERE);
        }
    }

    private void showHelp() {
        System.out.println(GameText.HELP_HEADER);
        System.out.println(GameText.HELP_GO);
        System.out.println(GameText.HELP_LOOK);
        System.out.println(GameText.HELP_TAKE);
        System.out.println(GameText.HELP_INVENTORY);
        System.out.println(GameText.HELP_STATS);
        System.out.println(GameText.HELP_USE);
        System.out.println(GameText.HELP_EQUIP);
        System.out.println(GameText.HELP_ATTACK);
        System.out.println(GameText.HELP_UNLOCK);
        System.out.println(GameText.HELP_HELP);
        System.out.println(GameText.HELP_HINT);
        System.out.println(GameText.HELP_QUIT);
    }

}
