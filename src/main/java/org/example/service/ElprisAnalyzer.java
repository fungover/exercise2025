/**
 * Provides static analysis methods for electricity price data:
 * -mean: Calculate the average SEK price.
 * -cheapest: Finds the cheapest hour.
 * -mostExpensive: Finds the most expensive hour.
 * -bestPeriod: Sliding window search for the cheapest block of hours.
 * -periodAverage: Calculate average price for a given period length.
 */

package org.example.service;

import org.example.model.Elpris;

import java.util.Comparator;
import java.util.List;

public class ElprisAnalyzer {

    public static double mean(List<Elpris> prices) {
        return prices.stream()
                .mapToDouble(Elpris::getSEK)
                .average()
                .orElse(0);
    }

    public static Elpris cheapest(List<Elpris> prices) {
        return prices.stream()
                .min(Comparator.comparingDouble(Elpris::getSEK)
                        .thenComparing(Elpris::getTimeStart))
                .orElse(null);
    }

    public static Elpris mostExpensive(List<Elpris> prices) {
        return prices.stream()
                .max(Comparator.comparingDouble(Elpris::getSEK)
                        .thenComparing(Elpris::getTimeStart))
                .orElse(null);
    }

    public static Elpris bestPeriod(List<Elpris> prices, int hours) {
        if (hours <= 0 || prices == null || prices.size() < hours) {
            return null;
        }
        double windowSum = 0d;
        for (int i = 0; i < hours; i++) {
            windowSum += prices.get(i).getSEK();
    }
            double minSum = windowSum;
            int bestStart = 0;
            for (int i = hours; i < prices.size(); i++) {
                windowSum += prices.get(i).getSEK() - prices.get(i - hours).getSEK();
                if (windowSum < minSum) {
                    minSum = windowSum;
                    bestStart = i - hours + 1;
                }
            }

        return prices.get(bestStart);
    }
    public static double periodAverage(List<Elpris> prices, Elpris start, int hours) {
        if (start == null || prices == null || hours <= 0)
            return 0;
    int index = prices.indexOf(start);
        if (index < 0 || index + hours > prices.size())
            return 0;
    return prices.subList(index, index + hours).stream()
            .mapToDouble(Elpris::getSEK)
            .average()
            .orElse(0);
    }
}
