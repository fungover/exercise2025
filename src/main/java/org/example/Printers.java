package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

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

            for (PriceEntry p : prices) {
                System.out.printf("%s → %s: %.3f SEK/kWh%n",
                        p.time_start.substring(11, 16),
                        p.time_end.substring(11, 16),
                        p.SEK_per_kWh);
            }
            System.out.println("----------------------------------------------------------");

            System.out.println("Cheapest hour: " + lowestPrice.time_start.substring(11, 16) + " " + lowestPrice.time_end.substring(11, 16));
            System.out.println("Most expensive hour: " + lowestPrice.time_start.substring(11, 16) + " " + lowestPrice.time_end.substring(11, 16));

        } catch (Exception e) {
            System.err.println("Couldn't read prices: " + e.getMessage());
        }
    }

    public static void printMenu() {
        System.out.println("----------------------------------------------------------");
        System.out.println("Welcome to Christians CLI for electrical prices in Sweden!");
        System.out.println("1. Show prices for today");
        System.out.println("2. Show prices for tomorrow");
        System.out.println("3. Show prices for yesterday");
        System.out.println("4. Show todays average prices");
        System.out.println("5. Find best charging hours time");
        System.out.println("6. Exit");
    }

    public static void printZoneAreas() {
        System.out.println("Enter Zone (SE1–SE4, default SE3): ");
        System.out.println("SE1: Luleå / Norra Sverige");
        System.out.println("SE2: Sundsvall / Norra ");
        System.out.println("SE3: Stockholm / Södra Mellansverige");
        System.out.println("SE4: Malmö / Södra Sverige");
    }
}
