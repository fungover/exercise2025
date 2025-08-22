package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.model.ElectricityPrice;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LowAndHighPrices {

    private static final DateTimeFormatter HHMM = DateTimeFormatter.ofPattern("HH:mm");

    public static void printMinMaxPrices(String json) {

        try {
            ObjectMapper mapper = new ObjectMapper()  // Objectmapper is used to convert JSON strings to Java objects and vice versa.
                    .registerModule(new JavaTimeModule()) // Makes Jackson aware of how to read and write Java 8 date/time types.
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //  When printing dates Java -> JSON, we want to use ISO-8601 format instead of timestamps.
                    .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE); // Makes sure we dont adjust times and keeps the original time zone information from the JSON.

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

            System.out.println("Best price (SEK): " + hhmm(minSEK.timeStart()) + " - " + hhmm(minSEK.timeEnd()) + " (" + minSEK.sekPerKwh() + " SEK/kWh)");
            System.out.println("Worst price (SEK): " + hhmm(maxSEK.timeStart()) + " - " + hhmm(maxSEK.timeEnd()) + " (" + maxSEK.sekPerKwh() + " SEK/kWh)");

            System.out.println("Best price (EUR): " + hhmm(minEUR.timeStart()) + " - " + hhmm(minEUR.timeEnd()) + " (" + minEUR.eurPerKwh() + " EUR/kWh)");
            System.out.println("Worst price (EUR): " + hhmm(maxEUR.timeStart()) + " - " + hhmm(maxEUR.timeEnd()) + " (" + maxEUR.eurPerKwh() + " EUR/kWh)");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private static String hhmm(OffsetDateTime time) {
        return time.toLocalTime().format(HHMM);
    }

}
