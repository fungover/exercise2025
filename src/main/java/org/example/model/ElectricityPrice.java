package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

// This class is used to map the JSON response from the API to a Java object.
// It uses Jackson annotations to specify the JSON property names that correspond to the fields in this class
// The class is immutable, meaning once an instance is created, its fields cannot be changed.
// Note to myself (@JsonProperty) annotation tells jackson to map the JSON field SEK_per_kWh to the Java record sekPerKwh.

public record ElectricityPrice(
    @JsonProperty("SEK_per_kWh") BigDecimal sekPerKwh,
    @JsonProperty("EUR_per_kWh") BigDecimal eurPerKwh,
    @JsonProperty("EXR") BigDecimal exr,
    @JsonProperty("time_start") OffsetDateTime timeStart,
    @JsonProperty("time_end") OffsetDateTime timeEnd
) {}
