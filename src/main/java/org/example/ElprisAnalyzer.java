package org.example;

import java.util.Comparator;
import java.util.List;

public class ElprisAnalyzer {

    public static double mean(List<Elpris> priser) {
        return priser.stream()
                .mapToDouble(Elpris::getSEK)
                .average()
                .orElse(0);
    }

    public static Elpris cheapest(List<Elpris> priser) {
        return priser.stream()
                .min(Comparator.comparingDouble(Elpris::getSEK)
                        .thenComparing(Elpris::getTimeStart))
                .orElse(null);
    }

    public static Elpris mostExpensive(List<Elpris> priser) {
        return priser.stream()
                .max(Comparator.comparingDouble(Elpris::getSEK)
                        .thenComparing(Elpris::getTimeStart))
                .orElse(null);
    }

    public static Elpris bestPeriod(List<Elpris> priser, int hours) {
        double minSum = Double.MAX_VALUE;
        int bestStart = 0;


        for (int i = 0; i <= priser.size() - hours; i++) {
            double sum = 0;
            for (int j = 0; j < hours; j++) {
                sum += priser.get(i + j).getSEK();
            }
            if (sum < minSum) {
                minSum = sum;
                bestStart = i;
            }
        }
        return priser.get(bestStart);
    }
    public static double periodAverage(List<Elpris> priser, Elpris start, int hours) {
    int index = priser.indexOf(start);
    return priser.subList(index, index + hours).stream()
            .mapToDouble(Elpris::getSEK)
            .average()
            .orElse(0);
    }
}
