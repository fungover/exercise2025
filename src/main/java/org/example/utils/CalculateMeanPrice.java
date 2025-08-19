package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

// Static class to represent each JSON objects in the array.
public class CalculateMeanPrice {

    public static class PriceEntry {
        public BigDecimal SEK_per_kWh; // The field which we map the SEK_per_kWH value from JSON.
        public BigDecimal EUR_per_kWh; // The field which we map the EUR_per_kWH value from JSON.
    }

    //Method to calculate the mean price ins SEK from the JSON string input.
    public static BigDecimal meanSEK(String json) {
        try {

            ObjectMapper mapper = new ObjectMapper(); // Create an ObjectMapper instance to parse the JSON string.

            // Parse the JSON string into a list of PriceEntry objects.
            List<PriceEntry> prices = mapper.readValue(json, new TypeReference<List<PriceEntry>>() {});

            BigDecimal sum = BigDecimal.ZERO; // Initialize a BigDecimal to hold the sum of the prices.

            for (PriceEntry entry : prices) { // Looping through each PriceEntry in the list with enchanced for loop.
                sum = sum.add(entry.SEK_per_kWh);
            }

            return prices.isEmpty() ? BigDecimal.ZERO //If the list is empty, we return zero.
            : sum.divide(BigDecimal.valueOf(prices.size()), 5, RoundingMode.HALF_UP); // Calculate the mean by dividing the sum by the number of entries, rounding to 5 decimal places.
        } catch (Exception e) {
            System.err.println("Error parsing JSON or calculating mean price: " + e.getMessage()); // Print the error message if there's an exception during parsing or calculation.
            return BigDecimal.ZERO; // Return zero if there's an error in parsing or calculation
        }

    }

    public static BigDecimal meanEUR(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<PriceEntry> prices = mapper.readValue(json, new TypeReference<List<PriceEntry>>() {});
            BigDecimal sum = BigDecimal.ZERO;

        }
    }
}

