package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Properties {
    @JsonProperty("SEK_per_kWh")
    private double sekPerKWh;

    @JsonProperty("EUR_per_kWh")
    private double eurPerKWh;

    @JsonProperty("EXR")
    private double exr;

    @JsonProperty("time_start")
    private String timeStart;

    @JsonProperty("time_end")
    private String timeEnd;

    public double getSekPerKWh() {
        return sekPerKWh;
    }

    public double getEurPerKWh() {
        return eurPerKWh;
    }

    public double getExr() {
        return exr;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }
}