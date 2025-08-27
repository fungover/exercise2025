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
        double minSum = Double.MAX_VALUE;
        int bestStart = 0;


        for (int i = 0; i <= prices.size() - hours; i++) {
            double sum = 0;
            for (int j = 0; j < hours; j++) {
                sum += prices.get(i + j).getSEK();
            }
            if (sum < minSum) {
                minSum = sum;
                bestStart = i;
            }
        }
        return prices.get(bestStart);
    }
    public static double periodAverage(List<Elpris> prices, Elpris start, int hours) {
    int index = prices.indexOf(start);
    return prices.subList(index, index + hours).stream()
            .mapToDouble(Elpris::getSEK)
            .average()
            .orElse(0);
    }
}
