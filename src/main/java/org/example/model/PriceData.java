package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceData {
    @JsonProperty("time_start")
    private String time_start;

    @JsonProperty("time_end")
    private String time_end;

    @JsonProperty("SEK_per_kWh")
    private double SEK_per_kWh;

    /**
     * Creates a new PriceData instance.
     *
     * No-argument constructor required for JSON deserialization (e.g., by Jackson).
     * Fields are initialized to their Java defaults (null for strings, 0.0 for numeric).
     */
    public PriceData() {
    }

    /**
     * Create a PriceData representing a price for a time interval.
     *
     * @param time_start   start of the interval (as a timestamp string)
     * @param time_end     end of the interval (as a timestamp string)
     * @param SEK_per_kWh  price in Swedish krona per kilowatt-hour for the interval
     */
    public PriceData(String time_start, String time_end, double SEK_per_kWh) {
        this.time_start = time_start;
        this.time_end = time_end;
        this.SEK_per_kWh = SEK_per_kWh;
    }

    /**
     * Returns the start timestamp for this price interval.
     *
     * @return the `time_start` value as represented in the model (may be null)
     */
    public String getTime_start() {
        return time_start;
    }

    /**
     * Sets the start time for this price entry.
     *
     * @param time_start the start timestamp string (maps to the JSON property "time_start")
     */
    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    /**
     * Returns the end time for the price interval.
     *
     * <p>The value is mapped from the JSON property "time_end". May be null if not present or not set.
     *
     * @return the end time string for the interval, or null if unset
     */
    public String getTime_end() {
        return time_end;
    }

    /**
     * Sets the end time of the price interval (maps to the JSON property `time_end`).
     *
     * @param time_end the end time value as provided in the `time_end` JSON field
     */
    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    /**
     * Returns the price in Swedish krona per kilowatt-hour.
     *
     * @return the price (SEK) per kWh
     */
    public double getSEK_per_kWh() {
        return SEK_per_kWh;
    }

    /**
     * Set the price in Swedish krona per kilowatt-hour.
     *
     * @param SEK_per_kWh price value expressed in SEK per kWh
     */
    public void setSEK_per_kWh(double SEK_per_kWh) {
        this.SEK_per_kWh = SEK_per_kWh;
    }
}
