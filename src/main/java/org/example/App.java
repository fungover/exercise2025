package org.example;

import java.time.LocalDateTime;
import java.util.List;

public class App {
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