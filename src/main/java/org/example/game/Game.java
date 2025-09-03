package org.example.game;

import org.example.entities.Player;
import org.example.map.Room;

import java.util.Scanner;

public class Game {
    // All game logic is executed here.
    public static void run() {
        Scanner input = new Scanner(System.in);
        System.out.println("--------------------------------");
        System.out.println("\uD83E\uDDD9Welcome to my Dungeon Crawler!");
        System.out.print("State your name, o' brave challenger: ");
        String name = input.nextLine();
        System.out.println("Welcome, WELCOME " + name + "! Your adventure begins...");
        System.out.println("--------------------------------");

        Room room = new Room();
        Player player = new Player(name);
        room.printRoom();
    }
}
