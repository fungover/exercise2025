package org.example;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        boolean cliActive = true;

        while(cliActive) {

            // Shows the CLI options
            System.out.println("Welcome to Christians Java CLI!");
            System.out.println("1. Something about electricity prices");
            System.out.println("2. Something else electricity prices");
            System.out.println("3. Some other CLI option");
            System.out.println("4. Exit");

            // Reading the user option
            String choice = scanner.nextLine().trim();


            // Taking action upon user input
            switch (choice) {
                case "1" -> System.out.println("You've chosen option 1");
                case "4" -> cliActive = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }
}

