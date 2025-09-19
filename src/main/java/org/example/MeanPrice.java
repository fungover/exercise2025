package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

class MeanPrice {
    private final PriceMapper prices;

    public MeanPrice(PriceMapper prices) {
        this.prices = prices;
    }

    private MeanPrices calculateMeanPrice() {
        BigDecimal totalSek = BigDecimal.ZERO;
        BigDecimal totalEur = BigDecimal.ZERO;
        Price[] todayPrices = prices.getTodayPrices();
        int count = todayPrices.length;
        if (count == 0)
            return new MeanPrices(BigDecimal.ZERO, BigDecimal.ZERO);

        for (var price : todayPrices) {
            if (price.getSekPerKWh() != null) {
                totalSek = totalSek.add(price.getSekPerKWh());
            }
            if (price.getEurPerKWh() != null) {
                totalEur = totalEur.add(price.getEurPerKWh());
            }
        }

        BigDecimal meanPriceSek = totalSek.divide(BigDecimal.valueOf(count),
                RoundingMode.HALF_UP);
        BigDecimal meanPriceEur = totalEur.divide(BigDecimal.valueOf(count),
                RoundingMode.HALF_UP);

        return new MeanPrices(meanPriceSek, meanPriceEur);
    }

    public void printMeanPrice() {
        MeanPrices meanPrice = calculateMeanPrice();
        System.out.println("The mean electricity price today:");
        System.out.println("SEK: " + meanPrice.sekPerKWh);
        System.out.println("EUR: " + meanPrice.eurPerKWh);
    }

    private record MeanPrices(BigDecimal sekPerKWh, BigDecimal eurPerKWh) {}
}
