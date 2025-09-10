package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// @JsonIgnoreProperties gör att vi slipper fel om API:t skickar med extra fält.
@JsonIgnoreProperties(ignoreUnknown = true)

// Representerar en rad i elpris-datan (från JSON)
public class PricePoint {

    private String hour;
    private double price;

    // Konstruktor för tester
    public PricePoint(String hour, double price) {
        this.hour = hour;
        this.price = price;
    }

    // Tom konstruktor för Jackson (JSON inläsning)
    public PricePoint() {
    }

    public String getHour() {
        return hour;
    }

    public double getPrice() {
        return price;
    }

    // Setters som JACKSON anropar när den läser JSON

    // Fältet "time_start" kommer från API:et som en lång datum/tid-sträng ("2025-09-10T01:00:00+02:00"),
    // Tar bara ut själva klockslaget (HH:mm) så att vi kan jobba enklare med timmen i programmet.
    @JsonProperty("time_start")
    public void setTimeStart(String timeStart) {
        if (timeStart != null && timeStart.length() >= 16) {
            this.hour = timeStart.substring(11, 16); // "HH:mm"
        } else {
            this.hour = timeStart; // fallback om formatet avviker
        }
    }

    // API-fält: "SEK_per_kWh" sparas i vårt price-fält
    @JsonProperty("SEK_per_kWh")
    public void setPrice(double price) {
        this.price = price;
    }
}
