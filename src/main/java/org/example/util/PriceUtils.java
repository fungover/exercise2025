package org.example.util;

import java.util.List;

public class PriceUtils {

    public static void priceCheck(List<ElectricityPrice> prices) {
        for (ElectricityPrice price : prices) {
            System.out.printf("%s -> %.3f SEK/kWh%n", price.time_start.substring(11, 16),
              price.SEK_per_kWh);
        }
    }

    public static double avgPrice(List<ElectricityPrice> prices) {
        double sum = 0.0;
        for (ElectricityPrice price : prices) {
            sum += price.SEK_per_kWh;
        }
        return prices.isEmpty() ? 0.0 : sum / prices.size();
    }
}
