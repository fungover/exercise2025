package org.example;

import java.io.IOException;

public class App {
     static void main(String[] args) {
        try {
            String year = "2025";
            String month = "08";
            String day = "20";
            String priceArea = "SE2";

            ApiClient.PriceData[] prices = ApiClient.fetchPrices(year, month, day, priceArea);

            for (ApiClient.PriceData price : prices) {
                System.out.printf("Start: %s | End: %s | SEK: %.2f%n",
                        price.time_start(), price.time_end(), price.SEK_per_kWh());
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.err.println("The request was interrupted: " + ie.getMessage());
        } catch (IOException ioe) {
            System.err.println("Network/IO error: " + ioe.getMessage());
        }
    }
}
