package org.example.service;

import org.example.model.PricePoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvConsumptionCalculator {

    private static final String DEFAULT_FILE = "data/consumption.csv";
    private static final ZoneId PRICE_ZONE = ZoneId.of("Europe/Stockholm");
    private static final DateTimeFormatter KEY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmXXX");

    public Map<String, Double> readConsumption(String filePath) {
        Map<String, Double> consumption = new HashMap<>();
        String pathToUse = (filePath == null || filePath.isEmpty()) ? DEFAULT_FILE : filePath;

        try (BufferedReader br = Files.newBufferedReader(Paths.get(pathToUse), StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",");
                if (parts.length != 2) continue;
                String tsRaw = parts[0].trim();
                String kwhRaw = parts[1].trim().replace("\"", "");
                double kWh;
                try {
                    kWh = Double.parseDouble(kwhRaw);
                    } catch (NumberFormatException ex) {
                    continue;
                    }
                OffsetDateTime normalizedTs;
                try {
                    normalizedTs = OffsetDateTime.parse(tsRaw);
                    } catch (java.time.format.DateTimeParseException ex1) {
                    try {
                        LocalDateTime ts = LocalDateTime.parse(tsRaw); // ISO_LOCAL_DATE_TIME; seconds optional
                        normalizedTs = ts.atZone(PRICE_ZONE).toOffsetDateTime();
                        } catch (java.time.format.DateTimeParseException ex2) {
                        continue;
                        }
                    }
                consumption.put(KEY_FORMATTER.format(normalizedTs), kWh);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file: " + pathToUse, e);
        }

        return consumption;
    }

    public double calculateTotalCost(List<PricePoint> prices, Map<String, Double> consumption) {
        if (prices == null || consumption == null || prices.isEmpty() || consumption.isEmpty()) return 0.0;

        BigDecimal total = BigDecimal.ZERO;

        for (PricePoint price : prices) {
            String key = KEY_FORMATTER.format(price.start());
            if (consumption.containsKey(key)) {
                BigDecimal cost = price.price().multiply(BigDecimal.valueOf(consumption.get(key)));
                total = total.add(cost);
            }
        }

        return total.doubleValue();
    }
}