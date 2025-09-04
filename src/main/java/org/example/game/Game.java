package org.example.game;

import org.example.entities.*;

import java.util.Scanner;

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
        initializeWorld();
        gameLoop();
    }

    private void showIntroduction() {
        System.out.println("\n=== Welcome to 'Escape The Dark Cave' ===");
        System.out.println("\n* You find yourself waking up in a dark cave *");
        System.out.println("\nAn old crackly voice speaks to you: ");
        System.out.println("-Hello friend.. I thought you were dead..");
        System.out.println("\n* You feel a cold shiver running down your spine *");
        System.out.println("\n-Don't worry friend, I will get you out of here..");
        System.out.println("\n(laughs) ..in one way or another...");
        System.out.println("\nBut, before I help you, let's get to know each other a little bit..");
        System.out.println("\nI'm Thade, I used to live here.. a long time ago..");
        System.out.println("(whispers) ..before the monsters came..");
        System.out.println("\n-What is your name, friend? ");
    }

    private void continueStory() {
        System.out.println("\n-Listen carefully now..");
        System.out.println("Hey, " + player.getName() + "! ARE YOU PAYING ATTENTION?");


    }

    private void GameInstructions() {
        System.out.println("\nYou need to find a the golden key that will unlock the door to the exit..");
        System.out.println("The key is hidden somewhere in the cave..");
        System.out.println("Be careful, there are dangerous monsters everywhere.. ");
        System.out.println("You can't get passed them..if you don't fight for your life..");
        System.out.println("There are weapons and healing potions spread around here..");
        System.out.println("And " + player.getName() + ".. \nI really suggest you find them first..");
        System.out.println("..and watch out for THE TROLL, he is guarding the exit..");
        System.out.println("\nGood luck frie.. hehe.. " + player.getName() + ".. you'll need it!");
        System.out.println("\nYou can type 'help' to see available commands");
    }

    private String getPlayerName() {
        String name = scanner.nextLine();
        System.out.println("\n-" + name + ".. interesting name..");
        System.out.println("Okay, friend.. I mean " + name + "..");
        return name;
    }

    private void getPlayerAttention() {
        boolean answerYes = false;
        while (!answerYes) {
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                answerYes = true;
                System.out.println("\nGood! Here is what you need to do, in order to get out of here..");
            } else {
                System.out.println("Okay..It's like that huh? I can wait forever.. \nSay 'yes' when you want to continue..");
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

            case "attack":
            case "fight":
                attackEnemy();
                break;

            case "help":
            case "h":
                showHelp();
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
            // Kolla om det finns levande fiender
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
            // DEBUG: Visa vad som finns
            System.out.println("Items in room:");
            for (Item roomItem : currentRoom.getItems()) {
                System.out.println("- " + roomItem.getName());
            }
        }
    }

    private void attackEnemy() {
        // TODO: Connect to Combat class
    }

    private void showHelp() {
        System.out.println("\n=== Available Commands ===");
        System.out.println("go <direction>  - Move (north, south, east, west)");
        System.out.println("look           - Look around the room");
        System.out.println("take <item>    - Pick up an item");
        System.out.println("inventory      - Show your items");
        System.out.println("stats          - Show your health and stats");
        System.out.println("attack         - Fight enemies in the room");
        System.out.println("help           - Show this help");
        System.out.println("quit           - Exit game");
    }


    private void initializeWorld() {
        // Create rooms
        Room startCave = new Room("Dark Cave",
                "The cave where you woke up. Cold stone walls surround you. \nThade's voice still echoes here.");

        Room weaponChamber = new Room("Weapon Chamber",
                "An old armory. Rusty weapons hang on the walls.");

        Room corridor = new Room("Stone Corridor",
                "A long, narrow corridor. You hear growling in the distance.");

        Room trollLair = new Room("Troll's Lair",
                "A massive chamber. The exit door is here, but a huge troll blocks your way!");

        // Connect rooms
        startCave.addExit("east", weaponChamber);
        startCave.addExit("north", corridor);

        weaponChamber.addExit("west", startCave);

        corridor.addExit("south", startCave);
        corridor.addExit("north", trollLair);

        trollLair.addExit("south", corridor);

        // Add items
        Weapon sword = new Weapon("Sword", "An rusty old sword", "Weapon", 1, 15);
        Healing potion = new Healing("Healing Potion", "A red liquid that smells of herbs", "Healing", 1, 30);

        weaponChamber.addItem(sword);
        startCave.addItem(potion);

        // Add enemies
        Enemy goblin = new Enemy("Cave Goblin", "A small but vicious creature", 20, 8);
        Enemy troll = new Enemy("Giant Troll", "The massive guardian of the exit", 80, 20);

        corridor.addEnemy(goblin);
        trollLair.addEnemy(troll);

        // Start in the cave
        currentRoom = startCave;
    }

}
