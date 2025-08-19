package org.example.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ElectricityPrice {
    public double SEK_per_kWh;
    public String time_start;

    @Override
    public String toString() {
        return String.format("%s - %.3f SEK/kWh", time_start, SEK_per_kWh);
    }
}
