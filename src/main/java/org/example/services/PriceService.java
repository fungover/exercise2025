package org.example.services;
import org.example.api.PriceApiClient;
import org.example.model.PricePoint;
import org.example.util.PriceJson;
import org.example.util.PriceOps;
import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public record PriceService(PriceApiClient apiClient, ZoneId zoneId) {

    public String handleChoice(int mainMenuChoice, LocalDate date, String areaCode) throws IOException, InterruptedException {
        return switch (mainMenuChoice) {
            case 1 -> downloadPrices(date, areaCode);
            case 2 -> calculateMean(areaCode);
            case 3 -> findExtremes(areaCode);
            case 4 -> bestChargingWindow(areaCode);
            default -> "Invalid option!";
        };
    }

    private String downloadPrices(LocalDate date, String areaCode)
            throws IOException, InterruptedException {

        // Fetch raw JSON for today and tomorrow
        String todayJson = apiClient.fetchPrices(date, areaCode);
        String tomorrowJson = apiClient.fetchPrices(date.plusDays(1), areaCode);

        // Parse to typed lists
        List<PricePoint> today = PriceJson.parseList(todayJson);
        List<PricePoint> tomorrow = PriceJson.parseList(tomorrowJson);

        // Merge + filter by time (zoneId provided by App)
        List<PricePoint> merged = PriceOps.mergeSorted(today, tomorrow);
        OffsetDateTime now = ZonedDateTime.now(zoneId).toOffsetDateTime();
        List<PricePoint> futureOnly = PriceOps.futureOrCurrent(merged, now);

        // Return as JSON so the CLI can just println
        return PriceJson.toPrettyJson(futureOnly);
    }

    private String calculateMean(String areaCode) {
        return "Mean price for area " + areaCode + ": ... (not implemented yet)";
    }

    private String findExtremes(String areaCode) {
        return "Cheapest and most expensive hours for area " + areaCode + ": ... (not implemented yet)";
    }

    private String bestChargingWindow(String areaCode) {
        return "Best charging window for area " + areaCode + ": ... (not implemented yet)";
    }
}
