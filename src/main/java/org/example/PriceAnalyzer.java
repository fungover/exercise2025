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

        int index = 0;
        for (Price price : prices) {
            OffsetDateTime timeStart = OffsetDateTime.parse(price.time_start);
            OffsetDateTime timeEnd = OffsetDateTime.parse(price.time_end);

            System.out.println("Object nr: " + index);
            System.out.println("Start" + timeStart.format(formatter));
            System.out.println("End " + timeEnd.format(formatter));
            System.out.println("Price (SEK): " + price.SEK_per_kWh);
            System.out.println("Price (EUR): " + price.EUR_per_kWh);
            System.out.println("---------------");

            totalPrice += price.SEK_per_kWh;

            if (price.SEK_per_kWh > priceMax || (price.SEK_per_kWh == priceMax && timeStart.isBefore(OffsetDateTime.parse(priceHourMax, formatter)))) {
                priceMax = price.SEK_per_kWh;
                priceHourMax = timeStart.format(formatter);
            }
            if (price.EUR_per_kWh < priceMin || (price.SEK_per_kWh == priceMin && timeEnd.isBefore(OffsetDateTime.parse(priceHourMin, formatter)))) {
                priceMin = price.EUR_per_kWh;
                priceHourMin = timeStart.format(formatter);
            }
            index++;
        }

        double meanPrice = totalPrice / prices.size();

        System.out.println("Mean Price : " + meanPrice);
        System.out.println("Max Price : " + priceMax + " at " + priceHourMax);
        System.out.println("Min Price : " + priceMin + " at " + priceHourMin);
    }

}
