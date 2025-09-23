package org.SpinalGlitter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

// Price entry as returned by the API
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceEntry {
    // Price in SEK per kWh
    @JsonProperty("SEK_per_kWh")
    public double sekPerKWh;
    // Start time of the price period
    @JsonProperty("time_start")
    public ZonedDateTime timeStart;
    // End time of the price period
    @JsonProperty("time_end")
    public ZonedDateTime timeEnd;


}
