package org.example.game;

import org.example.entities.characters.Player;
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
    }

    public void chooseDifficulty() {
        boolean validChoice = false;

        do {
        System.out.println("Please choose a dificulty level:");
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

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        } while (!validChoice);

        System.out.println("You have chosen the " + difficulty + " difficulty.");
    }

    public void startGame() {

    }

    public void endGame() {}
}
