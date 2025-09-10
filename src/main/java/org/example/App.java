package org.example;

import java.sql.SQLOutput;
import java.util.List;

public class App {
    public static void main(String[] args) {
        List<PricePoint> prices = List.of(
                new PricePoint("00:00", 1.0),
                new PricePoint("01:00", 0.5),
                new PricePoint("02:00", 1.5),
                new PricePoint("03:00", 2.0),
                new PricePoint("04:00", 1.0),
                new PricePoint("05:00", 0.5),
                new PricePoint("06:00", 1.5),
                new PricePoint("07:00", 1.5),
                new PricePoint("08:00", 1.0)
        );

        // Medelpris, billigast, dyrast pris kr/kWh
        double avgPrice = PriceUtils.averagePrice(prices);
        PricePoint cheapestPrice = PriceUtils.cheapestPrice(prices);
        PricePoint mostExpensivePrice = PriceUtils.mostExpensivePrice(prices);

        System.out.println("Medelpris: " + String.format("%.2f", avgPrice) + " kr/kWh");
        System.out.println("Billigaste timme: " + cheapestPrice.getHour() + " (" + cheapestPrice.getPrice() + " kr/kWh)");
        System.out.println("Dyraste timme: " + mostExpensivePrice.getHour() + " (" + mostExpensivePrice.getPrice() + " kr/kWh)");

        System.out.println();

        // Slidingwindows 2h/4h/8h
        int best2hStart = PriceUtils.findBestWindowStart(prices, 2);
        System.out.println(PriceUtils.windowSummary(prices, best2hStart, 2));

        int best4hStart = PriceUtils.findBestWindowStart(prices, 4);
        System.out.println(PriceUtils.windowSummary(prices, best4hStart, 4));

        int best8hStart = PriceUtils.findBestWindowStart(prices, 8);
        System.out.println(PriceUtils.windowSummary(prices, best8hStart, 8));
    }
}
