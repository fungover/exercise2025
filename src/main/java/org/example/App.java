package org.example;

import org.example.entities.Inventory;
import org.example.entities.Player;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Player user = new Player("x", 10, 10, 10);
        Inventory userInventory = new Inventory();

        System.out.println("Welcome user! Please enter your name:");
        Scanner scanner = new Scanner(System.in);
        String playerName = scanner.nextLine();

        user.setName(playerName);

        System.out.println("Welcome " + playerName + ". Let your adventure begin!");
    }
}
