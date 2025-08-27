/**
 * Represent a single hourly electricity price entry.
 * Fields include SEK per kWh, EUR per kWh, exchange rate, and start/end times.
 * Parsing of ISO date-time strings to ZonedDateTime.
 */

package org.example.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Elpris {
    private final double EUR_per_kWh;
    private final double SEK_per_kWh;
    private final double EXR;
    private final String time_start;
    private final String time_end;

    public Elpris(double eurPerKWh, double sekPerKWh, double exr, String timeStart, String timeEnd) {
        EUR_per_kWh = eurPerKWh;
        SEK_per_kWh = sekPerKWh;
        EXR = exr;
        time_start = timeStart;
        time_end = timeEnd;
    }

    public double getSEK() {
        return SEK_per_kWh;
    }

    public double getEUR() {
        return EUR_per_kWh;
    }

    public double getEXR() {
        return EXR;
    }

    public ZonedDateTime getTimeStart() {
        return ZonedDateTime.parse(time_start, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
    public ZonedDateTime getTimeEnd() {
        return ZonedDateTime.parse(time_end, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    }

