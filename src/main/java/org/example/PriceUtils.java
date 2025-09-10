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
}