package org.example.game;

import org.example.entities.Enemy;
import org.example.entities.Player;
import org.example.entities.items.Item;
import org.example.utils.Enemies;
import org.example.entities.items.Inventory;
import org.example.entities.items.Weapon;
import org.example.map.Dungeon;
import org.example.service.Combat;
import org.example.service.GameLogic;
import org.example.utils.ItemsOnFloor;

import java.util.Scanner;

public class Game {

    // All game logic is executed here.
    public static void run() {
        Scanner scan = new Scanner(System.in);
        System.out.println("--------------------------------");
        System.out.println("\uD83E\uDDD9Welcome to my Dungeon Crawler!");
        System.out.print("State your name, o' brave challenger: ");
        String name = scan.nextLine();
        System.out.println("Welcome, WELCOME " + name + "!");
        System.out.println("--------------------------------");
        countDown();

        final Player player = new Player(name, 70);
        final Inventory inventory = new Inventory();
        final Enemies enemies = new Enemies(); // Enemy List
        final ItemsOnFloor itemsOnFloor = new ItemsOnFloor(); // Item List
        final GameLogic logic = new GameLogic();
        final Dungeon dungeon = new Dungeon(player, enemies, itemsOnFloor);

        // Items
        inventory.addItem(new Weapon("Sword", 20));

        // Game loop
        while (true) {
            dungeon.printDungeon(inventory);

            if (logic.wishToPickUpItem(dungeon, player, scan)) {
                Item item = logic.getItemFromList(dungeon, player, itemsOnFloor);
                logic.pickUpItem(item, inventory);
                dungeon.removeItemTile(item);
                pause();
                dungeon.printDungeon(inventory);
            }

            if (logic.wishToFightEnemy(dungeon, player, scan)) {
                Combat combat = new Combat();
                Enemy enemy = logic.getEnemyFromList(dungeon, player, enemies);
                combat.startFight(player, inventory, enemy, scan);
                dungeon.removeEnemyTile(enemy);
                pause();
                dungeon.printDungeon(inventory);
            }

            System.out.print("Enter command (or 'q' to quit game): ");
            String userInput = scan.nextLine();
            if (userInput.equalsIgnoreCase("q")) {
                System.out.println("Exiting game...");
                break;
            }
            logic.moveInput(dungeon, player, userInput);

            dungeon.renderPlayerPosition(player);
            pause(); // This makes it possible to read messages before re-render
        }
    }

    private static void countDown() {
        try {
            System.out.print("Your adventure begins in 3, ");
            Thread.sleep(1000);
            System.out.print("2, ");
            Thread.sleep(1000);
            System.out.print("1... ");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Something went terribly wrong.");
        }
    }

    private static void pause() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupt status
        }
    }
}
