package org.example.cli;

import org.example.utils.CalculateMeanPrice;
import org.example.utils.LowAndHighPrices;

import java.util.Scanner;

public class Menu {

    public void showMenu(String jsonToday, String jsonTomorrow) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select time period:");
            System.out.println("1. Today");
            System.out.println("2. Tomorrow");
            System.out.print("Enter your choice: ");
            int dayChoice;

            if (scanner.hasNextInt()) {
                dayChoice = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Not a number, please try again.");
                scanner.nextLine();
                continue;
            }

            String json;
            if (dayChoice == 1) {
                json = jsonToday;
            } else if (dayChoice == 2) {
                json = jsonTomorrow;
            } else {
                System.out.println("Invalid choice, Please try again!");
                continue;
            }

            while (true) {
                System.out.println("1. Print mean price for current 24-hour period.");
                System.out.println("2. Print cheapest and most expensive hours in the current 24-hour period. (not implemented)");
                System.out.println("3. Best time to charge the electric car (not implemented)");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
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
                        System.out.println("Mean price in SEK: " + CalculateMeanPrice.meanSEK(json));
                        System.out.println("Mean price in EUR: " + CalculateMeanPrice.meanEUR(json));
                        return;
                    }
                    case 2 -> {
                        LowAndHighPrices.printMinMaxPrices(json);
                        return;
                    }
                    case 3 -> {
                        System.out.println("Best time to charge the electric car: (not implemented)");
                        // Todo: implement this logic aswell.
                    }
                    default -> {
                        System.out.println("Invalid choice, please try again.");
                    }
                }
            }
        }
    }
}
