package org.SpinalGlitter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceEntry {

    @JsonProperty("SEK_per_kWh")
    public double sekPerKWh;

    @JsonProperty("EUR_per_kWh")
    public double eurPerKWh;

    @JsonProperty("EXR")
    public double exchangeRate;

    @JsonProperty("time_start")
    public ZonedDateTime timeStart;

    @JsonProperty("time_end")
    public ZonedDateTime timeEnd;


}
