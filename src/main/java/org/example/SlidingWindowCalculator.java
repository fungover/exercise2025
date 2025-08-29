package org.example;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class SlidingWindowCalculator {

    public static void findCheapestWindow(PriceEntry[] prices, int hours) {
        findCheapestWindow(prices, hours, 1.0);
    }


    public static void findCheapestWindow(PriceEntry[] prices, int hours, double energyKWh) {

        if(hours <= 0) {
            System.out.println("Hours must be greater than zero");
            return;
        }
        if(prices == null || prices.length < hours) {
            System.out.println("Not enough hours");
            return;
        }

        // Counting som of the first window
        double currentSum = 0;
        for (int i = 0; i < hours; i++) {
            currentSum += prices[i].SEK_per_kWh;
        }

        double bestSum = currentSum;
        int bestStart = 0;

        // Push the window
        for (int start = 1; start <= prices.length - hours; start++) {
            currentSum = currentSum - prices[start - 1].SEK_per_kWh
                    + prices[start + hours - 1].SEK_per_kWh;

            if (currentSum < bestSum) {
                bestSum = currentSum;
                bestStart = start;
            }
        }

        int bestEnd = bestStart + hours - 1;

        DateTimeFormatter hm = DateTimeFormatter.ofPattern("HH:mm");
        String startHm;
        String endHm;
        try {
            startHm = OffsetDateTime.parse(prices[bestStart].time_start).toLocalTime().format(hm);
            endHm   = OffsetDateTime.parse(prices[bestEnd].time_end).toLocalTime().format(hm);
        } catch (java.time.format.DateTimeParseException | NullPointerException e) {
            startHm = (prices[bestStart].time_start != null && prices[bestStart].time_start.length() >= 16)
                    ? prices[bestStart].time_start.substring(11, 16) : "??:??";
            endHm = (prices[bestEnd].time_end != null && prices[bestEnd].time_end.length() >= 16)
                    ? prices[bestEnd].time_end.substring(11, 16) : "??:??";
        }
        double avg = bestSum / hours;
        double total = avg * energyKWh;
        System.out.printf("%dh cheapest: %s → %s | Average price: %.3f SEK/kWh | Total for %.2f kWh: %.2f SEK%n",
                hours, startHm, endHm, avg, energyKWh, total);

    }
}


