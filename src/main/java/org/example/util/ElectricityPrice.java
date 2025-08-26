package org.example.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ElectricityPrice {
    public BigDecimal SEK_per_kWh;
    public String time_start;

    @Override
    public String toString() {
        //
        String time = (time_start != null && time_start.length() >= 16) ? time_start.substring(
          11, 16) : "??:??";
        java.math.BigDecimal value = (SEK_per_kWh !=
          null) ? SEK_per_kWh : java.math.BigDecimal.ZERO;

        return String.format("%s - %s SEK/kWh", time,
          value.setScale(3, java.math.RoundingMode.HALF_UP)
               .toPlainString());
        //return String.format("%s - %.3f SEK/kWh", time_start.substring(11, 16), SEK_per_kWh);
    }
}
