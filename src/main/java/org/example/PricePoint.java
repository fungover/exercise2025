package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//Ignorerar okända fält som ex EUR_PER_kWh
@JsonIgnoreProperties(ignoreUnknown = true)
public record PricePoint(
        @JsonProperty("time_start") String time,
        @JsonProperty("SEK_per_kWh") double price
) {}
