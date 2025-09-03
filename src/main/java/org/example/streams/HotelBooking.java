package org.example.streams;

import java.math.BigDecimal;
import java.time.LocalDate;

public record HotelBooking(Guest guest, String roomType, LocalDate checkInDate, LocalDate checkOutDate, BigDecimal pricePerNight) {}