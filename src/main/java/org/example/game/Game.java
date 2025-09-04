package org.example.game;

import org.example.entities.*;

import java.util.Scanner;

import static org.example.utils.Colors.*;

public class Game {
    private Scanner scanner;
    private Player player;
    private Room currentRoom;
    private boolean gameRunning;

    public Game() {
        scanner = new Scanner(System.in);
        gameRunning = true;
    }

    public void start() {
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
        System.out.println(bold("\n=== Welcome to 'Escape The Dark Cave' ==="));
        System.out.println("\n* You find yourself waking up in a dark cave *");
        System.out.println("\nAn old crackly voice speaks to you: ");
        System.out.println(purple("-Hello friend.. I thought you were dead.."));
        System.out.println("\n* You feel a cold shiver running down your spine *");
        System.out.println(purple("\n-Don't worry friend, I will get you out of here.."));
        System.out.println(purple("..hehe..in one way or another..."));
        System.out.println(purple("\nBut, before I help you, let's get to know each other a little bit.."));
        System.out.println(purple("\nI'm Thade, I used to live here.. a long time ago.."));
        System.out.println(purple("(whispers) ..before the monsters came.."));
        System.out.println(purple("\n-What is your name, friend? "));
    }

    private void continueStory() {
        System.out.println(purple("\n-Listen carefully now.."));
        System.out.print(purple("Hey, "));
        System.out.print(cyan(player.getName()));
        System.out.print(purple("! "));
        System.out.println(bold(purple("Are you PAYING ATTENTION?")));


    }

    private void GameInstructions() {
        System.out.println(purple("\nYou need to find a the golden key that will unlock the door to the exit.."));
        System.out.println(purple("The key is hidden somewhere in the cave.."));
        System.out.println(purple("Be careful, there are dangerous monsters everywhere.. "));
        System.out.println(purple("You can't get passed them or run away..."));
        System.out.println(purple(".. you have to fight for your life first.."));
        System.out.println(purple("There are weapons and healing potions spread around here.."));
        System.out.print(purple("And " ));
        System.out.print(cyan(player.getName()));
        System.out.println(purple( ".. I suggest you find them first.."));
        System.out.println(purple("oh.. and watch out for THE TROLL, he is guarding the exit.."));
        System.out.print(purple("\nGood luck frie.. hehe.. "));
        System.out.print(cyan(player.getName()));
        System.out.print(purple(".. you'll need it!"));
        System.out.println("\nYou can type 'help' to see available commands");
    }

    private String getPlayerName() {
        String name = scanner.nextLine();
        System.out.print(purple("\n-"));
        System.out.print(cyan(name));
        System.out.print(purple( ".. interesting name.."));
        System.out.print(purple("\nOkay, friend.. I mean "));
        System.out.print(cyan(name));
        System.out.println(purple(".."));
        return name;
    }

