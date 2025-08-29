package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Properties {
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

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public BigDecimal getMinValue(BigDecimal[] priceArray) {
        BigDecimal minValue = priceArray[0];
        for (int i = 1; i < priceArray.length; i++) {
            if(priceArray[i].compareTo(minValue) < 0) {
                minValue = priceArray[i];
            }
        }
        return minValue;
    }
}