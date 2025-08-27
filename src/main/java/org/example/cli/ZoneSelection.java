package org.example.cli;

import org.example.utils.CsvEnergyCalculator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class ZoneSelection {

    public String chooseZone(Scanner scanner) {

            while (true) {
                System.out.println("\nPlease select an option:\n");
                System.out.println("1. Energy zone selection.");
                System.out.println("2. Calculate consumption from CSV file.");
                System.out.print("\nEnter your choice: ");

                if (scanner.hasNextInt()) {
                    int mainChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (mainChoice == 1) {
                        return selectZone(scanner);
                    } else if (mainChoice == 2) {
                        calculateCsv(scanner);
                        return null;
                    } else {
                        System.out.println("Invalid choice, please try again:");
                    }
                } else {
                    System.out.println("Not a number, please try again.");
                    scanner.nextLine();
                }
            }

    }

    private String selectZone(Scanner scanner) {
        while (true) {
            System.out.println("\nPlease select a zone:\n");
            System.out.println("1. SE1 (Luleå / North Sweden)");
            System.out.println("2. SE2 (Sundsvall / North middle Sweden)");
            System.out.println("3. SE3 (Stockholm / South middle Sweden)");
            System.out.println("4. SE4 (Malmö / South Sweden)");
            System.out.print("\nEnter the number of your choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 ->{return "SE1";}
                    case 2 ->{return "SE2";}
                    case 3 ->{return "SE3";}
                    case 4 ->{return "SE4";}
                    default -> System.out.println("Invalid choice, please try again.");
                }
            } else {
                System.out.println("Not a number, please try again.");
                scanner.nextLine();
            }
        }
    }

    private void calculateCsv(Scanner scanner) {
        System.out.println("Enter CSV file path:");
        String csvPath = scanner.nextLine();

        System.out.print("Enter electricity price per kWh: ");

        try {
            BigDecimal price = new BigDecimal(scanner.nextLine());
            CsvEnergyCalculator.calculateFromCsv(csvPath, price);
        } catch (IOException e) {
            System.out.println("Could not read CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format, please enter a valid number.");
        }
    }
}