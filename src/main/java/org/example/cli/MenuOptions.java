package org.example.cli;

import org.example.util.ElectricityPrice;
import org.example.util.PriceUtils;

import java.util.List;
import java.util.Scanner;

public class MenuOptions {
    public static void mainMenu(Scanner scanner, List<ElectricityPrice> pricesToday,
                                List<ElectricityPrice> pricesTomorrow, String zone) {
        boolean running = true;
        while (running) {
            System.out.println("\nSelected Zone " + zone);
            System.out.println("""
                               1. Average price info
                               2. Full list of the price for the day in your zone
                               5. Exit
                               """);

            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();

                switch (input) {
                    case 1 -> subAvgPriceMenu(pricesToday, pricesTomorrow);
                    case 2 -> PriceUtils.priceCheck(pricesToday);
                    case 5 -> running = false;
                    default -> System.out.println("Invalid choice, please try " + "again");
                }
            } else {
                System.out.println(
                  "Invalid choice, please input a number between 1 and 4 or 5" + " to exit.");
                scanner.next();
            }
        }
    }

    public static void subAvgPriceMenu(List<ElectricityPrice> pricesToday,
                                       List<ElectricityPrice> pricesTomorrow) {
        System.out.println("\nSelect today or tomorrow");
        System.out.println("""
                           1. Average price for today
                           2. Average price for tomorrow
                           3. Back
                           """);
        String input = System.console()
                             .readLine();

        switch (input) {
            case "1":
                if (pricesToday.isEmpty()) {
                    System.out.println("No price data available for today");
                } else {
                    System.out.printf(
                      "the average price for the day\nin your zone is %" + ".3f SEK/kWh\n",
                      PriceUtils.avgPrice(pricesToday));
                }
                break;
            case "2":
                if (pricesTomorrow.isEmpty()) {
                    System.out.println("No price data available for tomorrow");
                } else {

                    System.out.printf(
                      "the average price for tomorrow\nis in your zone is %" + ".3f SEK/kWh\n",
                      PriceUtils.avgPrice(pricesTomorrow));
                }
                break;
            case "3": {
                break;
            }

        }
    }
}
