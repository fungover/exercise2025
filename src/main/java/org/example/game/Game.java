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


    public void startGame() {
    System.out.println("Welcome to the Dungeon.... I have been waiting for an adventurer..");
    System.out.print("Please, tell me your name ");
    String name = scanner.nextLine();

    player = new Player(name);
    dungeon = new Dungeon(5, 5);
    Random random = new Random();

    List<Enemy> enemies = List.of(
            new Troll(0,0),
            new Orc(0,0)
    );

    List<Tile> emptyTiles =
            IntStream.range(0, dungeon.getRows())
                    .boxed()
                    .flatMap(row -> IntStream.range(0,dungeon.getCols())
                            .mapToObj(col ->dungeon.getTile(row,col)))
                    .filter(Tile::isWalkable)
                    .collect(Collectors.toCollection(ArrayList::new));

    for (Enemy enemy: enemies) {
        Tile tile = emptyTiles.remove(random.nextInt(emptyTiles.size()));
        tile.setEnemy(enemy);
        enemy.moveTo(tile.getRow(), tile.getCol());

        System.out.println(enemy.getType() + " placed at row " + enemy.getRow() + ", col " + enemy.getCol());
    }

        for (int r = 0; r < dungeon.getRows(); r++) {
            for (int c = 0; c < dungeon.getCols(); c++) {
                Tile t = dungeon.getTile(r, c);
                String s = t.getTileType();
                if (t.getEnemy() != null) s += " (" + t.getEnemy().getType() + ")";
                System.out.print(s + "\t");
            }
            System.out.println();
        }

        movementLogic = new MovementLogic();
    player.addItem(new Potion("Potion", 20));  // en test för att se om det funkar att se inventoryplayer.addItem(new Weapon("Fire Sword", 20));
    player.addItem(new Weapon("Wooden Sword", 10));
    player.addItem(new Weapon("Fire Sword", 10));

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
