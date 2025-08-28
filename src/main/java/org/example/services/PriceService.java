package org.example.services;
import org.example.api.PriceApiClient;
import org.example.model.DailyExtremes;
import org.example.model.HourExtremes;
import org.example.model.PricePoint;
import org.example.util.PriceJson;
import org.example.util.PriceOps;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public record PriceService(PriceApiClient apiClient, ZoneId zoneId) {

    public String handleChoice(int mainMenuChoice, LocalDate date, String areaCode)
            throws IOException, InterruptedException {
        return switch (mainMenuChoice) {
            case 1 -> downloadPrices(date, areaCode);
            case 2 -> calculateMean(date, areaCode);
            case 3 -> findExtremes(date, areaCode);
            case 4 -> bestChargingWindow(areaCode);
            default -> "Invalid option!";
        };
    }

    /** Get current and future hours data only (today/tomorrow) */
    private String downloadPrices(LocalDate date, String areaCode)
            throws IOException, InterruptedException {
        List<PricePoint> today = fetchUpcomingToday(date, areaCode);
        List<PricePoint> tomorrow = fetchUpcomingTomorrow(date, areaCode);
        List<PricePoint> merged = PriceOps.mergeSorted(today, tomorrow);
        return PriceJson.toPrettyJson(merged);
    }

    /** Get mean for today only */
    private String calculateMean(LocalDate date, String areaCode)
            throws IOException, InterruptedException {

        String todayJson = apiClient.fetchPrices(date, areaCode);
        List<PricePoint> points = PriceJson.parseList(todayJson);
        // Guard: no data for that day
        if (points.isEmpty()) {
            return "No price data available for " + date + " (" + areaCode + ").";
        }

        // Sum precisely with BigDecimal
        BigDecimal sum = BigDecimal.ZERO;
        for (PricePoint p : points) {
            sum = sum.add(p.sekPerKWh());
        }

        // Mean = sum / count, then format for display
        BigDecimal mean = sum.divide(BigDecimal.valueOf(points.size()), 5, RoundingMode.HALF_UP);

        String meanText = mean.setScale(3, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString();

        return "Mean price for " + date + " (" + areaCode + "): "
                + meanText + " SEK/kWh (" + points.size() + " hours)";
    }

    /** Get current and future hours data only (today/tomorrow) */
    private String findExtremes(LocalDate date, String areaCode)
            throws IOException, InterruptedException {

        var today = fetchUpcomingToday(date, areaCode);
        var tomorrow = fetchUpcomingTomorrow(date, areaCode);
        var todayEx = today.isEmpty() ? HourExtremes.EMPTY : PriceOps.extremesByPriceEarliest(today);
        var tomorrowEx = tomorrow.isEmpty() ? HourExtremes.EMPTY : PriceOps.extremesByPriceEarliest(tomorrow);

        return PriceJson.toPrettyJson(new DailyExtremes(todayEx, tomorrowEx));
    }

    private String bestChargingWindow(String areaCode) {
        return "Best charging window for area " + areaCode + ": ... (not implemented yet)";
    }

    /** Upcoming prices for *today* only (filters past hours). */
    private List<PricePoint> fetchUpcomingToday(LocalDate date, String areaCode)
            throws IOException, InterruptedException {

        String json = apiClient.fetchPrices(date, areaCode);
        List<PricePoint> points = PriceJson.parseList(json);

        OffsetDateTime now = ZonedDateTime.now(zoneId).toOffsetDateTime();
        return PriceOps.futureOrCurrent(points, now);
    }

    /** Upcoming prices for *tomorrow* only (filters past hours relative to now). */
    private List<PricePoint> fetchUpcomingTomorrow(LocalDate date, String areaCode)
            throws IOException, InterruptedException {

        String json = apiClient.fetchPrices(date.plusDays(1), areaCode);
        List<PricePoint> points = PriceJson.parseList(json);

        OffsetDateTime now = ZonedDateTime.now(zoneId).toOffsetDateTime();
        return PriceOps.futureOrCurrent(points, now);
    }
}
