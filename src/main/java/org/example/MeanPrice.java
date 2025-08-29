package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

class MeanPrice {
    private final Prices prices = new Prices();

    public void getMeanPrice() {
        Properties[] todayPrices = prices.getTodayPrices();

        BigDecimal totalSek = BigDecimal.ZERO;
        BigDecimal totalEur = BigDecimal.ZERO;
        int count = todayPrices.length;

        for (var price : todayPrices) {
            totalSek = totalSek.add(price.getSekPerKWh());
            totalEur = totalEur.add(price.getEurPerKWh());
        }

        BigDecimal meanPriceSek = totalSek.divide(BigDecimal.valueOf(count),
                RoundingMode.HALF_UP);
        BigDecimal meanPriceEur = totalEur.divide(BigDecimal.valueOf(count),
                RoundingMode.HALF_UP);

        System.out.println("The mean electricity price today:");
        System.out.println("SEK: " + meanPriceSek);
        System.out.println("EUR: " + meanPriceEur);
    }
}
