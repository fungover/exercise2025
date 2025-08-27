package org.example.cli;

import org.example.model.ChargingResult;
import org.example.utils.CalculateMeanPrice;
import org.example.utils.ChargingOptimizer;
import org.example.utils.LowAndHighPrices;

import java.math.RoundingMode;
import java.util.Scanner;

public class Menu {

    private static final int[] CHARGING_OPTIONS = {2, 4, 8}; // predefined charging options in hours

    public void showMenu(String jsonData, Scanner scanner) { //Main menu method.

            while (true) { // Looping until the user has made a valid choice.
                displayMainMenu(); //Showing main menu options
                int choice = getValidChoice(scanner); // Getting a valid choice from the user.

                switch (choice) {
                    case 1 -> {
                        displayMeanPrices(jsonData); // Displaying the mean prices.
                        return;
                    }
                    case 2 -> {
                        LowAndHighPrices.printMinMaxPrices(jsonData); // Displaying the low and highest prices.
                        return;
                    }
                    case 3 -> {
                        showChargingMenu(jsonData, scanner); // Showing the charging option menu.
                        return;
                    }
                    default -> System.out.println("Invalid choice, please try again."); // Handling invalid choices.
                }
            }

    }

    private void displayMainMenu() {
        System.out.println("\nSelect the information you want to see:\n");
        System.out.println("1. Print mean price for the current 24-hour period from current time.");
        System.out.println("2. Print cheapest and most expensive hours in the available period.");
        System.out.println("3. Best time to charge a vehicle from the current time forward.\n");
        System.out.print("Enter your choice: ");
    }

    private void displayMeanPrices(String jsonData) { // Displaying the mean prices in SEK and EUR.
        System.out.println("\nThe mean price " + CalculateMeanPrice.getPeriodInfo(jsonData)); // Getting the period info for the mean price calculation. (24h from current hour or less if not enough data).
        System.out.println("Mean price in SEK: " + CalculateMeanPrice.meanSEK(jsonData)); // Calculating and displaying the mean price in SEK.
        System.out.println("Mean price in EUR: " + CalculateMeanPrice.meanEUR(jsonData)); // Calculating and displaying the mean price in EUR.
    }

    private void showChargingMenu(String jsonData, Scanner scanner) { // Showing the charging options menu.
        while (true) { // Looping until the user has made a valid choice.
            displayChargingOptions(); // Displaying the charging options.
            int choice = getValidChoice(scanner); // Getting a valid choice from the user.

            if (choice >= 1 && choice <= CHARGING_OPTIONS.length) { //Check to see if the choice is between 1 and the length of the charging options array.
                int chargingHours = CHARGING_OPTIONS[choice - 1]; // Getting the charging hours based on the user's choice.
                displayChargingResult(jsonData, chargingHours); // Displaying the charging result.
                return;
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void displayChargingOptions() { // Displaying the charging options to the user.
        System.out.println("Choose your charging duration:\n");
        for (int i = 0; i < CHARGING_OPTIONS.length; i++) { // Looping through the charging options array to display the options.
            System.out.printf("%d. %d hours\n", i + 1, CHARGING_OPTIONS[i]);
        }
        System.out.print("Enter your choice: ");
    }

    private void displayChargingResult(String jsonData, int chargingHours) { // Displaying the charging result based on the user's choice of charging hours.
        ChargingResult result = ChargingOptimizer.findBestChargingTime(jsonData, chargingHours); //Calls the ChargingOptimizer to find the best charging time.

        if (result != null) {
            var sum = result.totalCost(); // sum of hourly prices for the charging period.
            var avg = sum.divide(java.math.BigDecimal.valueOf(result.chargingHours()), 6, RoundingMode.HALF_UP); // Average price per kWh for the charging period.

            System.out.println("Best charging time: \n");
            System.out.printf("Duration: %d hours\n", chargingHours); // Displaying the duration of the charging period.
            System.out.printf("Start time: %s %s\n", result.startTime().toLocalDate(), result.startTime().toLocalDateTime().toLocalTime()); // Displaying the start time of the charging period.
            System.out.printf("End time: %s %s\n", result.endTime().toLocalDate(), result.endTime().toLocalDateTime().toLocalTime()); // Displaying the end time of the charging period.
            System.out.printf("Sum of hourly prices: %.3f SEK\n", result.totalCost()); // Displaying the total cost for the charging period.
            System.out.printf("Average price during charging: %.3f SEK/KwH\n", avg); // Displaying the average price per kWh for the charging period.
        } else {
            System.out.printf("Could not find suitable %d-hour period\n", chargingHours); // If no suitable period was found, inform the user.
        }
    }

    private int getValidChoice(Scanner scanner) { // Method to get a valid integer choice from the user.
        while (true) {
            if (scanner.hasNextInt()) { // Check if the next input is an integer.
                int choice = scanner.nextInt(); // Read the integer input.
                scanner.nextLine(); // Consume the newline character.
                return choice; // Return the valid choice.
            } else {
                System.out.println("Not a number, please try again."); // Inform the user of invalid input.
                scanner.nextLine(); // Consume the invalid input.
            }
        }
    }


}