package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.PriceEntry;
import org.example.util.FileUtil;

import java.net.http.HttpResponse;
import java.util.Arrays;

public class PriceService {

    public static void downloadPrices(String url, String fileName, String dayLabel) {
        HttpResponse<String> response = ApiClient.getPrices(url);
        if (response == null || response.statusCode() != 200) {
            System.out.println(dayLabel + " could not be downloaded.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        try {
            PriceEntry[] entries = mapper.readValue(response.body(), PriceEntry[].class);
            FileUtil.createFile(fileName);
            writeHeader(fileName, dayLabel);

            Arrays.stream(entries).forEach(entry -> {
                String line = entry.time_start() + " - " + entry.time_end() + ": " + entry.SEK_per_kWh() + " SEK_per_kWh";
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
            writeHeader(fileName, dayLabel + " (mean)");

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
                    .min((e1, e2) -> Double.compare(e1.SEK_per_kWh(), e2.SEK_per_kWh()))
                    .orElse(null);

            PriceEntry expensiveEntry = Arrays.stream(entries)
                    .max((e1, e2) -> Double.compare(e1.SEK_per_kWh(), e2.SEK_per_kWh()))
                    .orElse(null);

            System.out.println("Cheapest price: \n" + cheapestEntry.time_start() + " - " + cheapestEntry.time_end() + "\n" + cheapestEntry.SEK_per_kWh() + " SEK_per_kWh");
            System.out.println("Most expensive price: \n" + expensiveEntry.time_start() + " - " + expensiveEntry.time_end() + "\n" + expensiveEntry.SEK_per_kWh() + " SEK_per_kWh");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeHeader(String fileName, String header) {
        String line = "===== " + header + " =====";
        System.out.println(line);
        FileUtil.writeToFile(fileName, line + "\n");
    }
}
