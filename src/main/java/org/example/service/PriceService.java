package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.PriceEntry;
import org.example.util.FileUtil;

import java.net.http.HttpResponse;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class PriceService {
    private static PriceEntry[] fetchPrices(String url) {
        HttpResponse<String> response = ApiClient.getPrices(url);
        if (response == null || response.statusCode() != 200) {
            System.out.println("Prices could not be downloaded.");
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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new PriceEntry[0];
    }


    public static void downloadPrices(String url, String fileName, String dayLabel) {
        PriceEntry[] entries = fetchPrices(url);

        try {
            FileUtil.createFile(fileName);
            writeHeader(fileName, dayLabel);

            Arrays.stream(entries).forEach(entry -> {
                String line = formatTimeRange(entry.time_start(), entry.time_end()) + ": " + entry.SEK_per_kWh() + " SEK_per_kWh";
                System.out.println(line);
                FileUtil.writeToFile(fileName, line + "\n");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fetchAndCalculateMeanPrice(String url, String fileName, String dayLabel) {
        HttpResponse<String> response = ApiClient.getPrices(url);
        if (response == null || response.statusCode() != 200) {
            System.out.println(dayLabel + " could not be downloaded.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        try {
            PriceEntry[] entries = mapper.readValue(response.body(), PriceEntry[].class);
            double meanPrice = Arrays.stream(entries).mapToDouble(PriceEntry::SEK_per_kWh).average().orElse(0);

            FileUtil.createFile(fileName);
            writeHeader(fileName, dayLabel);

            String line = meanPrice + " SEK_per_kWh";
            System.out.println(line);
            FileUtil.writeToFile(fileName, line + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fetchAndPrintCheapestAndMostExpensivePrices(String url, String fileName, String dayLabel) {
        HttpResponse<String> response = ApiClient.getPrices(url);
        if (response == null || response.statusCode() != 200) {
            System.out.println(dayLabel + " could not be downloaded.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        try {
            PriceEntry[] entries = mapper.readValue(response.body(), PriceEntry[].class);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void optimalChargeTime(String url, String fileName, String dayLabel) {
        HttpResponse<String> response = ApiClient.getPrices(url);
        if (response == null || response.statusCode() != 200) {
            System.out.println(dayLabel + " could not be downloaded.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        try {
            PriceEntry[] entries = mapper.readValue(response.body(), PriceEntry[].class);

            int[] durations = {2, 4, 8};

            for (int duration : durations) {
                int omptimalHours = slidingWindowAlgorithm(entries, entries.length, duration);
                System.out.println("Optimal charge time (" + duration + "h): " +
                        formatTimeRange(entries[omptimalHours].time_start(),
                                entries[omptimalHours + duration - 1].time_end()));
            }


        } catch (Exception e) {
            e.printStackTrace();
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
            System.out.println("Invalid");
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
