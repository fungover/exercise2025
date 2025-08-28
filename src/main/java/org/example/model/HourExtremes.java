package org.example.model;

// HourExtremes.java
public record HourExtremes(PricePoint cheapest, PricePoint mostExpensive) {
    public static final HourExtremes EMPTY = new HourExtremes(null, null);
}


