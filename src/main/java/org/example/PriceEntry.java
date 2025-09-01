package org.example;

import java.time.OffsetDateTime;

public record PriceEntry(OffsetDateTime startTime, double pricePerKWh) {
}
