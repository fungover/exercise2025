package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.ElectricityPrice;

import java.util.List;

public class LowAndHighPrices {

    public static void printMinMaxPrices(String json) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            List< ElectricityPrice> prices = mapper.readValue(json, new TypeReference<>() {});

            ElectricityPrice minSEK = prices.get(0), maxSEK = prices.get(0);
            ElectricityPrice minEUR = prices.get(0), maxEUR = prices.get(0);

            for (ElectricityPrice p : prices) {
                if (p.sekPerKwh().compareTo(minSEK.sekPerKwh()) < 0) minSEK = p;
                if (p.sekPerKwh().compareTo(maxSEK.sekPerKwh()) > 0) maxSEK = p;
                if (p.eurPerKwh().compareTo(minEUR.eurPerKwh()) < 0) minEUR = p;
                if (p.eurPerKwh().compareTo(maxEUR.eurPerKwh()) > 0) maxEUR = p;
            }

            System.out.println("Cheapest hour (SEK): " + minSEK.timeStart() + " - " + minSEK.timeEnd() + " (" + minSEK.sekPerKwh() + " SEK/kWh)");
            System.out.println("Most expensive hour (SEK): " + maxSEK.timeStart() + " - " + maxSEK.timeEnd() + " (" + maxSEK.sekPerKwh() + " SEK/kWh)");

            System.out.println("Cheapest hour (EUR): " + minEUR.timeStart() + " - " + minEUR.timeEnd() + " (" + minEUR.eurPerKwh() + " EUR/kWh)");
            System.out.println("Most expensive hour (EUR): " + maxEUR.timeStart() + " - " + maxEUR.timeEnd() + " (" + maxEUR.eurPerKwh() + " EUR/kWh)");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
