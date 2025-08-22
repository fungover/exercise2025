package org.example.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


// Record to store the result of the charging operation.


public record ChargingResult(
        OffsetDateTime startTime,
        OffsetDateTime endTime,
        BigDecimal totalCost,
        int chargingHours
) {}
