package org.example.game;

import org.example.entities.Player;
import java.util.Scanner;

public class Game {
private Scanner scanner = new Scanner(System.in);
private Player player;

    public void startGame() {
    System.out.println("Welcome to the Dungeon.... I have been waiting for an adventurer..");
    System.out.print("Please, tell me your name ");
    String name = scanner.nextLine();

    player = new Player(name);
    System.out.println("Well Hello, " + player.getName() + ". Step inside and see what happens...");
    System.out.println("Your health is " + player.getHealth());
    }
}
