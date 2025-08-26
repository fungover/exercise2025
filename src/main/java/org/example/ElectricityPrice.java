package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ElectricityPrice(
        @JsonProperty("SEK_per_kWh") double SEK_per_kWh,
        @JsonProperty("EUR_per_kWh") double EUR_per_kWh,
        @JsonProperty("EXR") double EXR,
        @JsonProperty("time_start") String time_start,
        @JsonProperty("time_end") String time_end
) {
}
