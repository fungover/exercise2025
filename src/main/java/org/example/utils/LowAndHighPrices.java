package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.ElectricityPrice;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LowAndHighPrices {

    private static final DateTimeFormatter HHMM = DateTimeFormatter.ofPattern("HH:mm");

    public static void printMinMaxPrices(String json) {

        try {

            ObjectMapper mapper = JsonMapper.get(); // calls our custom ObjectMapper from JsonMapper class.

            List<ElectricityPrice> prices = mapper.readValue(json, new TypeReference<>() {}); // Reads the JSON string and maps it to a list of ElectricityPrice objects.

            if (prices.isEmpty()) {
                System.out.println("No prices available for the selected period.");
                return;
            }


            ElectricityPrice minSEK = prices.get(0), maxSEK = prices.get(0); // Initialize min and max prices in SEK with the first price in the list.
            ElectricityPrice minEUR = prices.get(0), maxEUR = prices.get(0); // Initialize min and max prices in EUR with the first price in the list.

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

            System.out.println("\nBest price (SEK): " + hhmm(minSEK.timeStart()) + " - " + hhmm(minSEK.timeEnd()) + " (" + minSEK.sekPerKwh() + " SEK/kWh)"); // Prints the best price in SEK with the start and end time.
            System.out.println("Worst price (SEK): " + hhmm(maxSEK.timeStart()) + " - " + hhmm(maxSEK.timeEnd()) + " (" + maxSEK.sekPerKwh() + " SEK/kWh)"); // Prints the worst price in SEK with the start and end time.

            System.out.println("Best price (EUR): " + hhmm(minEUR.timeStart()) + " - " + hhmm(minEUR.timeEnd()) + " (" + minEUR.eurPerKwh() + " EUR/kWh)"); // Prints the best price in EUR with the start and end time.
            System.out.println("Worst price (EUR): " + hhmm(maxEUR.timeStart()) + " - " + hhmm(maxEUR.timeEnd()) + " (" + maxEUR.eurPerKwh() + " EUR/kWh)"); // Prints the worst price in EUR with the start and end time.

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private static String hhmm(OffsetDateTime time) { // Converts the OffsetDateTime to a string in HH:mm format.
        return time.toLocalTime().format(HHMM);
    }

}
