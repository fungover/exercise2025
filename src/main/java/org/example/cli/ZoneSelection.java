package org.example.cli;

import java.util.Scanner;

public class ZoneSelection {

    public String chooseZone() {

        Scanner scanner = new Scanner(System.in);
        int choice = 0;


        while (true) {
            System.out.println("Please select a zone:");
            System.out.println("1. SE1 (Luleå / North Sweden)");
            System.out.println("2. SE2 (Sundsvall / North middle Sweden)");
            System.out.println("3. SE3 (Stockholm / South middle Sweden)");
            System.out.println("4. SE4 (Malmö / South Sweden)");
            System.out.print("Enter the number of your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> {return "SE1";}
                    case 2 -> {return "SE2";}
                    case 3 -> {return "SE3";}
                    case 4 -> {return "SE4";}
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }


        }

    }
}
