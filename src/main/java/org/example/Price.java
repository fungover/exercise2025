package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {
    @JsonProperty("SEK_per_kWh")
    private BigDecimal sekPerKWh;

    @JsonProperty("EUR_per_kWh")
    private BigDecimal eurPerKWh;

    @JsonProperty("EXR")
    private BigDecimal exr;

    @JsonProperty("time_start")
    private String timeStart;

    @JsonProperty("time_end")
    private String timeEnd;

    public BigDecimal getSekPerKWh() {
        return sekPerKWh;
    }

    public BigDecimal getEurPerKWh() {
        return eurPerKWh;
    }

    public BigDecimal getExr() {
        return exr;
    }

    public String getStartHour() {
            return timeStart.substring(11, 16);
    }

    public String getStartDate() {
        return timeStart.substring(0, 10);
    }

    public String getEndHour() {
        return timeEnd.substring(11, 16);
    }

    public String getEndDate() {
        return timeEnd.substring(0, 10);
    }
}