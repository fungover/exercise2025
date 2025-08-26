package org.example;

//TODO implement identifier for best charge hours in span of 2, 4 or 8 hours (sliding window algorithm)

import java.util.Comparator;
import java.util.List;

public class PriceCalculator {

    public static double calculateAveragePrice(List<ApiClient.ElectricityPrice> prices) {
        return prices.stream()
                .mapToDouble(ApiClient.ElectricityPrice::SEK_per_kWh)
                .average()
                .orElseThrow(() -> new IllegalArgumentException("Prislistan kan inte vara tom"));
    }


    public static ApiClient.ElectricityPrice findMaxPrice(List<ApiClient.ElectricityPrice> prices) {
        return prices.stream()
                .max(Comparator.comparingDouble(ApiClient.ElectricityPrice::SEK_per_kWh))
                .orElseThrow(() -> new IllegalArgumentException("Prislistan kan inte vara tom"));
    }

    public static ApiClient.ElectricityPrice findMinPrice(List<ApiClient.ElectricityPrice> prices) {
        return prices.stream()
                .min(Comparator.comparingDouble(ApiClient.ElectricityPrice::SEK_per_kWh))
                .orElseThrow(() -> new IllegalArgumentException("Prislistan kan inte vara tom"));
    }

}

