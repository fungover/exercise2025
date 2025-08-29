package org.example;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PriceAnalyzer {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm");

    public static void analyze(List<Price> prices) {
        double totalPrice = 0;
        double priceMax = Double.MIN_VALUE;
        double priceMin = Double.MAX_VALUE;
        String priceHourMax = null;
        String priceHourMin = null;
        boolean debug = false;

        int index = 0;
        for (Price price : prices) {
            OffsetDateTime timeStart = OffsetDateTime.parse(price.time_start);
            OffsetDateTime timeEnd = OffsetDateTime.parse(price.time_end);

            if (debug) {
                System.out.println("Object nr: " + index);
                System.out.println("Start" + timeStart.format(formatter));
                System.out.println("End " + timeEnd.format(formatter));
                System.out.println("Price (SEK): " + price.SEK_per_kWh);
                System.out.println("Price (EUR): " + price.EUR_per_kWh);
                System.out.println("---------------");
            }

            totalPrice += price.SEK_per_kWh;

            //Todo Max price
            if (price.SEK_per_kWh > priceMax) {
                priceMax = price.SEK_per_kWh;
                priceHourMax = timeStart.format(formatter);
            }
            //Todo Min price
            if (price.EUR_per_kWh < priceMin) {
                priceMin = price.EUR_per_kWh;
                priceHourMin = timeStart.format(formatter);
            }
            index++;
        }

        double meanPrice = totalPrice / prices.size();

        System.out.println("Mean Price : " + meanPrice + " SEK");
        System.out.println("Max Price : " + priceMax + " at " + priceHourMax);
        System.out.println("Min Price : " + priceMin + " at " + priceHourMin);
    }
        //Todo Sliding Window
    public static void findBestChargingWindow(List<Price> prices,int durationHours) {
        if (prices.size() < durationHours) {
            System.out.println("Not enough time for " + durationHours + " hours");
        }
        double bestSum = Double.POSITIVE_INFINITY;
        OffsetDateTime bestStart = null;
        OffsetDateTime bestEnd = null;
            //Todo Outer loop finds the best window of time
        for (int i = 0; i <= prices.size() - durationHours; i++) {
            double sum = 0;
            //Todo Inner loop calculate total price
            for (int j = 0; j < durationHours; j++) {
                sum += prices.get(i + j).SEK_per_kWh;
            }
            if (sum < bestSum) {
                bestSum = sum;
                bestStart = OffsetDateTime.parse(prices.get(i).time_start);
                bestEnd = OffsetDateTime.parse(prices.get(i+ durationHours - 1).time_end);
            }
        }

        if (bestStart != null) {
            System.out.printf("Best %d hours charging window: %s --> %s (total %.0f SEK)%n",durationHours,bestStart.format(formatter),bestEnd.format(formatter),bestSum);
        }
    }
}
