package org.example;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ElectricityPriceAnalyzer {
    private static final ZoneId zoneId = ZoneId.of("Europe/Stockholm");
    private static final LocalDate today = LocalDate.now(zoneId);

    private static Path csvPathForZone(String zone) {
        return Path.of("output", "prices_" + zone + ".csv");
    }

    public static void printCheapestAndMostExpensiveToday(String zone) throws IOException, InterruptedException {
        Path csv = csvPathForZone(zone);
        if (Files.exists(csv)) {
            ElectricityPriceFetcher.downloadTodayAndTomorrow(zone);
        }

        LocalDate today = LocalDate.now(zoneId);

        PriceEntry cheapest = null;
        PriceEntry mostExpensive = null;
        BigDecimal minPrice = null;
        BigDecimal maxPrice = null;
        OffsetDateTime minStart = null;
        OffsetDateTime maxStart = null;

        try (var reader = Files.newBufferedReader(csv)) {
            String line;
            boolean skipFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue;
                }

                if (line.isBlank()) {
                    continue;
                }

                String[] cols = line.split(";", -1);
                if (cols.length < 3) {
                    continue;
                }

                String timeStart = cols[0];
                String timeEnd   = cols[1];
                String sekStr    = cols[2];

                if (!timeStart.startsWith(today.toString())) {
                    continue;
                }
                if (sekStr.isBlank()) {
                    continue;
                }

                BigDecimal price;
                OffsetDateTime start;
                try {
                    price = new BigDecimal(sekStr);
                    start = OffsetDateTime.parse(timeStart);
                } catch (Exception e) {
                    continue;
                }

                if (minPrice == null || price.compareTo(minPrice) < 0
                        || (price.compareTo(minPrice) == 0 && start.isBefore(minStart))) {
                    minPrice = price;
                    minStart = start;
                    cheapest = new PriceEntry(price, null, null, timeStart, timeEnd);
                }

                if (maxPrice == null || price.compareTo(maxPrice) > 0
                        || (price.compareTo(maxPrice) == 0 && start.isBefore(maxStart))) {
                    maxPrice = price;
                    maxStart = start;
                    mostExpensive = new PriceEntry(price, null, null, timeStart, timeEnd);
                }
            }
        }
        System.out.println(zone + " Cheapest and Most Expensive hours for today");
        System.out.println("Cheapest: From: " + cheapest.time_start() + " To: " + cheapest.time_end()
                + " Price: " + cheapest.SEK_per_kWh() + " SEK per kWh");
        System.out.println("Most expensive: From: " + mostExpensive.time_start() + " To: " + mostExpensive.time_end()
                + " Price: " + mostExpensive.SEK_per_kWh() + " SEK per kWh");
    }

    public static void printBestChargingHoursFromNow(String zone, int hours) throws IOException, InterruptedException {
        if (hours <= 0) {
            System.out.println("Window must be > 0 hours.");
            return;
        }

        Path csv = csvPathForZone(zone);
        if (!Files.exists(csv)) {
            ElectricityPriceFetcher.downloadTodayAndTomorrow(zone);
        }

        OffsetDateTime now = OffsetDateTime.now(zoneId).withMinute(0).withSecond(0).withNano(0);
        List<PriceEntry> remainingHours = new ArrayList<>();

        try (var reader = Files.newBufferedReader(csv)) {
            String line;
            boolean skipFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue;
                }
                if (line.isBlank()) {
                    continue;
                }

                String[] cols = line.split(";", -1);
                if (cols.length < 3) {
                    continue;
                }

                String timeStart = cols[0];
                String timeEnd   = cols[1];
                String sekStr    = cols[2];
                if (sekStr.isBlank()) {
                    continue;
                }

                OffsetDateTime start;
                try {
                    start = OffsetDateTime.parse(timeStart);
                } catch (Exception e) {
                    continue;
                }
                if (start.isBefore(now)) {
                    continue;
                }

                BigDecimal price;
                try {
                    price = new BigDecimal(sekStr);
                } catch (NumberFormatException e) {
                    continue;
                }

                remainingHours.add(new PriceEntry(price, null , null, timeStart, timeEnd));
            }
        }

        if (remainingHours.size() < hours) {
            System.out.println(zone + ": Not enough available hours for " + hours + "h.");
            return;
        }

        int bestStart = 0;
        BigDecimal bestSum = BigDecimal.ZERO;

        for (int i = 0; i < hours; i++) {
            bestSum = bestSum.add(remainingHours.get(i).SEK_per_kWh());
        }
        BigDecimal currSum = bestSum;

        for (int i = 1; i + hours <= remainingHours.size(); i++) {
            currSum = currSum
                    .subtract(remainingHours.get(i - 1).SEK_per_kWh())
                    .add(remainingHours.get(i + hours - 1).SEK_per_kWh());
            if (currSum.compareTo(bestSum) < 0) {
                bestSum = currSum;
                bestStart = i;
            }
        }

        System.out.println(zone + " Best " + hours + " charging hours from current hour:");
        for (int i = bestStart; i < bestStart + hours; i++) {
            PriceEntry priceEntry = remainingHours.get(i);
            System.out.println("From: " + priceEntry.time_start() + " to: " + priceEntry.time_end() + "  " + priceEntry.SEK_per_kWh().toPlainString() + " SEK/kWh");
        }
    }

    public static void printMeanPriceForToday(String zone) throws IOException, InterruptedException {
        Path csv = csvPathForZone(zone);

        if (!hasPriceForToday(zone)) {
            ElectricityPriceFetcher.downloadTodayAndTomorrow(zone);
        }

        try (var reader =  Files.newBufferedReader(csv)) {
            BigDecimal sum = BigDecimal.ZERO;
            int count = 0;
            String line;
            boolean skipFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue;
                }

                String[] columns = line.split(";");
                if (columns.length > 0) {
                    String timeStart = columns[0];
                    String price = columns[2];
                    if (timeStart.startsWith(today.toString())) {
                       try {
                           sum = sum.add(new BigDecimal(price));
                           count++;
                       } catch (NumberFormatException e) {

                       }
                    }
                }
            }
            if (count == 0) {
                System.out.println("Could not calculate the mean price for the current 24 hour period");
            } else {
                System.out.println("The mean price for the current 24 hour period is " + sum.divide(BigDecimal.valueOf(count), 5, RoundingMode.HALF_UP) +
                        "SEK/kWh over " + count + " hours");
            }
        }
    }

    public static boolean hasPriceForToday(String zone) throws IOException {
        Path csv = csvPathForZone(zone);
        if (!Files.exists(csv)) {
            return false;
        }

        try (var reader =  Files.newBufferedReader(csv)) {
            String line;
            boolean skipFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue;
                }

                String[] columns = line.split(";");
                if (columns.length > 0) {
                    String timeStart = columns[0];
                    if (timeStart.startsWith(today.toString())) {
                        return true;
                    }

                }

            }
        }
        return false;
    }
}
