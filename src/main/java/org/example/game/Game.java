package org.example.game;

import org.example.entities.Inventory;
import org.example.entities.Player;
import org.example.map.MapCreation;
import org.example.service.MovementEnum;

import java.util.Scanner;

public class Game {
    public static void GameStart(Player player, Inventory inventory) {
        MapCreation map = new MapCreation(10, 10);

        Scanner scanner = new Scanner(System.in);

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
