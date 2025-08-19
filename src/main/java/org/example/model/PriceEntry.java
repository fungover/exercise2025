package org.example.model;

import java.time.LocalDateTime;

public class PriceEntry {
    private double price;
    private LocalDateTime time;


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
