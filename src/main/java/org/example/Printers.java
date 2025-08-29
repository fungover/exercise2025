package org.example;

import java.time.LocalDate;
import java.util.Arrays;

public class Printers {
    public static void printMenu() {
        System.out.println("==========================================================");
        System.out.println(" 1 - Show electrical prices for a day");
        System.out.println(" 2 - Show average price for a day");
        System.out.println(" 3 - Show best charging hours");
        System.out.println(" 4 - Exit");
        System.out.println("==========================================================");
        System.out.print("Choose option: ");
    }

    public static void printPricesForDay(LocalDate day, String zone, String json) {
        PriceEntry[] prices = HttpFetch.parseEntries(json);
        System.out.println("Prices for " + day + " in zone " + zone);
        for (PriceEntry p : prices) {
            System.out.printf("%s → %s : %.3f kr/kWh%n", p.time_start, p.time_end, p.SEK_per_kWh);
        }
    }

    public static void printBestChargingWindows(PriceEntry[] prices) {
        findBestChargingPeriod(prices, 2);
        findBestChargingPeriod(prices, 4);
        findBestChargingPeriod(prices, 8);
    }

    private static void findBestChargingPeriod(PriceEntry[] prices, int hours) {
        double bestAvg = Double.MAX_VALUE;
        int bestIndex = 0;

        for (int i = 0; i <= prices.length - hours; i++) {
            double sum = 0;
            for (int j = i; j < i + hours; j++) {
                sum += prices[j].SEK_per_kWh;
            }
            double avg = sum / hours;
            if (avg < bestAvg) {
                bestAvg = avg;
                bestIndex = i;
            }
        }

        PriceEntry start = prices[bestIndex];
        PriceEntry end = prices[bestIndex + hours - 1];

        System.out.printf("Best %dh charging: %s → %s (avg %.3f kr/kWh)%n",
                hours, start.time_start, end.time_end, bestAvg);
    }
}
