package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;

public class Printers {

    public static void printPricesForDay(LocalDate date, String zone, String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            PriceEntry[] prices = mapper.readValue(json, PriceEntry[].class);

            PriceEntry lowestPrice = prices[0];
            for (int i = 0; i < prices.length; i++) {
                if(prices[i].SEK_per_kWh < lowestPrice.SEK_per_kWh) {
                    lowestPrice = prices[i];
                }
            }

            PriceEntry highestPrice = prices[0];
            for (int i = 0; i < prices.length; i++) {
                if(prices[i].SEK_per_kWh > highestPrice.SEK_per_kWh) {
                    highestPrice = prices[i];
                }
            }

            System.out.println("----------------------------------------------------------");
            System.out.println("Prices for " + date + " in " + zone + ":");
            System.out.println("----------------------------------------------------------");


            for (PriceEntry p : prices) {
                System.out.printf("%s → %s: %.3f SEK/kWh%n",
                        p.time_start.substring(11, 16),
                        p.time_end.substring(11, 16),
                        p.SEK_per_kWh);
            }
            System.out.println("----------------------------------------------------------");
            System.out.printf("Cheapest hour: %s → %s, %.3f SEK/kWh%n",
                    lowestPrice.time_start.substring(11, 16),
                    lowestPrice.time_end.substring(11, 16),
                    lowestPrice.SEK_per_kWh);

            System.out.printf("Most expensive hour: %s → %s, %.3f SEK/kWh%n",
                    highestPrice.time_start.substring(11, 16),
                    highestPrice.time_end.substring(11, 16),
                    highestPrice.SEK_per_kWh);

            System.out.printf("Cheapest hour: %s → %s, %.3f EUR/kWh%n",
                    lowestPrice.time_start.substring(11, 16),
                    lowestPrice.time_end.substring(11, 16),
                    lowestPrice.EUR_per_kWh);

            System.out.printf("Most expensive hour: %s → %s, %.3f EUR/kWh%n",
                    highestPrice.time_start.substring(11, 16),
                    highestPrice.time_end.substring(11, 16),
                    highestPrice.EUR_per_kWh);


        } catch (Exception e) {
            System.err.println("Couldn't read prices: " + e.getMessage());
        }
    }

    public static void printMenu() {
        System.out.println("==========================================================");
        System.out.println("          Welcome to Christians CLI");
        System.out.println("==========================================================");
        System.out.println("| 1 | Show electrical prices for a day");
        System.out.println("| 2 | Show average price for a day");
        System.out.println("| 3 | Show best charging hours for a day");
        System.out.println("| 4 | Exit");
        System.out.println("==========================================================");
        System.out.print("> ");
    }


    public static void printZoneAreas() {
        System.out.println("==========================================================");
        System.out.println("   Select Electricity Price Zone (SE1 – SE4)");
        System.out.println("   Default: SE3");
        System.out.println("==========================================================");
        System.out.println("| SE1 | Luleå / Norra Sverige");
        System.out.println("| SE2 | Sundsvall / Norra");
        System.out.println("| SE3 | Stockholm / Södra Mellansverige");
        System.out.println("| SE4 | Malmö / Södra Sverige");
        System.out.println("==========================================================");
        System.out.print("Enter Zone (SE1–SE4, default SE3): ");
    }
    public static void printBestChargingWindows(PriceEntry[] prices) {
        System.out.println("==========================================================");
        System.out.println(" Best charging windows (2 / 4 / 8 hours) ");
        System.out.println("==========================================================");

        SlidingWindowCalculator.findCheapestWindow(prices, 2);
        SlidingWindowCalculator.findCheapestWindow(prices, 4);
        SlidingWindowCalculator.findCheapestWindow(prices, 8);


        System.out.println("==========================================================");
    }

}
