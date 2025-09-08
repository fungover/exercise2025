package org.example.game;

import org.example.map.DungeonGenerator;
import org.example.map.DungeonMap;
import org.example.entities.Player;
import org.example.entities.Tile;
import org.example.utils.RandomUtils;
import org.example.service.MovementService;

import java.util.Scanner;

public class Game {

    private static int difficulty;
    private static int startLocation;
    private static int wallCount;
    private static int itemCount;
    private static int enemyCount;


    public static void main(String[] args) {

        //Player name and difficulty selection

        Scanner input = new Scanner(System.in);
        //System.out.println("\nWelcome to the Micro Dungeon crawler\n");
        System.out.println("\n      /| The Great!");
        System.out.println("O|xxxx|>================>");
        System.out.println("      \\|    Not so great: Dungeon Crawler!");
        System.out.print(" \nEnter Player Name: ");
        String PlayerName = input.nextLine();

        System.out.println("\nSelect difficulty(just type in the number):\n 1. Easy 2. Normal 3. Hard \n");
        String choice = "";
        do {
            System.out.print("Choose your fate: ");
            choice = input.nextLine();
            if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
                System.out.println("Invalid choice. Try again");
            }
        } while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3"));

        int difficultyChoice = Integer.parseInt(choice);
        difficultyModifier(difficultyChoice);

        //Map being generated
        Tile[][] generated = DungeonGenerator.generateDungeon(difficulty, difficulty);
        DungeonMap map = new DungeonMap(generated);

        //Player being generated
        Player player = new Player(PlayerName, 100, 20, startLocation, startLocation);
        generated[player.getX()][player.getY()].setVisited(true);

        //All objects being placed on map
        RandomUtils.placeAllObjects(generated, wallCount, itemCount, enemyCount, player);

        //Game loop
        boolean running = true;
        while (running) {
            map.print(player);

            System.out.println("Commands: [go north] [go south] [go west] [go east] [look] [attack] [quit]");
            System.out.print("Enter your command: ");
            String command = input.nextLine().trim().toLowerCase();

            if (command.equals("quit")) {
                System.out.println("Thanks for playing! Better luck next time!");
                running = false;
            } else {
                MovementService.movePlayer(command, player, generated);
            }

            if (player.getHealth() <= 0) {
                System.out.println("You're out of health! Game over!");
                running = false;
            }

            if (!enemiesRemain(generated)) {
                System.out.println("Congratulations, all enemies are defeated! \n You have successfully saved whoever it is you felt in your heart that you wanted to save!");
                running = false;
            }
        }
    }

    // All individual difficulty settings.
    private static void difficultyModifier(int difficultyChoice) {
        difficulty = switch (difficultyChoice) {
            case 1 -> 5;
            case 2 -> 11;
            case 3 -> 15;
            default -> 11;
        };

        startLocation = switch (difficultyChoice) {
            case 1 -> 2;
            case 2 -> 5;
            case 3 -> 7;
            default -> 5;
        };

        wallCount = switch (difficultyChoice) {
            case 1 -> 10;
            case 2 -> 25;
            case 3 -> 40;
            default -> 15;
        };

        itemCount = switch (difficultyChoice) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 4;
            default -> 2;
        };

        enemyCount = switch (difficultyChoice) {
            case 1 -> 3;
            case 2 -> 20;
            case 3 -> 45;
            default -> 20;
        };
    }

    //checks the dungeon for remaining enemies
    private static boolean enemiesRemain(Tile[][] generated) {
        for (Tile[] row : generated) {
            for (Tile tile : row) {
                if (tile.getEnemy() != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
