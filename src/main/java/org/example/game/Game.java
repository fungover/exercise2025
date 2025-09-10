package org.example.game;

import org.example.entities.enemies.Enemy;
import org.example.entities.items.Item;
import org.example.entities.Player;
import org.example.map.Dungeon;
import org.example.service.Combat;
import org.example.service.MovementLogic;
import org.example.map.Tile;
import org.example.service.PotionLogic;
import org.example.utils.Spawner;
import java.util.Random;
import java.util.Scanner;


public class Game {
    private Scanner scanner = new Scanner(System.in);
    private Player player;
    private Dungeon dungeon;
    private MovementLogic movementLogic;

    private boolean allEnemiesDefeated() {
        for (int row = 0; row < dungeon.getRows(); row++) {
            for (int col = 0; col < dungeon.getCols(); col++) {
                Tile tile = dungeon.getTile(row, col);
                if (tile.getEnemy() != null && tile.getEnemy().isAlive()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void startGame() {
        System.out.println("Welcome to the Dungeon.... I have been waiting for an adventurer..");
        System.out.print("Please, tell me your name ");
        String name = scanner.nextLine();

        player = new Player(name);
        dungeon = new Dungeon(5, 5);
        Combat combat = new Combat();
        PotionLogic potionLogic = new PotionLogic();
        Random random = new Random();
        movementLogic = new MovementLogic();
        Spawner.spawnAll(dungeon);

        int previousRow = player.getRow();
        int previousCol = player.getCol();

        System.out.println("Well Hello, " + player.getName() + ". Step inside and see what happens...");
        System.out.println("Your health is " + player.getHealth());
        System.out.println("Lets go!");


        while (true) {
            System.out.print("> ");
            String playerInput = scanner.nextLine();
            Tile currentTile = dungeon.getTile(player.getRow(), player.getCol());

            switch (playerInput) {
                case "move up":
                    previousRow = player.getRow();
                    previousCol = player.getCol();
                    movementLogic.movePlayer(player, "up", dungeon);
                    break;

                case "move down":
                    previousRow = player.getRow();
                    previousCol = player.getCol();
                    movementLogic.movePlayer(player, "down", dungeon);
                    break;

                case "move left":
                    previousRow = player.getRow();
                    previousCol = player.getCol();
                    movementLogic.movePlayer(player, "left", dungeon);
                    break;

                case "move right":
                    previousRow = player.getRow();
                    previousCol = player.getCol();
                    movementLogic.movePlayer(player, "right", dungeon);
                    break;

                case "look":
                    currentTile = dungeon.getTile(player.getRow(), player.getCol());
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
                    break;

                case "attack":
                    Enemy enemy = currentTile.getEnemy();
                    if (enemy != null) {
                        if (player.hasWeapon()) {
                            combat.fight(player, enemy);
                            if (!enemy.isAlive()) {
                                currentTile.setEnemy(null);
                            }
                        } else {
                            System.out.println("You have no weapon! Go and find one first.");
                        }
                    } else {
                        System.out.println("There is no enemy here to attack!");
                    }
                    break;

                case "use potion":
                    potionLogic.usePotion(player);
                    break;
                case "move back":
                    player.moveTo(previousRow, previousCol);
                    System.out.println("You moved back to the previous tile.");
                    break;

                case "quit":
                    System.out.println("Thanks for playing!");
                    return;

                default:
                    System.out.println("Nothing happens... maybe try another move?");

            }
            currentTile = dungeon.getTile(player.getRow(), player.getCol());

            if (currentTile.getItem() != null) {
                Item item = currentTile.getItem();
                player.addItem(item);
                currentTile.setItem(null);
                System.out.println("You picked up a " + item.getName() + "!");
            }

            if (playerInput.startsWith("move") && currentTile.getEnemy() != null) {
                System.out.println("There is a " + currentTile.getEnemy().getType() + " here!");
                System.out.println("Type 'attack' to fight or 'move back' to retreat.");
            }

            if (allEnemiesDefeated()) {
                System.out.println("Congratulations! You defeated all enemies and won the game!");
                break;
            }

        }
    }
}
