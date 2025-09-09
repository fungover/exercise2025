package org.example.model;

import java.time.OffsetDateTime;

public record Hour(Double SEK_per_kWh, Double EUR_per_kWh, Double EXR, String time_start, String time_end) {
	public int startHour() {
		try {
			return OffsetDateTime.parse(time_start).getHour();
		} catch (java.time.format.DateTimeParseException e) {
			throw new IllegalArgumentException("Invalid time_start: " + time_start, e);
		}
	}
	public int endHour() {
		try {
			return OffsetDateTime.parse(time_end).getHour();
		} catch (java.time.format.DateTimeParseException e) {
			throw new IllegalArgumentException("Invalid time_end: " + time_end, e);
		}
	}
}
