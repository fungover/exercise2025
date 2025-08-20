package org.example;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Elpris {
    private double EUR_per_kWh;
    private double SEK_per_kWh;
    private double EXR;
    private String time_start;
    private String time_end;

    public double getSEK() {
        return SEK_per_kWh;
    }

    public double getEUR() {
        return EUR_per_kWh;
    }

    public ZonedDateTime getTimeStart() {
        return ZonedDateTime.parse(time_start, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
    public ZonedDateTime getTimeEnd() {
        return ZonedDateTime.parse(time_end, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    }

