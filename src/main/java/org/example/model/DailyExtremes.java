package org.example.model;

public record DailyExtremes(
        HourExtremes today,
        HourExtremes tomorrow
) {}
