package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.model.ElectricityPrice;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class LowAndHighPrices {

    private static final DateTimeFormatter HHMM = DateTimeFormatter.ofPattern("HH:mm");

    public static void printMinMaxPrices(String json) {

        try { // the try block, which will jump to the catch block if an excepetion occours.
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Creates an ObjectMapper that read JSON and maps it to Java objects.

            List<ElectricityPrice> prices = mapper.readValue(json, new TypeReference<>() {}); // Reads the JSON string and maps it to a list of ElectricityPrice objects.

            //We assume that the first price in the list is both the minimum and maximum price.
            ElectricityPrice minSEK = prices.get(0), maxSEK = prices.get(0);
            ElectricityPrice minEUR = prices.get(0), maxEUR = prices.get(0);

            // Iterate through the list to find the minimum and maximum prices in both SEK and EUR.
            for (ElectricityPrice price : prices) {

                int cmpMinSEK = price.sekPerKwh().compareTo(minSEK.sekPerKwh());
                if (cmpMinSEK < 0 || (cmpMinSEK == 0 && price.timeStart().isBefore(minSEK.timeStart()))) {
                    minSEK = price;
                }

                int cmpMaxSEK = price.sekPerKwh().compareTo(maxSEK.sekPerKwh());
                if (cmpMaxSEK > 0 || (cmpMaxSEK == 0 && price.timeStart().isBefore(maxSEK.timeStart()))) {
                    maxSEK = price;
                }

                int cmpEURMin = price.eurPerKwh().compareTo(minEUR.eurPerKwh());
                if (cmpEURMin < 0 || (cmpEURMin == 0 && price.timeStart().isBefore(minEUR.timeStart()))) {
                    minEUR = price;
                }

                int cmpEURMax = price.eurPerKwh().compareTo(maxEUR.eurPerKwh());
                if (cmpEURMax > 0 || (cmpEURMax == 0 && price.timeStart().isBefore(maxEUR.timeStart()))) {
                    maxEUR = price;
                }
            }

            System.out.println("Best price (SEK): " + minSEK.timeStart().format(HHMM) + " - " + minSEK.timeEnd().format(HHMM) + " (" + minSEK.sekPerKwh() + " SEK/kWh)");
            System.out.println("Worst price (SEK): " + maxSEK.timeStart().format(HHMM) + " - " + maxSEK.timeEnd().format(HHMM) + " (" + maxSEK.sekPerKwh() + " SEK/kWh)");

            System.out.println("Best price (EUR): " + minEUR.timeStart().format(HHMM) + " - " + minEUR.timeEnd().format(HHMM) + " (" + minEUR.eurPerKwh() + " EUR/kWh)");
            System.out.println("Worst price (EUR): " + maxEUR.timeStart().format(HHMM) + " - " + maxEUR.timeEnd().format(HHMM) + " (" + maxEUR.eurPerKwh() + " EUR/kWh)");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
