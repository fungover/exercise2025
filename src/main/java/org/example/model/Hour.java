package org.example.model;

import java.time.OffsetDateTime;


public record Hour(Double SEK_per_kWh, Double EUR_per_kWh, Double EXR, String time_start, String time_end) {
	public int startHour() {
		try {
			final String s = java.util.Objects.requireNonNull(time_start, "time_start is null");
			return OffsetDateTime.parse(s).getHour();
		} catch (java.time.format.DateTimeParseException e) {
			throw new IllegalArgumentException("Invalid time_start: " + time_start, e);
		}
	}
	public int endHour() {
		try {
			final String e = java.util.Objects.requireNonNull(time_end, "time_end is null");
			return OffsetDateTime.parse(e).getHour();
		} catch (java.time.format.DateTimeParseException e) {
			throw new IllegalArgumentException("Invalid time_end: " + time_end, e);
		}
	}
}
