package org.example;

import java.util.List;

class PriceStats {
    static double mean(List<Price> prices) {
        return prices.stream()
                .mapToDouble(Price::getSekPerKwh)
                .average()
                .orElse(Double.NaN);
    }
}
