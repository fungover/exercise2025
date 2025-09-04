package org.SpinalGlitter.exercise2;

import org.SpinalGlitter.exercise2.game.Game;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Welcome to the Dungeon Crawler!?");
        System.out.println("What do you want to name your character?");
        System.out.print("> ");
        String name = inputScanner.nextLine();
        System.out.println("What difficulty do you want to play (easy/medium/hard)? (default = easy)");
        System.out.print("> ");
        String difficulty = inputScanner.nextLine();
        Game game = new Game(name, difficulty);
        System.out.println();
        game.startGame();
        inputScanner.close();
    }
}
