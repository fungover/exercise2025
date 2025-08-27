package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.ElectricityPrice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CalculateMeanPrice {

public static BigDecimal meanSEK(String json) { // Method to calculate the mean price in SEK.
    return calculateMean(json, "SEK_per_kWh");
}
public static BigDecimal meanEUR(String json) { // Method to calculate the mean price in EUR.
    return calculateMean(json, "EUR_per_kWh");
}

private static BigDecimal calculateMean(String json, String currency) { // Helper method to calculate the mean price based on the specified currency.

 try {
     ObjectMapper mapper = JsonMapper.get(); // Create an instance of ObjectMapper to parse the JSON string.
     List<ElectricityPrice> prices = mapper.readValue(json, new TypeReference<>() {}); // Reads the JSON string and maps it to a list of ElectricityPrice objects.

     if (prices.isEmpty()) {
         return BigDecimal.ZERO; // If the list is empty, return 0.
     }

     int startIndex = PriceDataUtils.findCurrentOrNextHourIndex(prices); // Find the index of the current hour or the next hour in the list of prices.

     int availableHours = prices.size() - startIndex; // Calculate the number of available hours from the current hour to the end of the list.
     int hoursToInclude = Math.min(24, availableHours); // We want to include up to 24 hours, but not more than available.

     if (hoursToInclude == 0) { // If there are no hours to include, return 0.
         return BigDecimal.ZERO;
     }

     BigDecimal sum = BigDecimal.ZERO; // Initialize sum to 0.
     int count = 0; // Initialize count to 0.

     for (int i = startIndex; i < startIndex + hoursToInclude; i++) { // Loop through the prices from the start index to the number of hours to include.
         ElectricityPrice price = prices.get(i); // Get the current price.
         if ("SEK_per_kWh".equals(currency)) { // Check the currency and add the appropriate price to the sum.
             sum = sum.add(price.sekPerKwh()); // Add the SEK price to the sum.
         } else if ("EUR_per_kWh".equals(currency)) { // Add the EUR price to the sum.
             sum = sum.add(price.eurPerKwh());
         }
         count++; // Increment the count.
     }
     return sum.divide(BigDecimal.valueOf(count), 5, RoundingMode.HALF_UP); // Calculate the mean by dividing the sum by the count, rounding to 5 decimal places.
 } catch (Exception e) {
     return BigDecimal.ZERO; // In case of any exception, return 0.
 }

}

public static String getPeriodInfo(String json) {  // Method to get information about the period used for mean price calculation.
    try {
        ObjectMapper mapper = JsonMapper.get(); // Create an instance of ObjectMapper to parse the JSON string.
        List<ElectricityPrice> prices = mapper.readValue(json, new TypeReference<>() {}); // Reads the JSON string and maps it to a list of ElectricityPrice objects.

        int startIndex = PriceDataUtils.findCurrentOrNextHourIndex(prices); // Find the index of the current hour or the next hour in the list of prices.
        int hoursToInclude = Math.min(24, prices.size() - startIndex); // Calculate the number of hours to include, up to 24 hours or the available hours.

        if (hoursToInclude <= 0) return "No data available"; // If there are no hours to include, return a message indicating no data is available.

        return String.format("from %s to %s (%d hours)\n", // Return a formatted string with the start and end times and the number of hours included.
                prices.get(startIndex).timeStart().toLocalDateTime().toLocalTime(),
                prices.get(startIndex + hoursToInclude - 1).timeEnd().toLocalDateTime().toLocalTime(),
                hoursToInclude);
    } catch (Exception e) {
        return "Error retrieving period info"; // In case of any exception, return an error message.
    }
}
}

