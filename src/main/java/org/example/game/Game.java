package org.example.game;

import java.util.Scanner;

public class Game {
private Scanner scanner = new Scanner(System.in);

    public void startGame() {
    System.out.println("Welcome to the Dungeon.... I have been waiting for an adventurer..");
    System.out.print("Please, tell me your name ");
        String name = scanner.nextLine();
    System.out.print("Well Hello, " + name + ". Step inside and see what happens...");
}
}
