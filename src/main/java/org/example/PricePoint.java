package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// @JsonIgnoreProperties gör att vi slipper fel om API:t skickar med extra fält.
@JsonIgnoreProperties(ignoreUnknown = true)

// Representerar en rad i elpris-datan (från JSON)
public class PricePoint {

    private String date;
    private String hour;
    private double price;

    // Konstruktor för tester
    public PricePoint(String hour, double price) {
        this.hour = hour;
        this.price = price;
    }

    // Extra konstruktor för tester där vi vill ange både datum och timme
    public PricePoint(String date, String hour, double price) {
        this.date = date;
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

    public String getDate() { return date; }

    // Setters som JACKSON anropar när den läser JSON

    // Fältet "time_start" kommer från API:et som en lång datum/tid-sträng ("2025-09-10T01:00:00+02:00"),
    // Tar bara ut själva klockslaget (HH:mm) så att vi kan jobba enklare med timmen i programmet.
    @JsonProperty("time_start")
    public void setTimeStart(String timeStart) {
        // Om värdet saknas helt -> standardvärden
        if (timeStart == null || timeStart.isBlank()) {
            this.date = "";
            this.hour = "00:00";
            return;
        }
        try {
            // Försök tolka datum/tid med java.time och ISO-format (hanterar Z, +02:00 osv.)
            var zdt = java.time.OffsetDateTime.parse(
                    timeStart,
                    java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME
            );

            // Spara datum (yyyy-MM-dd) och klockslag (HH:mm)
            this.date = zdt.toLocalDate().toString();
            this.hour = zdt.toLocalTime()
                    .withSecond(0).withNano(0) // tar bort sekunder/nano
                    .format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));

        } catch (Exception e) {
            // Fallback om formatet är oväntat → försök med substring
            this.date = timeStart.length() >= 10 ? timeStart.substring(0, 10) : "";
            this.hour = timeStart.length() >= 16 ? timeStart.substring(11, 16) : "00:00";
        }
    }


    // API-fält: "SEK_per_kWh" sparas i vårt price-fält
    @JsonProperty("SEK_per_kWh")
    public void setPrice(double price) {
        this.price = price;
    }
}
