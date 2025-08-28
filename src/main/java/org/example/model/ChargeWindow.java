package org.example.model;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ChargeWindow(
        OffsetDateTime start,
        OffsetDateTime end,
        int hours,
        BigDecimal averageSekPerKWh,
        BigDecimal sumSekPerKWh
) {}
