package org.example.game;

import org.example.map.DungeonGenerator;
import org.example.map.DungeonMap;
import org.example.entities.Player;
import org.example.entities.Tile;
import org.example.service.MovementService;

import java.util.Scanner;

public class Game {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Micro Dungeon crawler\n");
        System.out.print("Enter Player Name: ");
        String PlayerName = input.nextLine();

        System.out.println("Select difficulty(just type in the number):\n 1. Easy 2. Normal 3. Hard");
        String choice = "";
        do {
            System.out.print("Choose your fate: ");
            choice = input.nextLine();
            if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
                System.out.println("Invalid choice. Try again");
            }
        } while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3"));

        int difficultyChoice = Integer.parseInt(choice);

        int difficulty = switch (difficultyChoice) {
            case 1 -> 5;
            case 2 -> 11;
            case 3 -> 15;
            default -> 10;
        };

        int startLocation = switch (difficultyChoice) {
            case 1 -> 1;
            case 2 -> 5;
            case 3 -> 7;
            default -> 2;
        };

        Tile[][] generated = DungeonGenerator.generateDungeon(difficulty, difficulty);
        DungeonMap map = new DungeonMap(generated);

        Player player = new Player(PlayerName, 100, startLocation, startLocation);
        generated[player.getX()][player.getY()].setVisited(true);

        map.print(player);
        //this code will change to have an ongoing game loop. Adding this now to test movement
        System.out.println("Commands: [go north] [go south] [go west] [go east] [look]");
        System.out.print("Enter your command: ");
        String command = input.nextLine().trim().toLowerCase();
        MovementService.movePlayer(command, player, generated);
        map.print(player);
    }
}
