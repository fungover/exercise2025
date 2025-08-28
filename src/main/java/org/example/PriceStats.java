package org.example;

import java.util.Comparator;
import java.util.List;

class PriceStats {
    static double mean(List<Price> prices) {
        return prices.stream()
                .mapToDouble(Price::getSekPerKwh)
                .average()
                .orElse(Double.NaN);
    }

    static Price cheapest(List<Price> prices) {
        return prices.stream()
                .min(Comparator.comparingDouble(Price::getSekPerKwh)
                        .thenComparing(Price::getStart))
                .orElse(null);
    }

    static Price mostExpensive(List<Price> prices) {
        return prices.stream()
                .max(Comparator.comparingDouble(Price::getSekPerKwh)
                        .thenComparing(Price::getStart))
                .orElse(null);
    }

    /** Hitta billigaste starttid för en viss laddtid */
    static void bestWindow(List<Price> prices, int hours) {
        if (prices.size() < hours) {
            System.out.println("Inte tillräckligt många timmar för " + hours + "h.");
            return;
        }

        double bestAvg = Double.MAX_VALUE;
        int bestIndex = 0;

        for (int i = 0; i <= prices.size() - hours; i++) {
            double sum = 0;
            for (int j = 0; j < hours; j++) {
                sum += prices.get(i + j).getSekPerKwh();
            }
            double avg = sum / hours;
            if (avg < bestAvg) {
                bestAvg = avg;
                bestIndex = i;
            }
        }

        System.out.printf("Bästa tid för %dh laddning: %s–%s (%.4f SEK/kWh)%n",
                hours,
                prices.get(bestIndex).getStart().toLocalTime(),
                prices.get(bestIndex + hours - 1).getEnd().toLocalTime(),
                bestAvg);
    }
}
