package org.example.model;

public record DailyChargeWindows(
        ChargeWindows today,
        ChargeWindows tomorrow
) {}
