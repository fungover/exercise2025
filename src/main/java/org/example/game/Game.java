package org.example.game;

import org.example.entities.Inventory;
import org.example.entities.Item;
import org.example.entities.Player;
import org.example.map.MapCreation;
import org.example.service.MovementEnum;

import java.util.Scanner;

public class Game {
    public static void GameStart(Player player) {
        boolean gameWon = false;
        MapCreation map = new MapCreation(10, 10);
        Inventory userInventory = player.getInventory();
        Scanner scanner = new Scanner(System.in);

        System.out.println("You wandered into a room and finds a health potion and a great sword");
        System.out.println("Do you wish to pick it up? Y/N");
        String itemChoice = scanner.nextLine().trim();

        if (itemChoice.equalsIgnoreCase("Y")) {
            Item sword = new Item("sword", "weapon");
            Item healthPotion = new Item("Health potion", "health_potion");

            userInventory.addItem(sword);
            userInventory.addItem(healthPotion);

            player.setDamage(15);

            System.out.println("You picked up the items and continued your journey.");
        } else {
            System.out.println("You chose to not pick up the items and continued your journey.");
        }

        while (player.getHealth() > 0 && !gameWon) {
            System.out.println("Where do you want to go? (up, down, left, right)");

            String choice = scanner.nextLine().trim();
            MovementEnum movement = MovementEnum.movementFromString(choice);

            if (movement == null) {
                System.out.println("Invalid choice. Try again.");
                continue;
            }

            int nextX = player.getPositionX() + movement.newX;
            int nextY = player.getPositionY() + movement.newY;

            if (!map.insideMap(nextX, nextY)) {
                System.out.println("When walking that direction you hit a wall. Please move another direction.");
                continue;
            }

            player.setPositionX(nextX);
            player.setPositionY(nextY);

            map.getTile(player.getPositionX(), player.getPositionY()).onEnter(player, map.getTile(player.getPositionX(), player.getPositionY()));
        }
    }
}
