package org.example.game;

import org.example.entities.Inventory;
import org.example.entities.Item;
import org.example.entities.Player;
import org.example.map.MapCreation;
import org.example.service.MovementEnum;

import java.util.Scanner;

public class Game {
    public static void GameStart(Player player, Inventory inventory) {
        MapCreation map = new MapCreation(10, 10);

        Scanner scanner = new Scanner(System.in);

        System.out.println("You wandered into a room and finds a health potion and a great sword");
        System.out.println("Do you wish to pick it up? Y/N");
        String itemChoice = scanner.next();

        if (itemChoice.equalsIgnoreCase("Y")) {
            Item sword = new Item("sword", "weapon");
            Item healthPotion = new Item("healthPotion", "potion");

            inventory.addItem(sword);
            inventory.addItem(healthPotion);

            System.out.println("You picked up the items and continued your journey.");
        } else {
            System.out.println("You chose to not pick up the items and continued your journey.");
        }

        while (true) {
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

            map.getTile(player.getPositionX(), player.getPositionY()).onEnter(player);
        }
    }
}
