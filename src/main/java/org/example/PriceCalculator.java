package org.example;

//TODO implement identifier for best charge hours in span of 2, 4 or 8 hours (sliding window algorithm)

import java.util.List;

public class PriceCalculator {

    public static double calculateAveragePrice(List<ApiClient.ElectricityPrice> prices) {
        return prices.stream()
                .mapToDouble(ApiClient.ElectricityPrice::SEK_per_kWh)
                .average()
                .orElseThrow(() -> new IllegalArgumentException("Prislistan kan inte vara tom"));
    }


    public static ApiClient.ElectricityPrice findMaxPrice(ApiClient.ElectricityPrice[] prices) {
        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Prisarray kan inte vara null eller tom");
        }

        ApiClient.ElectricityPrice maxPrice = prices[0];
        for (ApiClient.ElectricityPrice price : prices) {
            if (price.SEK_per_kWh() > maxPrice.SEK_per_kWh()) {
                maxPrice = price;
            }
        }
        return maxPrice;
    }

    public static ApiClient.ElectricityPrice findMinPrice(ApiClient.ElectricityPrice[] prices) {
        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Prisarray kan inte vara null eller tom");
        }

        ApiClient.ElectricityPrice minPrice = prices[0];
        for (ApiClient.ElectricityPrice price : prices) {
            if (price.SEK_per_kWh() < minPrice.SEK_per_kWh()) {
                minPrice = price;
            }
        }
        return minPrice;
    }
}

