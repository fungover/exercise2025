package org.example.model;

public class HourlyPrice {
    private final int hour;
    private final double sekPerKwh;
    private final double eurPerKwh;
    private final String zone;

    public HourlyPrice(int hour, double sekPerKwh, double eurPerKwh, String zone) {
        if (hour < 0 || hour > 23) throw new IllegalArgumentException("Hour must be between 0 and 23");
        if (sekPerKwh < 0) throw new IllegalArgumentException("SEK per kWh must be positive");
        if (eurPerKwh < 0) throw new IllegalArgumentException("EUR per kWh must be positive");
        if (zone == null || zone.isBlank()) throw new IllegalArgumentException("Zone must not be null or blank");

        this.hour = hour;
        this.sekPerKwh = sekPerKwh;
        this.eurPerKwh = eurPerKwh;
        this.zone = zone;
    }

    public int getHour() {
        return hour;
    }

    public double getSekPerKwh() {
        return sekPerKwh;
    }

    public double getEurPerKwh() {
        return eurPerKwh;
    }

    public String getZone() {
        return zone;
    }

    @Override
    public String toString() {
        return String.format("Hour %d: SEK %.2f, EUR %.2f (%s)", hour, sekPerKwh, eurPerKwh, zone);
    }
}
