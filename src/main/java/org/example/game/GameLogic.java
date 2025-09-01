package org.example.game;

import org.example.entities.characters.Player;
import org.example.entities.items.Item;
import org.example.map.DungeonGrid;
import org.example.service.SpawnService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameLogic {
    private static final Logger LOGGER = Logger.getLogger(GameLogic.class.getName());

    private DungeonGrid grid;
    private Player player;
    private SpawnService spawnService;
    private boolean gameOver;
    private String difficulty;


    public void initializeGame() {
        System.out.println("=== Welcome to the dungeon!===");
        chooseDifficulty();
        chooseMapSize();


    }

    public String getName() {
        System.out.println("Please enter you name");
        String name = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            name = reader.readLine();
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
                        break;
                    case 2:
                        difficulty = "medium";
                        break;
                    case 3:
                        difficulty = "hard";
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
        System.out.println("\nPlease choose a map size:");

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
        while ( !gameOver) {

        }
        endGame();
    }

    public void endGame() {
        System.out.println("=== Game Over ===");
        if (/* player won */) {
            System.out.println("Congratulations! You cleared the dungeon.");
        } else {
            System.out.println("You have died. Better luck next time!");
        }
    }
}
