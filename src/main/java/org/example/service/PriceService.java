package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.PriceEntry;
import org.example.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PriceService {
    private static final Logger logger = LoggerFactory.getLogger(PriceService.class);

    private static PriceEntry[] fetchPrices(String url) {
        HttpResponse<String> response = ApiClient.getPrices(url);
        if (response == null || response.statusCode() != 200) {
            logger.error("Prices could not be fetched from {}", url);
            return new PriceEntry[0];
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        try {
            PriceEntry[] entries = mapper.readValue(response.body(), PriceEntry[].class);
            OffsetDateTime now = OffsetDateTime.now();
            entries = Arrays.stream(entries)
                    .filter(entry -> entry.time_start().isAfter(now) && entry.time_end().isAfter(now))
                    .toArray(PriceEntry[]::new);

            return entries;

        } catch (IOException e) {
            logger.error("Prices could not be parsed from {}", url, e);
            return new PriceEntry[0];
        }
    }

    public static void downloadPrices(String url, String fileName, String dayLabel) {
        try {
            PriceEntry[] entries = fetchPrices(url);
            FileUtil.createFile(fileName);
            writeHeader(fileName, dayLabel);

            Arrays.stream(entries).forEach(entry -> {
                String line = formatTimeRange(entry.time_start(), entry.time_end()) + ": " + entry.SEK_per_kWh() + " SEK_per_kWh";
                System.out.println(line);
                FileUtil.writeToFile(fileName, line + "\n");
            });
        } catch (RuntimeException e) {
            logger.error("Prices could not be written to {}", fileName, e);
        }
    }

    public static void fetchAndCalculateMeanPrice(String url, String fileName, String dayLabel) {
        try {
            PriceEntry[] entries = fetchPrices(url);
            double meanPrice = Arrays.stream(entries).mapToDouble(PriceEntry::SEK_per_kWh).average().orElse(0);

            FileUtil.createFile(fileName);
            writeHeader(fileName, dayLabel);

            String line = meanPrice + " SEK_per_kWh";
            System.out.println(line);
            FileUtil.writeToFile(fileName, line + "\n");
        } catch (RuntimeException e) {
            logger.error("Prices could not be parsed from {}", url, e);
        }
    }

    public static void fetchAndPrintCheapestAndMostExpensivePrices(String url, String fileName, String dayLabel) {
        try {
            PriceEntry[] entries = fetchPrices(url);
            PriceEntry cheapestEntry = Arrays.stream(entries)
                    .min(java.util.Comparator
                            .comparingDouble(PriceEntry::SEK_per_kWh)
                            .thenComparing(PriceEntry::time_start))
                    .orElse(null);

            PriceEntry expensiveEntry = Arrays.stream(entries)
                    .max(java.util.Comparator
                            .comparingDouble(PriceEntry::SEK_per_kWh)
                            .thenComparing(PriceEntry::time_start))
                    .orElse(null);

            System.out.println("Cheapest price: \n" + formatTimeRange(cheapestEntry.time_start(), cheapestEntry.time_end()) + "\n" + cheapestEntry.SEK_per_kWh() + " SEK_per_kWh");
            System.out.println("Most expensive price: \n" + formatTimeRange(expensiveEntry.time_start(), expensiveEntry.time_end()) + "\n" + expensiveEntry.SEK_per_kWh() + " SEK_per_kWh");

        } catch (RuntimeException e) {
            logger.error("Prices could not be parsed from {}", url, e);
        }
    }

    public static void optimalChargeTime(String url, String fileName, String dayLabel) {
        try {
            PriceEntry[] entries = fetchPrices(url);

            System.out.println("=== " + dayLabel + " ===");

            int[] durations = {2, 4, 8};

            for (int duration : durations) {
                int optimalHours = slidingWindowAlgorithm(entries, entries.length, duration);

                if (optimalHours == -1) {
                    System.out.println("Not enough data to calculate optimal charge time for " + duration + "h");
                    continue;
                }

                double totalCost = 0;
                for (int i = optimalHours; i < optimalHours + duration; i++) {
                    totalCost += entries[i].SEK_per_kWh();
                }

                System.out.println("(" + duration + "h): " +
                        formatTimeRange(entries[optimalHours].time_start(),
                                entries[optimalHours + duration - 1].time_end()) + "\n Total cost: " + totalCost + "SEK_per_kWh");
                //Add SEK
            }


        } catch (RuntimeException e) {
            logger.error("Prices could not be parsed from {}", url, e);
        }

    }

    private static void writeHeader(String fileName, String header) {
        String line = "===== " + header + " =====";
        System.out.println(line);
        FileUtil.writeToFile(fileName, line + "\n");
    }

    private static String formatTimeRange(OffsetDateTime start, OffsetDateTime end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return start.atZoneSameInstant(ZoneId.systemDefault()).format(formatter) + " - " +
                end.atZoneSameInstant(ZoneId.systemDefault()).format(formatter);
    }

    private static int slidingWindowAlgorithm(PriceEntry[] array, int arrayLength, int windowSize) {
        if (arrayLength <= windowSize) {
            return -1;
        }

        double windowSum = 0;

        for (int i = 0; i < windowSize; i++)
            windowSum += array[i].SEK_per_kWh();

        double minSum = windowSum;
        int minIndex = 0;

        for (int i = windowSize; i < arrayLength; i++) {
            windowSum += array[i].SEK_per_kWh() - array[i - windowSize].SEK_per_kWh();
            if (windowSum < minSum) {
                minSum = windowSum;
                minIndex = i - windowSize + 1;
            }
        }

        return minIndex;
    }

}
