package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;

public class Prices {
    private Properties[] todayPrices;
    private Properties[] tomorrowPrices;
    private final FetchPrice fetch = new FetchPrice();
    private final ObjectMapper mapper = new ObjectMapper();

    public Properties[] getTodayPrices() {
        try {
            String firstPayload = fetch.getTodayPrices();
            todayPrices = mapper.readValue(firstPayload, Properties[].class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return todayPrices;
    }

    public Properties[] getTomorrowPrices() {
        try {
            String secondPayload = fetch.getTomorrowPrices();
            tomorrowPrices = mapper.readValue(secondPayload, Properties[].class);
        } catch (JsonProcessingException _) {
        }
        return tomorrowPrices;
    }

    public Properties[] getPrices() {
        this.todayPrices = getTodayPrices();
        this.tomorrowPrices = getTomorrowPrices();

        if (tomorrowPrices != null) {
            return ArrayUtils.addAll(todayPrices, tomorrowPrices);
        } else {
            return todayPrices;
        }
    }
}