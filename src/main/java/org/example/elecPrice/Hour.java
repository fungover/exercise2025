package org.example.elecPrice;

import java.time.OffsetDateTime;

public record Hour(Double SEK_per_kWh, Double EUR_per_kWh, Double EXR, String time_start, String time_end) {
	public int formatHour(String timeUnparsed) {
		OffsetDateTime parsed = OffsetDateTime.parse(timeUnparsed);
		return parsed.getHour();
	};
}
