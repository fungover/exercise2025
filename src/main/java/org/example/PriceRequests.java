package org.example;

public record PriceRequests(int year, int month, int day, String zone) {

    public PriceRequests {

        if (zone == null || !zone.matches("(?i)SE[1-4]")) {
            throw new IllegalArgumentException("zone must be one of SE1, SE2, SE3, SE4");
            }
             zone = zone.toUpperCase(java.util.Locale.ROOT);
                 java.time.LocalDate.of(year, month, day);
            }
    public String buildUrl() {
        // Build: https://www.elprisetjustnu.se/api/v1/prices/YYYY/MM-DD_ZONE.json
         return String.format(
                 "https://www.elprisetjustnu.se/api/v1/prices/%d/%02d-%02d_%s.json",
                 year, month, day, zone);
    }
}