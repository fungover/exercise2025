package org.example.game;

import org.example.entities.Player;
import org.example.map.Room;
import org.example.service.Movement;

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

        Player player = new Player(name);
        Room room = new Room(player);

        while (true) {
            Movement movement = new Movement();
            room.printRoom();

            System.out.print("Choose your direction: ");
            String direction = scan.nextLine();
            if (direction.equalsIgnoreCase("Q")) {
                System.out.println("Exiting...");
                break;
            }

            System.out.println();
            movement.moveInput(room, player, direction);
            movement.renderPlayerPos(room, player.getX(),  player.getY());
        }
    }
}
