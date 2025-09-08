package org.example.game;

import org.example.entities.Item;
import org.example.entities.Player;
import org.example.entities.Potion;
import org.example.map.Dungeon;
import org.example.service.MovementLogic;
import org.example.map.Tile;
import java.util.Scanner;

public class Game {
private Scanner scanner = new Scanner(System.in);
private Player player;
private Dungeon dungeon;
private MovementLogic movementLogic;


    public void startGame() {
    System.out.println("Welcome to the Dungeon.... I have been waiting for an adventurer..");
    System.out.print("Please, tell me your name ");
    String name = scanner.nextLine();

    player = new Player(name);
    dungeon = new Dungeon(3, 3);
    movementLogic = new MovementLogic();
    player.addItem(new Potion("Small Potion", 20)); // en test för att se om det funkar att se inventory

    System.out.println("Well Hello, " + player.getName() + ". Step inside and see what happens...");
    System.out.println("Your health is " + player.getHealth());
    System.out.println("Lets go!");


        while (true) {
            System.out.print("> ");
            String playerInput = scanner.nextLine();

            switch (playerInput) {
                case "move up":
                    movementLogic.movePlayer(player, "up", dungeon);
                    //  Flytta spelaren upp (north) om nästa tile är walkable
                    break;

                    case "move down":
                        movementLogic.movePlayer(player, "down", dungeon);
                    //  Flytta spelaren ner (south) om nästa tile är walkable
                    break;

                case "move left":
                    movementLogic.movePlayer(player, "left", dungeon);
                    //  Flytta spelaren vänster (west) om nästa tile är walkable
                    break;

                case "move right":
                    movementLogic.movePlayer(player, "right", dungeon);
                    //  Flytta spelaren höger (east) om nästa tile är walkable
                    break;

                case "look":
                    Tile currentTile = dungeon.getTile(player.getRow(), player.getCol());
                    System.out.println("You are standing on a: " + currentTile.getTileType() + " tile");
                    //  Visa vilken tile spelaren står på
                    break;

                case "inventory":
                    if (player.getInventory().isEmpty()) {
                        System.out.println("Nothing in the inventory");
                    } else {
                        System.out.println("You have:");
                        for (Item item : player.getInventory()) {
                            System.out.println("- " + item.getName());
                        }
                    }
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

//För OOP = låta Player eller en MovementService hantera förflyttning - inte i Game

    }



}
