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
                               2. Full list of the price for today or tomorrow in your zone
                               3. Lowest/highest price info
                               5. Exit
                               """);

            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();

                switch (input) {
                    case 1 -> subAvgPriceMenu(pricesToday, pricesTomorrow);
                    case 2 -> subFullListMenu(pricesToday, pricesTomorrow);
                    case 3 -> subLowHighPriceInfo(pricesToday, pricesTomorrow);
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
        boolean runningSub = true;
        while (runningSub) {

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
                        runningSub = false;
                    }
                    break;
                case "2":
                    if (pricesTomorrow.isEmpty()) {
                        System.out.println("No price data available for tomorrow");
                    } else {

                        System.out.printf(
                          "the average price for tomorrow\nis in your zone is %" +
                            ".3f SEK/kWh\n", PriceUtils.avgPrice(pricesTomorrow));
                        runningSub = false;
                    }
                    break;
                case "3":
                    runningSub = false;
                default:
                    System.out.println("Invalid choice, please try again");

            }
        }
    }

    public static void subFullListMenu(List<ElectricityPrice> pricesToday,
                                       List<ElectricityPrice> pricesTomorrow) {
        boolean runningSub = true;
        while (runningSub) {
            System.out.println("\nSelect today or tomorrow");

            System.out.println("""
                               1. Full price list for today
                               2. Full price list for tomorrow
                               3. Back
                               """);
            String input = System.console()
                                 .readLine();

            switch (input) {
                case "1":
                    if (pricesToday.isEmpty()) {
                        System.out.println("No price data available for today");
                    } else {
                        PriceUtils.priceCheck(pricesToday);
                        runningSub = false;
                    }
                    break;
                case "2":
                    if (pricesTomorrow.isEmpty()) {
                        System.out.println("No price data available for tomorrow");
                    } else {
                        PriceUtils.priceCheck(pricesTomorrow);
                        runningSub = false;
                    }
                    break;
                case "3":
                    runningSub = false;
                default:
                    System.out.println("Invalid choice, please try again");

            }
        }
    }

    public static void subLowHighPriceInfo(List<ElectricityPrice> pricesToday,
                                           List<ElectricityPrice> pricesTomorrow) {
        //TODO 4.Identify and print the hours with the cheapest and most expensive prices.
        boolean runningSub = true;
        while (runningSub) {
            System.out.println("\nSelect today or tomorrow");
            System.out.println("""
                               Do you wish to know what hours are the cheapest or most expensive for:
                               
                               1. Today
                               2. Tomorrow
                               3. Back
                               """);
            String input = System.console()
                                 .readLine();
            switch (input) {
                case "1":
                    PriceUtils.checkMinMaxPrice(pricesToday);
                    break;
                case "2":
                    PriceUtils.checkMinMaxPrice(pricesTomorrow);
                    break;
                case "3":
                    runningSub = false;
                default:
                    System.out.println("Invalid choice, please try again");

            }
        }
    }

    public static void subFindBestChargingTime(List<ElectricityPrice> prices) {
        //TODO 6.Determine the best time to charge an electric car for durations of 2, 4, or 8 hours.
        boolean runningSub = true;
        while (runningSub) {
            System.out.println("\nSelect today or tomorrow");
            System.out.println("""
                               And we will find the best hours to charge your car
                               1. Today
                               2. Tomorrow
                               3. Back
                               """);
            String input = System.console()
                                 .readLine();
            switch (input) {
                //today
                case "1":
                    boolean subMenuRunning = true;
                    while (subMenuRunning) {

                        System.out.println("""
                                           How many hours do u wish to charge?
                                           1. 2 hours
                                           2. 4 hours
                                           3. 8 hours
                                           4. Back
                                           """);
                        String subInput = System.console()
                                                .readLine();
                        switch (subInput) {
                            case "1":
                            case "2":
                            case "3":
                            case "4":
                                subMenuRunning = false;
                                break;
                            default:
                                System.out.println("Invalid choice, please try again");
                        }
                    }
                    break;
                //tomorrow
                case "2":
            }
        }
    }
}
