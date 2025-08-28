package org.example.ui;
import org.example.model.PricePoint;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class PriceCsvExporter {
    private PriceCsvExporter() {}

    /** Writes a CSV to ./out and returns the file path. Returns null if list empty. */
    public static Path exportPricesCsv(List<PricePoint> pts, ZoneId zone, LocalDate date, String area)
            throws IOException {
        if (pts == null || pts.isEmpty()) return null;

        Path dir = Path.of("out");                 // project-local
        Files.createDirectories(dir);

        // local dates for filename
        LocalDate startDate = pts.getFirst().start().atZoneSameInstant(zone).toLocalDate();
        LocalDate endDate   = pts.getLast().end().atZoneSameInstant(zone).toLocalDate();

        String safeArea = area.replaceAll("[^A-Za-z0-9_-]", "_");
        String fileName = startDate.equals(endDate)
                ? "prices_" + safeArea + "_" + startDate + "_upcoming.csv"
                : "prices_" + safeArea + "_" + startDate + "_to_" + endDate + "_upcoming.csv";

        Path file = dir.resolve(fileName);

        // CSV content
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder csv = new StringBuilder("start_local,end_local,sek_per_kwh\n");
        for (PricePoint p : pts) {
            csv.append(p.start().atZoneSameInstant(zone).format(df)).append(',')
                    .append(p.end().atZoneSameInstant(zone).format(df)).append(',')
                    .append(csvPrice(p.sekPerKWh()))
                    .append('\n');
        }

        Files.writeString(file, csv.toString(), StandardCharsets.UTF_8);
        return file;
    }

    private static String csvPrice(BigDecimal v) {
        return v.setScale(3, RoundingMode.HALF_UP).toPlainString();
    }
}