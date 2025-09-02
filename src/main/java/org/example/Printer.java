package org.example;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Printer {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private static String formatHourRange(PricePerHour p) {
        OffsetDateTime start = OffsetDateTime.parse(p.time_start());
        OffsetDateTime end = OffsetDateTime.parse(p.time_end());
        return start.format(TIME_FORMATTER) + " - " + end.format(TIME_FORMATTER);
    }

    public static void printAllPrices(PricePerHour[] prices) {
        for (PricePerHour p : prices) {
            System.out.println(formatHourRange(p) + " : " + p.SEK_per_kWh() + " kr/kWh");
        }
    }

    public static void printCalculatedPrices(PricePerHour[] prices) {
        System.out.println("\nMedelpris: " + String.format("%.2f", Calculate.calculateMean(prices)) + " kr/kWh");

        PricePerHour expensive = Calculate.findMostExpensiveHour(prices);
        PricePerHour cheap = Calculate.findCheapestHour(prices);

        System.out.println("Dyraste timmen: " + formatHourRange(expensive) + " (" + expensive.SEK_per_kWh() + " kr/kWh)");
        System.out.println("Billigaste timmen: " + formatHourRange(cheap) + " (" + cheap.SEK_per_kWh() + " kr/kWh)");
    }
}



