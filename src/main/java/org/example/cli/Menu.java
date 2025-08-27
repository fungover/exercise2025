package org.example.cli;

import org.example.model.ChargingResult;
import org.example.utils.CalculateMeanPrice;
import org.example.utils.ChargingOptimizer;
import org.example.utils.LowAndHighPrices;

import java.math.RoundingMode;
import java.util.Scanner;

public class Menu {

    private static final int[] CHARGING_OPTIONS = {2, 4, 8}; // predefined charging options in hours

    public void showMenu(String jsonData) {

        try (Scanner scanner = new Scanner(System.in)) {

            while (true) {
                displayMainMenu();
                int choice = getValidChoice(scanner);

                switch (choice) {
                    case 1 -> {
                        displayMeanPrices(jsonData);
                        return;
                    }
                    case 2 -> {
                        LowAndHighPrices.printMinMaxPrices(jsonData);
                        return;
                    }
                    case 3 -> {
                        showChargingMenu(jsonData, scanner);
                        return;
                    }
                    default -> System.out.println("Invalid choice, please try again.");
                }
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\nSelect the information you want to see:\n");
        System.out.println("1. Print mean price for the current 24-hour period from current time.");
        System.out.println("2. Print cheapest and most expensive hours in the available period.");
        System.out.println("3. Best time to charge a vehicle from the current time forward.");
        System.out.print("Enter your choice: ");
    }

    private void displayMeanPrices(String jsonData) {
        System.out.println("\nThe mean price " + CalculateMeanPrice.getPeriodInfo(jsonData));
        System.out.println("Mean price in SEK: " + CalculateMeanPrice.meanSEK(jsonData));
        System.out.println("Mean price in EUR: " + CalculateMeanPrice.meanEUR(jsonData));
    }

    private void showChargingMenu(String jsonData, Scanner scanner) {
        while (true) {
            displayChargingOptions();
            int choice = getValidChoice(scanner);

            if (choice >= 1 && choice <= CHARGING_OPTIONS.length) {
                int chargingHours = CHARGING_OPTIONS[choice - 1];
                displayChargingResult(jsonData, chargingHours);
                return;
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void displayChargingOptions() {
        System.out.println("Choose your charging duration:\n");
        for (int i = 0; i < CHARGING_OPTIONS.length; i++) {
            System.out.printf("%d. %d hours\n", i + 1, CHARGING_OPTIONS[i]);
        }
        System.out.print("Enter your choice: ");
    }

    private void displayChargingResult(String jsonData, int chargingHours) {
        ChargingResult result = ChargingOptimizer.findBestChargingTime(jsonData, chargingHours);

        if (result != null) {
            var sum = result.totalCost();
            var avg = sum.divide(java.math.BigDecimal.valueOf(result.chargingHours()), 6, RoundingMode.HALF_UP);

            System.out.println("Best charging time: \n");
            System.out.printf("Duration: %d hours\n", chargingHours);
            System.out.printf("Start time: %s %s\n", result.startTime().toLocalDate(), result.startTime().toLocalDateTime().toLocalTime());
            System.out.printf("End time: %s %s\n", result.endTime().toLocalDate(), result.endTime().toLocalDateTime().toLocalTime());
            System.out.printf("Total cost: %,.3f SEK\n", result.totalCost());
            System.out.printf("Average price per kWh: %.3f SEK\n", avg);
        } else {
            System.out.printf("Could not find suitable %d-hour period\n", chargingHours);
        }
    }

    private int getValidChoice(Scanner scanner) {
        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } else {
                System.out.println("Not a number, please try again.");
                scanner.nextLine();
            }
        }
    }


}