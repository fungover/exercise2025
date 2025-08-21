package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.ElectricityPrice;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LowAndHighPrices {

    public static void printMinMaxPrices(String json) {

        try { // the try block, which will jump to the catch block if an excepetion occours.
            ObjectMapper mapper = new ObjectMapper(); // Creates an ObjectMapper that read JSON and maps it to Java objects.
            List<ElectricityPrice> prices = mapper.readValue(json, new TypeReference<>() {}); // Reads the JSON string and maps it to a list of ElectricityPrice objects.

            //We assume that the first price in the list is both the minimum and maximum price.
            ElectricityPrice minSEK = prices.get(0), maxSEK = prices.get(0);
            ElectricityPrice minEUR = prices.get(0), maxEUR = prices.get(0);

            // Iterate through the list to find the minimum and maximum prices in both SEK and EUR.
            for (ElectricityPrice p : prices) {
                if (p.sekPerKwh().compareTo(minSEK.sekPerKwh()) < 0) minSEK = p; // Compare the current price with the minimum price in SEK.
                if (p.sekPerKwh().compareTo(maxSEK.sekPerKwh()) > 0) maxSEK = p; // Compare the current price with the maximum price in SEK.
                if (p.eurPerKwh().compareTo(minEUR.eurPerKwh()) < 0) minEUR = p; // Compare the current price with the minimum price in EUR.
                if (p.eurPerKwh().compareTo(maxEUR.eurPerKwh()) > 0) maxEUR = p; // Compare the current price with the maximum price in EUR.
            }

            // OffsetDateTime.parse() makes the text to a date / time object
            // .format makes date/time object to a string with the format HH:mm
            System.out.println("Best Price (SEK): " +
                    OffsetDateTime.parse(minSEK.timeStart()).format(DateTimeFormatter.ofPattern("HH:mm")) +
                    " - " +
                    OffsetDateTime.parse(minSEK.timeEnd()).format(DateTimeFormatter.ofPattern("HH:mm")) +
                    " (" + minSEK.sekPerKwh() + " SEK/kWh)");

            System.out.println("Worst Price (SEK): " +
                    OffsetDateTime.parse(maxSEK.timeStart()).format(DateTimeFormatter.ofPattern("HH:mm")) +
                    " - " +
                    OffsetDateTime.parse(maxSEK.timeEnd()).format(DateTimeFormatter.ofPattern("HH:mm")) +
                    " (" + maxSEK.sekPerKwh() + " SEK/kWh)");

            System.out.println("Best Price (EUR): " +
                    OffsetDateTime.parse(minEUR.timeStart()).format(DateTimeFormatter.ofPattern("HH:mm")) +
                    " - " +
                    OffsetDateTime.parse(minEUR.timeEnd()).format(DateTimeFormatter.ofPattern("HH:mm")) +
                    " (" + minEUR.eurPerKwh() + " EUR/kWh)");

            System.out.println("Worst Price (EUR): " +
                    OffsetDateTime.parse(maxEUR.timeStart()).format(DateTimeFormatter.ofPattern("HH:mm")) +
                    " - " +
                    OffsetDateTime.parse(maxEUR.timeEnd()).format(DateTimeFormatter.ofPattern("HH:mm")) +
                    " (" + maxEUR.eurPerKwh() + " EUR/kWh)");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
