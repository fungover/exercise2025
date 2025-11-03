package org.example.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.model.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;


public final class ResultViews {
    private static final ObjectMapper M = new ObjectMapper().registerModule(new JavaTimeModule());
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm");
    private ResultViews() {
    }

    /**
     * Option 1: prices (today + tomorrow, upcoming only)
     */
    public static String prices(String json, ZoneId zone, LocalDate date, String area, Scanner scanner)
            throws IOException {
        List<PricePoint> pts = M.readValue(json, new TypeReference<>() {
        });
        if (pts.isEmpty()) return "No upcoming hours for " + date + " (" + area + ").";

        StringBuilder preview = new StringBuilder("Upcoming hours for ")
                .append(area).append(" starting ").append(date).append(":\n");
        int limit = Math.min(pts.size(), 8);
        for (int i = 0; i < limit; i++) preview.append(" • ").append(slot(pts.get(i), zone)).append('\n');
        if (pts.size() > limit) preview.append(" … and ").append(pts.size() - limit).append(" more hours");

        System.out.println(preview);
        System.out.print("\nSave CSV to ./out? [y/N]: ");

        String ans = scanner.nextLine();
        if (ans.isBlank()) ans = scanner.nextLine();
        ans = ans.trim().toLowerCase();
        if (!ans.equals("y") && !ans.equals("yes")) return "Skipped saving.";

        var path = PriceCsvExporter.exportPricesCsv(pts, zone, area);
        return (path == null) ? "No data to save." : "Saved " + pts.size() + " rows to " + path.toAbsolutePath();
    }

    /**
     * Option 2: daily mean
     */
    public static String mean(String json, LocalDate date, String area) throws IOException {
        DailyMean m = M.readValue(json, DailyMean.class);

        if (m.meanSekPerKWh() == null || m.hours() == 0) {
            return "No price data available for " + date + " (" + area + ").";
        }

        String meanText = price(m.meanSekPerKWh());
        return "Mean price for " + date + " (" + area + "): "
                + meanText + " SEK/kWh (" + m.hours() + " hours)";
    }

    /**
     * Option 3: extremes (today and tomorrow)
     */
    public static String extremes(String json, ZoneId zone, LocalDate date, String area) throws IOException {
        DailyExtremes d = M.readValue(json, DailyExtremes.class);
        return "Cheapest & most expensive (remaining) for " + area + ":\n"
                + " Today (" + date + "):\n"
                + "  - Cheapest:       " + pp(d.today().cheapest(), zone) + "\n"
                + "  - Most expensive: " + pp(d.today().mostExpensive(), zone) + "\n"
                + " Tomorrow (" + date.plusDays(1) + "):\n"
                + "  - Cheapest:       " + pp(d.tomorrow().cheapest(), zone) + "\n"
                + "  - Most expensive: " + pp(d.tomorrow().mostExpensive(), zone);
    }

    /**
     * Option 4: charging windows (2/4/8h, today and tomorrow)
     */
    public static String chargeWindows(String json, ZoneId zone, LocalDate date, String area) throws IOException {
        DailyChargeWindows d = M.readValue(json, DailyChargeWindows.class);
        return "Best charging windows for " + area + " (remaining hours):\n"
                + " Today (" + date + "):\n"
                + win("2h", d.today().h2(), zone) + "\n"
                + win("4h", d.today().h4(), zone) + "\n"
                + win("8h", d.today().h8(), zone) + "\n"
                + " Tomorrow (" + date.plusDays(1) + "):\n"
                + win("2h", d.tomorrow().h2(), zone) + "\n"
                + win("4h", d.tomorrow().h4(), zone) + "\n"
                + win("8h", d.tomorrow().h8(), zone);
    }

    /**
     * helpers
     */
    private static String slot(PricePoint p, ZoneId zone) {
        return t(p.start(), zone) + "–" + t(p.end(), zone) + "  " + price(p.sekPerKWh()) + " SEK/kWh";
    }

    private static String pp(PricePoint p, ZoneId zone) {
        return (p == null) ? "—" : slot(p, zone);
    }

    private static String win(String label, ChargeWindow w, ZoneId zone) {
        if (w == null) return "  - " + label + ": —";
        return "  - " + label + ": " + t(w.start(), zone) + "–" + t(w.end(), zone)
                + "  avg " + price(w.averageSekPerKWh()) + " (sum " + price(w.sumSekPerKWh()) + ")";
    }

    private static String t(OffsetDateTime odt, ZoneId zone) {
        return odt.atZoneSameInstant(zone).format(TF);
    }

    private static String price(BigDecimal v) {
        return v.setScale(3, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
    }
}
