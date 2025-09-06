package org.example.game;

import org.example.entities.Player;
import org.example.entities.items.HealthPotion;
import org.example.entities.items.Inventory;
import org.example.entities.items.Weapon;
import org.example.map.Dungeon;
import org.example.service.Combat;
import org.example.service.GameLogic;

import java.util.Scanner;

public class Game {

    // All game logic is executed here.
    public static void run() throws InterruptedException {
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
        final GameLogic logic = new GameLogic();
        Dungeon dungeon = new Dungeon(player);

        // Items
        inventory.addItem(new Weapon("Sword", 1, 20));

        // Game loop
        while (true) {
            // "Clear" the terminal
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
            dungeon.printDungeon(inventory);

            if (logic.wishToPickUpItem(dungeon, player, scan)) {
                HealthPotion potion;
                if (inventory.getHealthPotion() != null) {
                    potion = inventory.getHealthPotion();
                } else {
                    potion = new HealthPotion("Health Potion", 1);
                }
                inventory.addItem(potion);
                System.out.println("A Health Potion has been picked up!");
            }

            if (logic.wishToFightEnemy(dungeon, player, scan)) {
                Combat combat = new Combat();
                combat.startFight(player, inventory, scan);
            }

            System.out.print("Enter command (or 'q' to quit game): ");
            String userInput = scan.nextLine();
            if (userInput.equalsIgnoreCase("q")) {
                System.out.println("Exiting game...");
                break;
            }
            logic.moveInput(dungeon, player, userInput);

            logic.renderPlayerPosition(dungeon, player);
            pause(); // This makes it possible to read messages before rerender
        }
    }

    private static void countDown() throws InterruptedException {
        System.out.print("Your adventure begins in 3, ");
        Thread.sleep(1000);
        System.out.print("2, ");
        Thread.sleep(1000);
        System.out.print("1... ");
        Thread.sleep(1000);
    }

    private static void pause() throws InterruptedException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupt status
        }
    }
}
