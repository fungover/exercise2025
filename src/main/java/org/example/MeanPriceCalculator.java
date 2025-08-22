package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class MeanPriceCalculator {

    public static void printMeanPrice(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            PriceEntry[] prices = mapper.readValue(json, PriceEntry[].class);

            double sum = 0;

            for(int i = 0; i < prices.length; i++) {
                sum += prices[i].SEK_per_kWh;
            }

            double mean = sum / prices.length;

            System.out.println("Mean price today: " + mean + " SEK/kWh");

        } catch (Exception e) {
            System.err.println("Could't calculate mean price: " + e.getMessage());
        }
    }
}
