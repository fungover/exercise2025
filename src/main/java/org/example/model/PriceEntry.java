package org.example.model;

import java.time.OffsetDateTime;

public record PriceEntry(
        double SEK_per_kWh,
        double EUR_per_kWh,
        double EXR,
        OffsetDateTime time_start,
        OffsetDateTime time_end) {
}
