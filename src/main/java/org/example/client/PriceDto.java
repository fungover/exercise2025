package org.example.client;

public record PriceDto (
        String time_start,
        String time_end,
        double SEK_per_kWh
) {}
