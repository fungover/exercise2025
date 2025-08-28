// src/main/java/org/example/model/DailyExtremes.java
package org.example.model;

public record DailyExtremes(
        HourExtremes today,
        HourExtremes tomorrow
) {}
