package org.example;

public class ConsumptionPoint {
    private final String time;
    private final double consumption;

    public ConsumptionPoint(String time, double consumption) {
        this.time = time;
        this.consumption = consumption;
    }

    public String getTime() {
        return time;
    }

    public double getConsumption() {
        return consumption;
    }
}
