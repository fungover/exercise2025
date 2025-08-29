package org.example;

import java.time.ZonedDateTime;

public class Price {
    private final ZonedDateTime start;
    private final ZonedDateTime end;
    private final double sekPerKwh;

    public Price(ZonedDateTime start, ZonedDateTime end, double sekPerKwh) {
        this.start = start;
        this.end = end;
        this.sekPerKwh = sekPerKwh;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public double getSekPerKwh() {
        return sekPerKwh;
    }

    @Override
    public String toString() {
        return start.toLocalTime() + "–" + end.toLocalTime() + "  " + sekPerKwh + " SEK/kWh";
    }
}
