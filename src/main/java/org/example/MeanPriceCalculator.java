package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

public class MeanPriceCalculator {

    public static void printMeanPrice(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PriceEntry[] prices = mapper.readValue(json, PriceEntry[].class);

            if(prices == null || prices.length == 0) {
                System.out.println("Price entry is empty");
                return;
            }

            double mean = Arrays.stream(prices)
                    .mapToDouble(p -> p.SEK_per_kWh)
                    .average()
                    .orElse(Double.NaN);

            System.out.printf("Mean price: %.3f SEK/kwh%n", mean);

        } catch (Exception e) {
            System.err.println("Could't calculate mean price: " + e.getMessage());
        }
    }
}