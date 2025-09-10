package org.example.game;

import org.example.entities.enemies.Enemy;
import org.example.entities.enemies.Orc;
import org.example.entities.enemies.Troll;
import org.example.entities.items.Item;
import org.example.entities.Player;
import org.example.entities.items.Potion;
import org.example.entities.items.Weapon;
import org.example.map.Dungeon;
import org.example.service.MovementLogic;
import org.example.map.Tile;
import org.example.utils.Spawner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
private Scanner scanner = new Scanner(System.in);
private Player player;
private Dungeon dungeon;
private MovementLogic movementLogic;

    public void printDungeonDebug() {
        System.out.println("=== Dungeon Debug Map ===");
        for (int row = 0; row < dungeon.getRows(); row++) {
            for (int col = 0; col < dungeon.getCols(); col++) {
                Tile tile = dungeon.getTile(row, col);

                if (player.getRow() == row && player.getCol() == col) {
                    System.out.print("P "); // Player
                } else if (tile.getEnemy() != null) {
                    System.out.print("E "); // Enemy
                } else if (tile.getItem() != null) {
                    System.out.print("I "); // Item
                } else {
                    System.out.print(". "); // Tom tile
                }
            }
            System.out.println();
        }
        System.out.println("=========================");
    }


    public void startGame() {


        System.out.println("Welcome to the Dungeon.... I have been waiting for an adventurer..");
    System.out.print("Please, tell me your name ");
    String name = scanner.nextLine();

    player = new Player(name);
    dungeon = new Dungeon(5, 5);
    Random random = new Random();
    movementLogic = new MovementLogic();
    Spawner.spawnAll(dungeon);

    System.out.println("Well Hello, " + player.getName() + ". Step inside and see what happens...");
    System.out.println("Your health is " + player.getHealth());
    System.out.println("Lets go!");
        printDungeonDebug();


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
                    if (currentTile.getEnemy() != null) {
                        System.out.println("There is a " + currentTile.getEnemy().getType() + " here!");
                    }
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
                    player.usePotion();
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
