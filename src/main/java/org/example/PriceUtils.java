package org.example;

import java.util.List;

public class PriceUtils {
    public static double averagePrice(List<PricePoint> prices) {
        double sum = 0;
        for (PricePoint p : prices) {
            sum += p.getPrice();
        }
        return sum / prices.size();
    }

    public static PricePoint cheapestPrice(List<PricePoint> prices) {
        if (prices == null || prices.isEmpty()) {
            throw new IllegalArgumentException("prices must not be empty");
        }
        PricePoint cheapest = prices.get(0);
        for (PricePoint p : prices) {
            if (p.getPrice() < cheapest.getPrice()) {
                cheapest = p;
            }
        }
        return cheapest;
    }

    public static PricePoint mostExpensivePrice(List<PricePoint> prices) {
        if (prices == null || prices.isEmpty()) {
            throw new IllegalArgumentException("prices must not be empty");
        }
        PricePoint mostExpensive = prices.get(0);
        for (PricePoint p : prices) {
            if (p.getPrice() > mostExpensive.getPrice()) {
                mostExpensive = p;
            }
        }
        return mostExpensive;
    }
}