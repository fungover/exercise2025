package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

class ElectricityPrice {
    @JsonProperty("SEK_per_kWh")
    private double SEK_per_kWh;

    @JsonProperty("EUR_per_kWh")
    private double EUR_per_kWh;

    @JsonProperty("EXR")
    private double EXR;

    @JsonProperty("time_start")
    private String time_start;

    @JsonProperty("time_end")
    private String time_end;

    public ElectricityPrice() {}

    public ElectricityPrice(double SEK_per_kWh, double EUR_per_kWh, double EXR, String time_start, String time_end) {
        this.SEK_per_kWh = SEK_per_kWh;
        this.EUR_per_kWh = EUR_per_kWh;
        this.EXR = EXR;
        this.time_start = time_start;
        this.time_end = time_end;
    }

    public double getSEK_per_kWh() {
        return SEK_per_kWh;
    }

    public double getEUR_per_kWh() {
        return EUR_per_kWh;
    }

    public double getEXR() {
        return EXR;
    }

    public String getTime_start() {
        return time_start;
    }

    public String getTime_end() {
        return time_end;
    }

}
