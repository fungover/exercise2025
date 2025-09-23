package org.example.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeUtils {

    public static String toHHmm(String isoTimestamp) {
        try {
            // Parse ISO timestamp and extract HH:mm format
            LocalDateTime dateTime = LocalDateTime.parse(isoTimestamp);
            return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            // Fallback to substring if parsing fails (maintain backward compatibility)
            if (isoTimestamp != null && isoTimestamp.length() >= 16) {
                return isoTimestamp.substring(11, 16);
            }
            return "00:00";
        }
    }
}
