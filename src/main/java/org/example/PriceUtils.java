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

    public static int findBestWindowStart(List<PricePoint> prices, int windowSize) {
        if (prices == null || prices.size() < windowSize) {
            throw new IllegalArgumentException("not enough data for window of size " + windowSize);
        }

        double bestAvg = Double.MAX_VALUE;
        int bestIndex = 0;

        for (int i = 0; i <= prices.size() - windowSize; i++) {
            double sum = 0;
            for (int j = 0; j < windowSize; j++) {
                sum += prices.get(i + j).getPrice();
            }
            double avg = sum / windowSize;

            if (avg < bestAvg) {
                bestAvg = avg;
                bestIndex = i;
            }
        }
        return bestIndex;
    }
}