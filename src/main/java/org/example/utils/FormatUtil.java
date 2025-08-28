package org.example.utils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class FormatUtil {

    public static String formatDate(String isoString) {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(isoString);
        return offsetDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String formatTime(String isoString) {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(isoString);
        return offsetDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static String formatPriceSEK(double sek) {
        return String.format(java.util.Locale.US, "%.5f SEK/kWh", sek);
    }

    public static String formatPriceEUR(double eur) {
        return String.format(java.util.Locale.US, "%.5f EUR/kWh", eur);
    }

    public static String formatEXR(double exr) {
        return String.format(java.util.Locale.US, "%.6f", exr);
    }
}
