package org.example.game;

import org.example.entities.Player;
import org.example.map.Dungeon;
import org.example.map.Tile;
import java.util.Scanner;

public class Game {
private Scanner scanner = new Scanner(System.in);
private Player player;
private Dungeon dungeon;
private int playerRow;
private int playerCol;

    public void startGame() {
    System.out.println("Welcome to the Dungeon.... I have been waiting for an adventurer..");
    System.out.print("Please, tell me your name ");
    String name = scanner.nextLine();

    player = new Player(name);
    System.out.println("Well Hello, " + player.getName() + ". Step inside and see what happens...");
    System.out.println("Your health is " + player.getHealth());

    // making the dungeon and start position and player start at index 0,0
        dungeon = new Dungeon(3,3);
        playerRow = 0;
        playerCol = 0;

        while (true) {
            System.out.print("> ");
            String playerInput = scanner.nextLine();

            switch (playerInput) {
                case "move up":
                    //  Flytta spelaren upp (north) om nästa tile är walkable
                    break;

                    case "move down":
                    //  Flytta spelaren ner (south) om nästa tile är walkable
                    break;

                case "move left":
                    //  Flytta spelaren vänster (west) om nästa tile är walkable
                    break;

                case "move right":
                    //  Flytta spelaren höger (east) om nästa tile är walkable
                    break;

                case "look":
                    //  Visa vilken tile spelaren står på
                    break;

                case "inventory":
                    //  Visa spelarens inventory
                    break;

                case "attack":
                    //  Hantera attack om det finns en fiende på tile
                    break;

                case "use potion":
                    //  Använd en potion från inventory
                    break;

                case "quit":
                    //  Avsluta
                    break;

                default:
                    System.out.println("Nothing happens... maybe try another move?");

            }
        }



    }


}
