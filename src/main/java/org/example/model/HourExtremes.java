package org.example.model;

public record HourExtremes(PricePoint cheapest, PricePoint mostExpensive) {
    public static final HourExtremes EMPTY = new HourExtremes(null, null);
}


