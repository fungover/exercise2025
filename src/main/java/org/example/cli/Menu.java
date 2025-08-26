package org.example.cli;

import org.example.model.ChargingResult;
import org.example.utils.CalculateMeanPrice;
import org.example.utils.ChargingOptimizer;
import org.example.utils.LowAndHighPrices;

import java.math.RoundingMode;
import java.util.Scanner;

public class Menu {

    public void showMenu(String jsonData) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSelect the information you want to see:\n");
            System.out.println("1. Print mean price for the current 24 hour period from current time.");
            System.out.println("2. Print cheapest and most expensive hours in the available period.");
            System.out.println("3. Best time to charge the electric car from the current time forward.");
            System.out.print("\nEnter your choice: ");

            int choice;

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Not a number, please try again.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("\nThe mean price " + CalculateMeanPrice.getPeriodInfo(jsonData));
                    System.out.println("Mean price in SEK: " + CalculateMeanPrice.meanSEK(jsonData));
                    System.out.println("Mean price in EUR: " + CalculateMeanPrice.meanEUR(jsonData));
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
                default -> {
                    System.out.println("Invalid choice, please try again.");
                }
            }
        }
    }

    private void showChargingMenu(String jsonData, Scanner scanner) {
        while (true) {
            System.out.println("Choose your charging duration:\n");
            System.out.println("1. 2 hours");
            System.out.println("2. 4 hours");
            System.out.println("3. 8 hours\n");
            System.out.print("Enter your choice: ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Not a number, please try again.");
                scanner.nextLine();
                continue;
            }

            int chargingHours;
            switch (choice) {
                case 1 -> chargingHours = 2;
                case 2 -> chargingHours = 4;
                case 3 -> chargingHours = 8;
                default -> {
                    System.out.println("Invalid choice, please try again.");
                    continue;
                }
            }
            ChargingResult result = ChargingOptimizer.findBestChargingTime(jsonData, chargingHours);

            if (result != null) {

                var sum = result.totalCost();
                var avg = sum.divide(java.math.BigDecimal.valueOf(result.chargingHours()),
                        6, RoundingMode.HALF_UP);

                System.out.println("Best charging time:\n");
                System.out.printf("Duration: %d hours\n", chargingHours);
                System.out.printf("Start time: %s %s\n",
                        result.startTime().toLocalDate(),
                        result.startTime().toLocalDateTime().toLocalTime());
                System.out.printf("End time: %s %s\n",
                        result.endTime().toLocalDate(),
                        result.endTime().toLocalDateTime().toLocalTime());
                System.out.printf("Total cost: %,3f SEK\n", result.totalCost());
                System.out.printf("Average price per kWh: %.3f SEK\n", avg);
            } else {
                System.out.printf("Could not find suitable %d-hour period\n", chargingHours);
            }
            return;
        }

    }
}
