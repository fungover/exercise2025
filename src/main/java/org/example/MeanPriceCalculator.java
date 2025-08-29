package org.example;

import java.util.Arrays;

public class MeanPriceCalculator {
    public static void printMeanPrice(String json) {
        PriceEntry[] prices = new HttpFetch().parseEntries(json);
        double mean = Arrays.stream(prices)
                .mapToDouble(p -> p.SEK_per_kWh)
                .average()
                .orElse(0.0);
        System.out.printf("Average price: %.3f kr/kWh%n", mean);
    }
}
