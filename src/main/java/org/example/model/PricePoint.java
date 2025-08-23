package org.example.model;

import java.time.LocalDateTime;

// A small data class that will hold the information about
// one hour price and start time and end time.
public record PricePoint(LocalDateTime start, double price) {}
