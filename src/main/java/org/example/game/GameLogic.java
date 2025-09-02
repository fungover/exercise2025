package org.example.game;

import org.example.entities.characters.Player;
import org.example.entities.enemies.Goblin;
import org.example.entities.items.Item;
import org.example.entities.items.Weapon;
import org.example.map.DungeonGrid;
import org.example.service.SpawnService;
import org.example.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameLogic {
    private static final Logger LOGGER = Logger.getLogger(GameLogic.class.getName());

    private DungeonGrid grid;
    private SpawnService spawnService;
    private boolean gameOver;
    private String difficulty;
    private int health;
    private int maxHealth;
    private Weapon startingWeapon;
    private String name;


    public void initializeGame() {
        System.out.println("=== Welcome to the dungeon!===");
        Utils.newRow();
        name = getName();

        Utils.newRow();
        chooseDifficulty();

        Utils.newRow();
        chooseMapSize();

        Utils.newRow();
        startGame();
    }

    public String getName() {
        System.out.println("Please enter a name for your player");
        String name = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            name = reader.readLine();
            System.out.println("Your name is " + name);

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error storing name", ex);
        }

        return name;
    }

    public void chooseDifficulty() {
        boolean validChoice = false;
        System.out.println("Please choose a dififculty level:");

        do {
            System.out.println("1. Easy");
            System.out.println("2. Medium");
            System.out.println("3. Hard");

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String input = reader.readLine();
                int choice = Integer.parseInt(input);

                if (choice < 1 || choice > 3) {
                    System.out.println("Invalid input. Please try again.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        difficulty = "easy";
                        health = 150;
                        maxHealth = 150;
                        startingWeapon = new Weapon("The Unlikely Mop", "Turns out this mop can clean up more than just floors.", 20, 30);
                        break;
                    case 2:
                        difficulty = "medium";
                        health = 100;
                        maxHealth = 100;
                        startingWeapon = new Weapon("The Rusty Spoon of Destiny", "It's not what you'd expect, but it's what you've got.", 10, 20);
                        break;
                    case 3:
                        difficulty = "hard";
                        health = 50;
                        maxHealth = 50;
                        startingWeapon = new Weapon ("The Pebble of Regret", "A simple rock. You've clearly made some poor life choices.", 1, 5);
                        break;
                    default:
                }

                validChoice = true;
                System.out.println("You have chosen the " + difficulty + " difficulty.");

            } catch (IOException | NumberFormatException ex) {
                LOGGER.log(Level.SEVERE, "Error choosing a difficulty", ex);
            }

        } while (!validChoice);
    }

    private void chooseMapSize() {
        boolean validChoice = false;
        System.out.println("Please choose a map size:");

        do {
            System.out.println("1. Small (10x10)");
            System.out.println("2. Medium (15x15)");
            System.out.println("3. Large (20x20)");

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String input = reader.readLine();
                int choice = Integer.parseInt(input);

                if (choice < 1 || choice > 3) {
                    System.out.println("Invalid input. Please try again.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        grid = new DungeonGrid(10, 10);
                        break;

                    case 2:
                        grid = new DungeonGrid(15, 15);
                        break;

                    case 3:
                        grid = new DungeonGrid(20, 20);
                        break;
                }

                validChoice = true;
                System.out.println("You have chosen a " + grid.getWidth() + "x" + grid.getHeight() + " map.");

            } catch (IOException | NumberFormatException ex) {
                LOGGER.log(Level.SEVERE, "Error choosing a map", ex);
            }

        } while (!validChoice);
    }

    public void startGame() {
        grid = DungeonGrid.createDungeonGrid(grid.getWidth(), grid.getHeight());
        int[] optimalStartPosition = grid.getOptimalStartPosition();

        spawnService = new SpawnService();
        spawnService.spawnEnemies(grid, 10, () -> new Goblin());

        int x = optimalStartPosition[0];
        int y = optimalStartPosition[1];

        Player player = new Player(name, health, maxHealth, x, y, startingWeapon );

        while ( !gameOver) {

                System.out.println("You are in the dungeon. Type 'help' for a list of commands.");

                gameOver = true;

        }
        endGame();
    }

    public void endGame() {
        System.out.println("=== Game Over ===");
        /* if () {
            System.out.println("Congratulations! You cleared the dungeon.");
        } else {
            System.out.println("You have died. Better luck next time!");
        } */
    }
}
