package org.example.menu;

import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean start = true;

        while (start) {

            System.out.println("\n*************************************************");
            System.out.println("*** Welcome! We check prices for electricity! ***");
            System.out.println("*************************************************");
            System.out.println("\nSelect a zone to continue!");
            System.out.println("(Press [1]-[5] and then press Enter):");
            System.out.println("\n[1] SE1 = Luleå / Northern Sweden");
            System.out.println("[2] SE2 = Sundsvall / Northern Central Sweden");
            System.out.println("[3] SE3 = Stockholm / Southern Central Sweden");
            System.out.println("[4] SE4 = Malmö / Southern Sweden");
            System.out.println("[5] Exit");

            int mainMenuChoice;

            if (scanner.hasNextInt()) {
                mainMenuChoice = scanner.nextInt();
            } else {
                System.out.println("Unavailable choice!");
                System.out.println("Please select a number: [1]-[5] and then press Enter");
                scanner.nextLine();
                continue;
            }

            switch (mainMenuChoice) {
                case 1 -> System.out.println("Menyval 1!");
                case 2 -> System.out.println("Menyval 2!");
                case 3 -> System.out.println("Menyval 3!");
                case 4 -> System.out.println("Menyval 4!");
                case 5 -> start = false;
                default -> System.out.println("Invalid choice.");

            }

        }
            // Closing program dot-"animation" (...)
        System.out.print("Closing program");
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.print(".");
        }
        System.out.println();

    }

}
