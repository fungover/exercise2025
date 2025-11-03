package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DailyMean(
        LocalDate date,
        String area,
        BigDecimal meanSekPerKWh,
        int hours
) {}
