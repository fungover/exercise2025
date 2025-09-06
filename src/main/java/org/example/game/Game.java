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
    public static void run() {
        Scanner scan = new Scanner(System.in);
        System.out.println("--------------------------------");
        System.out.println("\uD83E\uDDD9Welcome to my Dungeon Crawler!");
        System.out.print("State your name, o' brave challenger: ");
        String name = scan.nextLine();
        System.out.println("Welcome, WELCOME " + name + "! Your adventure begins...");
        System.out.println("--------------------------------");



        final Player player = new Player(name, 70);
        final Inventory inventory = new Inventory();
        final GameLogic logic = new GameLogic();
        Dungeon dungeon = new Dungeon(player);

        // Game loop
        while (true) {

            // Items
            inventory.addItem(new Weapon("Sword", 1, 20));
            inventory.addItem(new HealthPotion("Health Potion", 0));

            dungeon.printDungeon();

            if (logic.wishToPickUpItem(dungeon, player, scan)) {
                HealthPotion potion;
                if (inventory.getHealthPotion() != null) {
                    potion = inventory.getHealthPotion();
                } else {
                    potion = new HealthPotion("Health Potion", 0);
                }
                inventory.addItem(potion);
                System.out.println(potion.getName() + " has been picked up!");
            }

            if (logic.wishToFightEnemy(dungeon, player, scan)) {
                Weapon weapon = inventory.getWeapon();
                HealthPotion potion = inventory.getHealthPotion();
                Combat combat = new Combat();
                combat.startFight(player, weapon, potion, scan);
            }

            logic.moveInput(dungeon, player, scan);
            logic.renderPlayerPosition(dungeon, player);
        }
    }
}