    private void getPlayerAttention() {
        boolean answerYes = false;
        while (!answerYes) {
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                answerYes = true;
                System.out.println(purple("\nGood! Here is what you need to do, in order to get out of here.."));
            } else {
                System.out.println(purple("Okay..It's like that huh? I can wait forever.. \nSay 'yes' when you want to continue.."));
            }
        }
    }

    private void gameLoop() {
        currentRoom.look();

        while (gameRunning && player.isAlive()) {
            System.out.println("\n> What do you want to do?");
            String command = scanner.nextLine().toLowerCase();
            handleCommand(command);
        }
    }

    private void handleCommand(String command) {
        String[] words = command.split(" ");
        String action = words[0];

        switch (action) {
            case "go":
            case "move":
                if (words.length > 1) {
                    movePlayer(words[1]);
                } else {
                    System.out.println("Go where? (north, south, east, west)");
                }
                break;

            case "look":
            case "l":
                currentRoom.look();
                break;

            case "take":
                if (words.length > 1) {
                    String itemName = command.substring(5); //Remove "take " (5 characters)
                    takeItem(itemName.trim());
                } else {
                    System.out.println("Take what?");
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
                    String itemName = command.substring(4); // Remove "use " (4 characters)
                    useItem(itemName.trim());
                } else {
                    System.out.println("Use what?");
                }
                break;

            case "equip":
                if (words.length > 1) {
                    String itemName = command.substring(6); // Remove "equip " (6 characters)
                    equipItem(itemName.trim());
                } else {
                    System.out.println("Equip what?");
                }
                break;

            case "unlock":
            case "open":
                if (words.length > 1 && words[1].equalsIgnoreCase("door")) {
                    unlockDoor();
                } else {
                    System.out.println("Unlock what? Try 'unlock door'");
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
                System.out.println(yellow("\nHere are a few tips:"));
                System.out.println("Items you see needs to be picked up before you can use/equip them.");
                System.out.println("You can pick up items by typing 'take <item>'.");
                System.out.println("You can use items by typing 'use <item>'.");
                System.out.println("You can equip items by typing 'equip <item>'.");
                System.out.println("Once you see an enemy you can't leave the room without fighting first.");
                System.out.println("You can take, use/equip items before fighting.");
                System.out.println("There is no compass, you need to keep track of where you are..");
                System.out.println("For available commands, type 'help'.");
                break;


            case "quit":
                gameRunning = false;
                System.out.println("Thanks for playing!");
                break;

            case "exit":
                System.out.println("To quit the game, type 'quit'");
                System.out.println("For other commands, type 'help'");
                break;

            default:
                System.out.println("I don't understand that command. Type 'help' for available commands.");
        }
    }

    private void movePlayer(String direction) {
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom != null) {
            if (currentRoom.hasAliveEnemies()) {
                System.out.println("You can't leave while enemies are blocking your way!");
                return;
            }
            currentRoom = nextRoom;
            currentRoom.look();
        } else {
            System.out.println("You can't go that way!");
        }
    }

    private void takeItem(String itemName) {
        Item item = currentRoom.findItem(itemName);
        if (item != null) {
            if (player.addItem(item)) {
                currentRoom.removeItem(item);
                System.out.println("You picked up: " + item.getName());
            } else {
                System.out.println("Your inventory is full!");
            }
        } else {
            System.out.println("There's no " + itemName + " here!");
            System.out.println("Items in room:");
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
            System.out.println("You can't use that item!");
        }
    }

    private void equipItem(String itemName) {
        Item item = player.findItem(itemName);
        if (item != null && item instanceof Weapon) {
            player.equipWeapon(itemName);
            player.removeItem(item);
        } else {
            System.out.println("You can't equip that!");
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
                System.out.println(red("*** GAME OVER! ***"));
                gameRunning = false;
            }
        } else {
            System.out.println("There are no enemies here to fight!");
        }
    }

    private void unlockDoor() {
        if (currentRoom.getName().equals("Troll's Lair")) {
            if (player.findItem("Golden Key") != null) {
                if (!currentRoom.hasAliveEnemies()) {
                    System.out.println(yellow("\n=== You insert the Golden Key into the massive door... ==="));
                    System.out.println("=== *CLICK* The door unlocks with a satisfying sound! ===");
                    System.out.println("\n=== The door swings open, revealing bright sunlight! ===");
                    System.out.println("=== You step outside and breathe fresh air for the first time in ages. ===");
                    System.out.println(bold(green("\n=== 🎉 CONGRATULATIONS! YOU HAVE ESCAPED THE DARK CAVE! 🎉 ===")));
                    System.out.println("\n=== Thade's corruption is broken, and you are finally free! ===");
                    System.out.println(cyan("\n=== Thanks for playing " + player.getName() + "! ==="));
                    gameRunning = false;
                } else {
                    System.out.println("You can't unlock the door while the troll is still alive!");
                }
            } else {
                System.out.println("You need the Golden Key to unlock this door!");
            }
        } else {
            System.out.println("There's no door to unlock here.");
        }
    }

    private void showHelp() {
        System.out.println(yellow("\n=== Available Commands ==="));
        System.out.println("go <direction> - Move (north, south, east, west)");
        System.out.println("look           - Look around the room");
        System.out.println("take <item>    - Pick up an item");
        System.out.println("inventory      - Show your items");
        System.out.println("stats          - Show your health and damage");
        System.out.println("use <item>     - Use healing potion");
        System.out.println("equip <item>   - Equip weapon");
        System.out.println("attack         - Fight enemies in the room");
        System.out.println("unlock         - Unlock door");
        System.out.println("help           - Show this help");
        System.out.println("stuck or hint  - Get some tips..");
        System.out.println("quit           - Exit game");
    }

}
