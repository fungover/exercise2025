package org.example;

import java.time.LocalDateTime;
import java.util.List;

public class App {
         /**
     * Entry point that retrieves today's electricity prices for a user-selected region and runs analysis.
     *
     * <p>Constructs a URL using the current date (YYYY, MM, DD) and a region code obtained from
     * Menu.askForRegion(), fetches the day's prices via PriceFetcher, and invokes PriceAnalyzer to
     * compute analyses (including best charging windows for 2, 4 and 8-hour periods).</p>
     *
     * <p>Side effects:
     * - Prints an error and returns immediately if the selected region is invalid (null).
     * - Performs a network fetch to obtain price data and calls analyzer utilities to process results.</p>
     */
    static void main() {
        LocalDateTime now = LocalDateTime.now();

        String sYear = String.valueOf(now.getYear());
        String sMonth = String.format("%02d", now.getMonthValue());
        String sDay = String.format("%02d", now.getDayOfMonth());

        String priceClass = Menu.askForRegion();
        if (priceClass ==null) {
            System.out.println("Invalid region, exiting...");
            return;
        }

        String url = String.format("https://www.elprisetjustnu.se/api/v1/prices/%s/%s-%s_%s.json", sYear, sMonth, sDay, priceClass);

        List<Price> prices = PriceFetcher.fetchPrices(url);
        //Todo Fetch average price and highest and lowest price during 24h period
        PriceAnalyzer.analyze(prices);
        //Todo Finds best window to charge during 24h period
        PriceAnalyzer.findBestChargingWindow(prices,2);
        PriceAnalyzer.findBestChargingWindow(prices,4);
        PriceAnalyzer.findBestChargingWindow(prices,8);
    }
}