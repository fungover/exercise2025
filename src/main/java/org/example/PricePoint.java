package org.example;

public class PricePoint {
    private final String hour;
    private final double price;

    public PricePoint(String hour, double price) {
        this.hour = hour;
        this.price = price;
    }

    public String getHour() {
        return hour;
    }

    public double getPrice() {
        return price;
    }
}
