package org.example;

import java.math.BigDecimal;

public record PriceEntry(
        BigDecimal SEK_per_kWh,
        BigDecimal EUR_per_kWh,
        BigDecimal EXR,
        String time_start,
        String time_end) {}
