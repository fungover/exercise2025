package org.example.services;
import org.example.api.PriceApiClient;
import org.example.model.*;
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
            case 4 -> bestChargingWindow(date, areaCode);
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

        if (points.isEmpty()) {
            return PriceJson.toPrettyJson(new DailyMean(date, areaCode, null, 0));
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (PricePoint p : points) {
            sum = sum.add(p.sekPerKWh());
        }

        BigDecimal mean = sum.divide(BigDecimal.valueOf(points.size()), 5, RoundingMode.HALF_UP);
        return PriceJson.toPrettyJson(new DailyMean(date, areaCode, mean, points.size()));
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

    /** Best charging windows (2/4/8h) for today & tomorrow, using upcoming hours only. */
    private String bestChargingWindow(LocalDate date, String areaCode)
            throws IOException, InterruptedException {
        var today = fetchUpcomingToday(date, areaCode);
        var tomorrow = fetchUpcomingTomorrow(date, areaCode);
        // Compute best windows; nulls if not enough hours left
        ChargeWindows todayWins = new ChargeWindows(
                PriceOps.bestWindow(today, 2),
                PriceOps.bestWindow(today, 4),
                PriceOps.bestWindow(today, 8)
        );

        ChargeWindows tomorrowWins = new ChargeWindows(
                PriceOps.bestWindow(tomorrow, 2),
                PriceOps.bestWindow(tomorrow, 4),
                PriceOps.bestWindow(tomorrow, 8)
        );
        return PriceJson.toPrettyJson(new DailyChargeWindows(todayWins, tomorrowWins));
    }

    /** Upcoming prices for *today* only (filters past hours). */
    private List<PricePoint> fetchUpcomingToday(LocalDate date, String areaCode)
            throws IOException, InterruptedException {
        String json = apiClient.fetchPrices(date, areaCode);
        List<PricePoint> points = PriceJson.parseList(json);
        return PriceOps.futureOrCurrent(points, now()); // includes current hour
    }

    /** Upcoming prices for *tomorrow* only (filters past hours relative to now). */
    private List<PricePoint> fetchUpcomingTomorrow(LocalDate date, String areaCode)
            throws IOException, InterruptedException {
        String json = apiClient.fetchPrices(date.plusDays(1), areaCode);
        List<PricePoint> points = PriceJson.parseList(json);
        return PriceOps.futureOrCurrent(points, now());
    }

    private OffsetDateTime now() {
        return ZonedDateTime.now(zoneId).toOffsetDateTime();
    }

}
