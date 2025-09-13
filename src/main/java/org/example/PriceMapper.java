package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;

public class PriceMapper {
    private final String zone;
    private Price[] todayPrices;
    private Price[] tomorrowPrices;
    private final ObjectMapper mapper = new ObjectMapper();

    public PriceMapper(String zone) {
        this.zone = zone;
    }

    public Price[] getAllPrices() {
        this.todayPrices = getTodayPrices();
        this.tomorrowPrices = getTomorrowPrices();
        if (tomorrowPrices != null) {
            return ArrayUtils.addAll(todayPrices, tomorrowPrices);
        } else {
            return todayPrices;
        }
    }

    public Price[] getTodayPrices() {
        try {
            FetchPrice fetch = new FetchPrice(zone);
            String firstPayload = fetch.fetchTodayPrices();
            todayPrices = mapper.readValue(firstPayload, Price[].class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return todayPrices;
    }

    public Price[] getTomorrowPrices() {
        try {
            FetchPrice fetch = new FetchPrice(zone);
            String secondPayload = fetch.fetchTomorrowPrices();
            tomorrowPrices = mapper.readValue(secondPayload, Price[].class);
        } catch (JsonProcessingException _) {
        }
        return tomorrowPrices;
    }
}