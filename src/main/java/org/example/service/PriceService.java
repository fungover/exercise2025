package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.PriceEntry;
import org.example.util.FileUtil;

import java.net.http.HttpResponse;

public class PriceService {
    public static void downloadPrices(String url, String fileName, String dayLabel) {
        HttpResponse<String> prices = ApiClient.getPrices(url);
        if (prices != null && prices.statusCode() == 200) {
            System.out.println(dayLabel);

            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();

            try {
                PriceEntry[] entries = mapper.readValue(prices.body(), PriceEntry[].class);
                System.out.println(dayLabel + " prices:");
                FileUtil.createFile(fileName);

                boolean firstLine = true;

                for (PriceEntry entry : entries) {
                    if (firstLine) {
                        System.out.println("===== " + dayLabel + " =====");
                        FileUtil.writeToFile(fileName, "===== " + dayLabel + " =====\n");
                        firstLine = false;
                    }
                    System.out.println(entry.time_start() + " - " + entry.time_end() + ": " + entry.SEK_per_kWh());
                    FileUtil.writeToFile(fileName, entry.time_start() + " - " + entry.time_end() + ": " + entry.SEK_per_kWh() + " SEK_per_kWh" + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println(dayLabel + "'s prices could not be downloaded. Status: " +
                    (prices != null ? prices.statusCode() : "No response"));
        }
    }

    public static void fetchAndCalculateMeanPrice(String url, String fileName, String dayLabel) {
        HttpResponse<String> prices = ApiClient.getPrices(url);
        if (prices != null && prices.statusCode() == 200) {
            System.out.println(dayLabel);

            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();

            boolean firstLine = true;

            try {
                PriceEntry[] entries = mapper.readValue(prices.body(), PriceEntry[].class);
                int numberOfEntries = entries.length;
                double totalPrice = 0;
                for (PriceEntry entry : entries) {
                    totalPrice += entry.SEK_per_kWh();
                }
                double meanPrice = totalPrice / numberOfEntries;

                System.out.println(dayLabel + " prices:");
                FileUtil.createFile(fileName);

                if (firstLine) {
                    System.out.println(dayLabel + "'s prices:");
                    FileUtil.writeToFile(fileName, "===== " + dayLabel + " =====\n");
                    firstLine = false;
                }

                System.out.println(meanPrice + " SEK_per_kWh");
                FileUtil.writeToFile(fileName, meanPrice + " SEK_per_kWh" + "\n");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(dayLabel + "'s prices could not be downloaded. Status: " +
                    (prices != null ? prices.statusCode() : "No response"));
        }
    }
}
