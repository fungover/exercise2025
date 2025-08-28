package org.example;

import java.util.Comparator;
import java.util.List;

class PriceStats {
    /** Returnerar medelpris (SEK/kWh) för listan av priser */
    static double mean(List<Price> prices) {
        return prices.stream()
                .mapToDouble(Price::getSekPerKwh)
                .average()
                .orElse(Double.NaN);
    }

    /** Hitta billigaste timmen, tidigast om flera har samma pris */
    static Price cheapest(List<Price> prices) {
        return prices.stream()
                .min(Comparator.comparingDouble(Price::getSekPerKwh)
                        .thenComparing(Price::getStart))
                .orElse(null);
    }

    /** Hitta dyraste timmen, tidigast om flera har samma pris */
    static Price mostExpensive(List<Price> prices) {
        return prices.stream()
                .max(Comparator.comparingDouble(Price::getSekPerKwh)
                        .thenComparing(Price::getStart))
                .orElse(null);
    }
}
