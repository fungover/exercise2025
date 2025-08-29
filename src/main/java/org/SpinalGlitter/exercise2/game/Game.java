package org.SpinalGlitter.exercise2.game;

import java.util.Scanner;

public final class Game {
    static void main() {
        System.out.println("Dungeon Crawler Game Started!");
        System.out.println("Kommandon: help, quit");



        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine().trim().toLowerCase();

                switch (input) {
                    case "help":
                        System.out.println("Available commands: help, quit");
                        break;
                    case "quit":
                        System.out.println("Exiting the game. Goodbye!");
                        return;
                    default:
                        System.out.println("Unknown command. Type 'help' for a list of commands.");
                        break;
                }
            }
        }
    }
}
