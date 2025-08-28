package org.example.model;

public record PriceHour(
        String time_start,
        String time_end,
        double SEK_per_kWh,
        double EUR_per_kWh,
        double EXR
) {}

