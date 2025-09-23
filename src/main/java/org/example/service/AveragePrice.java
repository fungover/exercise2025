package org.example.service;

import org.example.model.PriceData;
import java.util.List;
import org.example.ui.Menu;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.LocalDate;

public class AveragePrice {
    // --------Method to calculate and show average price---------
    public static void showAveragePrice(String userName, String zoneName, String zoneId) {
        try {
            GetPrices service = new GetPrices(zoneId);
            LocalDate todaySE = ZonedDateTime.now(ZoneId.of("Europe/Stockholm")).toLocalDate();
            List<PriceData> prices = service.findPricesForDate(todaySE);
            if (prices.isEmpty()) {
                System.out.println("No price data available.");
                return;
            }
            // ---Calculate the average price--
            double totalPrice = 0.0;
            for (PriceData price : prices) {
                totalPrice += price.getSEK_per_kWh();
            }
            double averagePrice = totalPrice / prices.size();
            // --Display results using Menu--
            Menu.averagePriceMenu(userName, zoneName, zoneId, prices.size(), averagePrice);
        } catch (Exception e) {
            System.out.println("Error calculating average price: " + e.getMessage());
        }
    }
}
