package org.example.cli;

import java.util.Scanner;

public class ZoneSelection {

    public String chooseZone() {

        Scanner scanner = new Scanner(System.in);



        while (true) {
            System.out.println("Please select a zone:");
            System.out.println("1. SE1 (Luleå / North Sweden)");
            System.out.println("2. SE2 (Sundsvall / North middle Sweden)");
            System.out.println("3. SE3 (Stockholm / South middle Sweden)");
            System.out.println("4. SE4 (Malmö / South Sweden)");
            System.out.print("\nEnter the number of your choice: ");

            if (scanner.hasNextInt()) { // Check if the next input is an integer, if its not it will skip to the else block.
                int choice = scanner.nextInt(); // Read the integer input
                scanner.nextLine();
                switch (choice) {
                    case 1 -> {return "SE1";}
                    case 2 -> {return "SE2";}
                    case 3 -> {return "SE3";}
                    case 4 -> {return "SE4";}
                    default -> System.out.println("Invalid choice. Please try again."); // Handle invalid choices
                }
            } else {
                System.out.println("\nNot a number, please try again!\n"); // If the input is not an integer, we print this message
                scanner.nextLine(); // Clear the invalid input
            }

        }

    }
}
