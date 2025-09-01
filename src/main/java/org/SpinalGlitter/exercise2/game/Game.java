package org.SpinalGlitter.exercise2.game;

import org.SpinalGlitter.exercise2.entities.Player;

import java.util.Scanner;

public final class Game {

    static void main() {

        Player player = new Player("Hero");

        System.out.println("Dungeon Crawler Game Started!");
        System.out.println("You are at " + player.getPosition() + " with health " + player.getCurrentHealth() + "HP.");
        System.out.println("Kommandon: help, quit");


        Scanner scanner = new Scanner(System.in);


        do {
            System.out.println("You are at " + player.getPosition() + " with health " + player.getCurrentHealth() + "HP.");
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "help": System.out.println("Available commands: help, quit, north, east, west, south");  break;
                case "quit": System.out.println("Exiting the game. Goodbye!"); return;
                case "north": player.move(0, 1); System.out.println("You moved north."); break;
                case "east": player.move(1, 0); System.out.println("You moved east."); break;
                case "south": player.move(0, -1); System.out.println("You moved south.");  break;
                case "west": player.move(-1, 0); System.out.println("You moved west."); break;
                default: System.out.println("Unknown command. Type 'help' for a list of commands."); break;
            }
        } while (player.isAlive());
    }
}
