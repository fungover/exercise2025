package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Price {
    private Properties[] todayPrices;
    private Properties[] tomorrowPrices;

    public Price() {
        FetchPrice fetch = new FetchPrice();
        ObjectMapper mapper = new ObjectMapper();

        //Get electricity prices for today
        try {
            String firstPayload = fetch.getTodayPrices();
            todayPrices = mapper.readValue(firstPayload, Properties[].class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }

        //Get electricity prices for tomorrow if available
        try {
            String secondPayload = fetch.getTomorrowPrices();
            tomorrowPrices = mapper.readValue(secondPayload, Properties[].class);
        } catch (JsonProcessingException e) {
            System.out.println("Prices Today:");
        }
    }

    public void getPrices() {
        if (tomorrowPrices != null)
            System.out.println("Prices Today:");

        for  (var price : todayPrices) {
            System.out.println(
                            "SEK_per_kWh: " + price.getSekPerKWh() + "\n" +
                            "EUR_per_kWh: " + price.getEurPerKWh() + "\n" +
                            "EXR: " + price.getExr() + "\n" +
                            "time_start: " + price.getTimeStart() + "\n" +
                            "time_end: " + price.getTimeEnd() + "\n"
            );
        }

        if (tomorrowPrices != null) {
            System.out.println("Prices Tomorrow:");
            for (var price : tomorrowPrices) {
                System.out.println(
                                "SEK_per_kWh: " + price.getSekPerKWh() + "\n" +
                                "EUR_per_kWh: " + price.getEurPerKWh() + "\n" +
                                "EXR: " + price.getExr() + "\n" +
                                "time_start: " + price.getTimeStart() + "\n" +
                                "time_end: " + price.getTimeEnd() + "\n"
                );
            }
        }
    }
}