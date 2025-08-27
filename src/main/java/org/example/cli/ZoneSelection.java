package org.example.cli;

import org.example.utils.CsvEnergyCalculator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class ZoneSelection {

    public String chooseZone(Scanner scanner) {

            while (true) { // Loop until a valid option is selected
                System.out.println("\nPlease select an option:\n");
                System.out.println("1. Energy zone selection."); // The api route, where the user later selects zone.
                System.out.println("2. Calculate consumption from CSV file."); // The CSV route, where the user can calculate cost from a CSV file.
                System.out.print("\nEnter your choice: ");

                if (scanner.hasNextInt()) {
                    int mainChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (mainChoice == 1) {
                        return selectZone(scanner); // proceeds to zone selection
                    } else if (mainChoice == 2) {
                        calculateCsv(scanner); // procees to CSV calculation.
                        return null;
                    } else {
                        System.out.println("Invalid choice, please try again:"); // Handles invalid choices.
                    }
                } else {
                    System.out.println("Not a number, please try again."); // Handles non-integer inputs.
                    scanner.nextLine(); // Clear the invalid input
                }
            }

    }

    private String selectZone(Scanner scanner) { // Method for selecting the energy zone.
        while (true) { // Loop until a valid zone is selected
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
                    case 1 ->{return "SE1";} // Returns the zone code based on user choice.
                    case 2 ->{return "SE2";}
                    case 3 ->{return "SE3";}
                    case 4 ->{return "SE4";}
                    default -> System.out.println("Invalid choice, please try again.");
                }
            } else {
                System.out.println("Not a number, please try again."); // Handles non-integer inputs.
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private void calculateCsv(Scanner scanner) { // Method for calculating energy consumption from a CSV file.
        System.out.println("Enter CSV file path:"); // Prompting user for the CSV file path.
        String csvPath = scanner.nextLine(); // Reading the file path input.

        System.out.print("Enter electricity price per kWh: "); // Prompting user for the electricity price.

        try {
            BigDecimal price = new BigDecimal(scanner.nextLine()); // Reading and converting the price input to BigDecimal.
            CsvEnergyCalculator.calculateFromCsv(csvPath, price); // Calling the CSV calculation method with the provided file path and price.
        } catch (IOException e) {
            System.out.println("Could not read CSV file: " + e.getMessage()); // Handling file read errors.
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format, please enter a valid number."); // Handling invalid price input.
        }
    }
}