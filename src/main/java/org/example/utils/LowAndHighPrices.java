package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.ElectricityPrice;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LowAndHighPrices {

    private static final DateTimeFormatter HHMM = DateTimeFormatter.ofPattern("HH:mm"); // Formatter to display time in HH:mm format.
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Formatter to display date in yyyy-MM-dd format.

    public static void printMinMaxPrices(String json) { // Method to print the minimum and maximum electricity prices in both SEK and EUR.

        try {

            ObjectMapper mapper = JsonMapper.get(); // calls our custom ObjectMapper from JsonMapper class.

            List<ElectricityPrice> prices = mapper.readValue(json, new TypeReference<>() {}); // Reads the JSON string and maps it to a list of ElectricityPrice objects.

            if (prices.isEmpty()) { // If the list is empty, we print a message and return.
                System.out.println("No prices available for the selected period.");
                return;
            }

            ElectricityPrice first = prices.get(0);
            ElectricityPrice minSEK = first, maxSEK = first;
            ElectricityPrice minEUR = first, maxEUR = first;

            // Iterate through the list prices to find the minimum and maximum prices in both SEK and EUR.
            for (ElectricityPrice price : prices) {

                int cmpMinSEK = price.sekPerKwh().compareTo(minSEK.sekPerKwh());
                if (cmpMinSEK < 0 || (cmpMinSEK == 0 && price.timeStart().isBefore(minSEK.timeStart()))) { // if the current price is less than the minimum price or equal, but starts earlier, we update the minimum price.
                    minSEK = price;
                }

                int cmpMaxSEK = price.sekPerKwh().compareTo(maxSEK.sekPerKwh());
                if (cmpMaxSEK > 0 || (cmpMaxSEK == 0 && price.timeStart().isBefore(maxSEK.timeStart()))) { // if the current price is greater than the maximum price of equal, but starts earlier, we update the maximum price.
                    maxSEK = price;
                }

                int cmpEURMin = price.eurPerKwh().compareTo(minEUR.eurPerKwh());
                if (cmpEURMin < 0 || (cmpEURMin == 0 && price.timeStart().isBefore(minEUR.timeStart()))) { // same but for EUR prices
                    minEUR = price;
                }

                int cmpEURMax = price.eurPerKwh().compareTo(maxEUR.eurPerKwh());
                if (cmpEURMax > 0 || (cmpEURMax == 0 && price.timeStart().isBefore(maxEUR.timeStart()))) { // same but for EUR prices
                    maxEUR = price;
                }
            }

            System.out.println("\nBest price (SEK): " + formatTimeWithDate(minSEK.timeStart()) + " - " + // Printing the best price in SEK with formatted start and end times.
                    formatTimeWithDate(minSEK.timeEnd()) + " (" + minSEK.sekPerKwh() + " SEK/kWh)"); // Using the helper method formatTimeWithDate to format the date and time.
            System.out.println("Worst price (SEK): " + formatTimeWithDate(maxSEK.timeStart()) + " - " + // Printing the worst price in SEK with formatted start and end times.
                    formatTimeWithDate(maxSEK.timeEnd()) + " (" + maxSEK.sekPerKwh() + " SEK/kWh)"); // Using the helper method formatTimeWithDate to format the date and time.

            System.out.println("Best price (EUR): " + formatTimeWithDate(minEUR.timeStart()) + " - " + //same as above, but for EUR prices.
                    formatTimeWithDate(minEUR.timeEnd()) + " (" + minEUR.eurPerKwh() + " EUR/kWh)");
            System.out.println("Worst price (EUR): " + formatTimeWithDate(maxEUR.timeStart()) + " - " +
                    formatTimeWithDate(maxEUR.timeEnd()) + " (" + maxEUR.eurPerKwh() + " EUR/kWh)");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String formatTimeWithDate(OffsetDateTime time) { //Helper method to format the date and time.
        return time.format(DATE) + " " + time.toLocalTime().format(HHMM); // Returns the formatted date and time as a string.
    }

}
