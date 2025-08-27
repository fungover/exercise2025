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
import java.util.Arrays;

public class PriceService {
    private static final Logger logger = LoggerFactory.getLogger(PriceService.class);

    private static PriceEntry[] fetchPrices(String url, boolean futureOnly) {
        HttpResponse<String> response = ApiClient.getPrices(url);
        if (response == null) {
            logger.error("No response received from {}", url);
            return new PriceEntry[0];
        }

        int status = response.statusCode();

        if (status == 404) {
            logger.warn("Prices at {} does not exist yet (404)", url);
            return new PriceEntry[0];
        }

        if (status != 200) {
            logger.error("Failed to fetch prices from {}. HTTP status: {}", url, status);
            return new PriceEntry[0];
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        try {
            PriceEntry[] entries = mapper.readValue(response.body(), PriceEntry[].class);

            if (futureOnly) {
                OffsetDateTime now = OffsetDateTime.now();
                entries = Arrays.stream(entries)
                        .filter(entry -> entry.time_end().isAfter(now))
                        .toArray(PriceEntry[]::new);
            }

            return entries;

        } catch (IOException e) {
            logger.error("Prices could not be parsed from {}", url, e);
            return new PriceEntry[0];
        }
    }

    public static void downloadPrices(String url, String fileName, String dayLabel) {
        try {
            PriceEntry[] entries = fetchPrices(url, true);

            if (entries.length == 0) {
                logger.warn("No prices found for {}", url);
                return;
            }

            int fileStatus = FileUtil.createFile(fileName);

            if (fileStatus != -1) {
                FileUtil.writeToFile(fileName, "Start,End,Price_SEK_per_kWh\n");
            }
            System.out.println("=== " + dayLabel + " ===");
            Arrays.stream(entries).forEach(entry -> {
                double roundedPrice = Math.round(entry.SEK_per_kWh() * 100) / 100.0;

                String line = formatTimeRange(entry.time_start(), entry.time_end()) + ": \n" + roundedPrice + " SEK_per_kWh \n";
                String csvLine = entry.time_start() + "," + entry.time_end() + "," + roundedPrice + "," ;

                System.out.println(line);

                FileUtil.writeToFile(fileName, csvLine + "\n");


            });
        } catch (RuntimeException e) {
            logger.error("Prices could not be written to {}", fileName, e);
        }
    }

    public static void fetchAndCalculateMeanPrice(String url, String fileName, String dayLabel) {
        try {
            PriceEntry[] entries = fetchPrices(url, false);
            double meanPrice = Arrays.stream(entries).mapToDouble(PriceEntry::SEK_per_kWh).average().orElse(0);
            double roundedMeanPrice = Math.round(meanPrice * 100) / 100.0;

            int fileStatus = FileUtil.createFile(fileName);

            if (fileStatus != -1) {
                FileUtil.writeToFile(fileName, "Start,End,Price_SEK_per_kWh\n");
            }

            String line = roundedMeanPrice + " SEK_per_kWh \n";
            String csvLine = roundedMeanPrice + "";

            System.out.println("=== " + dayLabel + " ===");
            System.out.println(line);
            FileUtil.writeToFile(fileName, csvLine + "\n");

        } catch (RuntimeException e) {
            logger.error("Prices could not be parsed from {}", url, e);
        }
    }

    public static void fetchAndPrintCheapestAndMostExpensivePrices(String url, String fileName, String dayLabel) {
        try {
            PriceEntry[] entries = fetchPrices(url, true);

            int fileStatus = FileUtil.createFile(fileName);

            if (fileStatus != -1) {
                FileUtil.writeToFile(fileName, "Start,End,Price_SEK_per_kWh\n");
            }

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

            double roundedCheapestPrice = Math.round(cheapestEntry.SEK_per_kWh() * 100) / 100.0;
            double roundedExpensivePrice = Math.round(expensiveEntry.SEK_per_kWh() * 100) / 100.0;

            System.out.println("=== " + dayLabel + " ===");
            String cheapestLine = "Cheapest price: \n" + formatTimeRange(cheapestEntry.time_start(), cheapestEntry.time_end()) + "\n" + roundedCheapestPrice + " SEK_per_kWh";
            String expensiveLine = "\nMost expensive price: \n" + formatTimeRange(expensiveEntry.time_start(), expensiveEntry.time_end()) + "\n" + roundedExpensivePrice + " SEK_per_kWh";
            String cheapestCsvLine = cheapestEntry.time_start() + "," + cheapestEntry.time_end() + "," + roundedCheapestPrice;
            String expensiveCsvLine = expensiveEntry.time_start() + "," + expensiveEntry.time_end() + "," + roundedExpensivePrice;

            FileUtil.writeToFile(fileName, cheapestCsvLine + "\n");
            FileUtil.writeToFile(fileName, expensiveCsvLine + "\n");

            System.out.println(cheapestLine);
            System.out.println(expensiveLine);

        } catch (RuntimeException e) {
            logger.error("Prices could not be parsed from {}", url, e);
        }
    }

    public static void optimalChargeTime(String url, String fileName, String dayLabel) {
        try {
            PriceEntry[] entries = fetchPrices(url, true);

            if (entries.length == 0) {
                logger.warn("No prices found for {}", url);
                return;
            }

            int fileStatus = FileUtil.createFile(fileName);

            if (fileStatus != -1) {
                FileUtil.writeToFile(fileName, "Duration (h),Start,End,Price_SEK_per_kWh\n");
            }

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

                double roundedTotalCost = Math.round(totalCost * 100) / 100.0;

                String line = "(" + duration + "h): " +
                        formatTimeRange(entries[optimalHours].time_start(),
                                entries[optimalHours + duration - 1].time_end()) + "\n Total cost: " + roundedTotalCost + " SEK_per_kWh\n";

                String csvLine = duration + "," + entries[optimalHours].time_start().toString() + "," + entries[optimalHours + duration - 1].time_end().toString() + "," + roundedTotalCost;

                FileUtil.writeToFile(fileName, csvLine + "\n");
                System.out.println(line);
            }


        } catch (RuntimeException e) {
            logger.error("Prices could not be parsed from {}", url, e);
        }

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
