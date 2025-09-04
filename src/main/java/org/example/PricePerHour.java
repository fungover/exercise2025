package org.example;

public record PricePerHour(
        double SEK_per_kWh,
        double EUR_per_kWh,
        String EXR,
        String time_start,
        String time_end

) {
}
