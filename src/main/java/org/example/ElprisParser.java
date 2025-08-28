package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Arrays;
import java.util.List;

class ElprisParser {
    private final ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());


    List<Price> parsePrices(String json) throws Exception {
        HourlyDTO[] arr = om.readValue(json, HourlyDTO[].class);
        return Arrays.stream(arr)
                .map(h -> new Price(h.time_start, h.time_end, h.SEK_per_kWh))
                .toList();
    }

    // DTO
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class HourlyDTO {
        public double SEK_per_kWh;
        public java.time.ZonedDateTime time_start;
        public java.time.ZonedDateTime time_end;
    }
}
