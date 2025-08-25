package org.example.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ElectricityPrice {
    public BigDecimal SEK_per_kWh;
    public String time_start;

    @Override
    public String toString() {
        return String.format("%s - %.3f SEK/kWh", time_start.substring(11, 16), SEK_per_kWh);
    }
}
