package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.ArrayList;
import java.util.List;

public class ElprisParser {
    private ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());

    public List<Price> parsePrices(String json) throws Exception {
        HourlyDTO[] arr = om.readValue(json, HourlyDTO[].class);
        List<Price> result = new ArrayList<>();
        for (HourlyDTO h : arr) {
            Price p = new Price(h.time_start, h.time_end, h.SEK_per_kWh);
            result.add(p);
        }
        return result;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class HourlyDTO {
        public double SEK_per_kWh;
        public java.time.ZonedDateTime time_start;
        public java.time.ZonedDateTime time_end;
    }
}
