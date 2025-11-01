package org.example.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


// Record to store the result of the charging operation.


public record ChargingResult( // Using record to store the result of the charging operation.
        OffsetDateTime startTime, // Start time of the charging period.
        OffsetDateTime endTime, // End time of the charging period.
        BigDecimal totalCost, // Total cost of the charging period.
        int chargingHours // Number of hours the car is charged.
) {}
