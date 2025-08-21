package org.example;

import java.time.LocalDate;

public class Printers {

    public static void printPricesForDay(LocalDate date, String zone, String json) {
        if (json == null) {
            System.out.println("No prices published for " + date + " in " + zone + ".");
            return;
        }
        System.out.println("Prices for " + date + " in " + zone + ":");
        System.out.println(json);
    }

    public static void printMenu() {
        System.out.println("Welcome to Christians CLI for electrical prices in Sweden!");
        System.out.println("1. Show prices for today");
        System.out.println("2. Show prices for tomorrow");
        System.out.println("3. Show prices for yesterday");
        System.out.println("4. Show average price for a day");
        System.out.println("5. Show cheapest and most expensive hour for a day");
        System.out.println("6. Find best charging hours time");
        System.out.println("7. Exit");
    }

    public static void printZoneAreas() {
        System.out.println("Enter Zone (SE1–SE4, default SE3): ");
        System.out.println("SE1: Luleå / Norra Sverige");
        System.out.println("SE2: Sundsvall / Norra ");
        System.out.println("SE3: Stockholm / Södra Mellansverige");
        System.out.println("SE4: Malmö / Södra Sverige");
    }
}
