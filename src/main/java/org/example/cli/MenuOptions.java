package org.example.cli;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.api.ElprisApi;
import org.example.util.ElectricityPrice;
import org.example.util.PriceUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuOptions {
    public static void mainMenu(Scanner scanner, List<ElectricityPrice> pricesToday,
                                List<ElectricityPrice> pricesTomorrow, String zone,
                                java.time.ZoneId seZone) {
        ElprisApi api = new ElprisApi();
        boolean running = true;
        while (running) {
            System.out.println("\nSelected Zone " + zone);
            System.out.println("""
                               1. Average price info
                               2. Full list of the price for today or tomorrow in your zone
                               3. Lowest/highest price info
                               4. Cheapest charging times
                               5. Exit
                               6. Refresh price data
                               """);

            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();

                switch (input) {
                    case 1 -> subAvgPriceMenu(pricesToday, pricesTomorrow);
                    case 2 -> subFullListMenu(pricesToday, pricesTomorrow);
                    case 3 -> subLowHighPriceInfo(pricesToday, pricesTomorrow);
                    case 4 -> subFindBestChargingTime(pricesToday, pricesTomorrow);
                    case 5 -> running = false;
                    case 6 -> {
                        try {
                            String todayFetchJson = api.getRequest(LocalDate.now(seZone),
                              zone);
                            String tomorrowFetchJson = api.getRequest(LocalDate.now(seZone)
                                                                               .plusDays(1),
                              zone);
                            ObjectMapper mapper = new ObjectMapper();

                            pricesToday.clear();
                            pricesToday.addAll(
                              mapper.readValue(todayFetchJson, new TypeReference<>() {
                              }));

                            pricesTomorrow.clear();
                            pricesTomorrow.addAll(
                              mapper.readValue(tomorrowFetchJson, new TypeReference<>() {
                              }));

                            System.out.println("Prices refreshed successfully!");

                        } catch (Exception e) {
                            System.out.println("Failed to refresh prices: " + e.getMessage());
                        }
                    }
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
                        System.out.println("-".repeat(20));
                    }
                    break;
                case "2":
                    if (pricesTomorrow.isEmpty()) {
                        System.out.println("No price data available for tomorrow");
                    } else {

                        System.out.printf(
                          "the average price for tomorrow\nis in your zone is %" +
                            ".3f SEK/kWh\n", PriceUtils.avgPrice(pricesTomorrow));
                        System.out.println("-".repeat(20));

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
            System.out.println("""
                               Select today or tomorrow
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

                    }
                    break;
                case "2":
                    if (pricesTomorrow.isEmpty()) {
                        System.out.println("No price data available for tomorrow");
                    } else {
                        PriceUtils.priceCheck(pricesTomorrow);

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
            System.out.println("""
                               Select today or tomorrow
                               Do you wish to know what hours are the cheapest or most expensive for:
                               1. Today
                               2. Tomorrow
                               3. Back
                               """);
            String input = System.console()
                                 .readLine();
            switch (input) {
                case "1":
                    if (pricesToday.isEmpty()) {
                        System.out.println("No price data available for tomorrow");
                    } else {
                        PriceUtils.checkMinMaxPrice(pricesToday);
                        break;
                    }
                case "2":
                    if (pricesTomorrow.isEmpty()) {
                        System.out.println("No price data available for tomorrow");
                    } else {

                        PriceUtils.checkMinMaxPrice(pricesTomorrow);
                        break;
                    }
                case "3":
                    runningSub = false;
                default:
                    System.out.println("Invalid choice, please try again");

            }
        }
    }

    public static void subFindBestChargingTime(List<ElectricityPrice> pricesToday,
                                               List<ElectricityPrice> pricesTomorrow) {
        //TODO 6.Determine the best time to charge an electric car for durations of 2, 4, or 8 hours.
        boolean runningSub = true;
        int[] chargingWindow = {2, 4, 8};
        while (runningSub) {
            System.out.println("""
                               Select today or tomorrow
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
                    if (pricesToday.isEmpty()) {
                        System.out.println("No price data available for tomorrow");
                    } else {
                        PriceUtils.findBestChargingTime(pricesToday, chargingWindow);
                        break;
                    }
                case "2":
                    if (pricesTomorrow.isEmpty()) {
                        System.out.println("No price data available for tomorrow");
                    } else {
                        PriceUtils.findBestChargingTime(pricesTomorrow, chargingWindow);
                        break;
                    }
                case "3":
                    runningSub = false;
            }
        }
    }
}
