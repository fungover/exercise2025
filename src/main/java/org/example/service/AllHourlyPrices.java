package org.example.service;

import org.example.model.PriceData;
import org.example.ui.Menu;
import java.util.List;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.LocalDate;

public class AllHourlyPrices {

    public static void showAllHourlyPrices(String userName, String zoneName, String zoneId) {
        try {
            GetPrices service = new GetPrices(zoneId);
            LocalDate todaySE = ZonedDateTime.now(ZoneId.of("Europe/Stockholm")).toLocalDate();
            List<PriceData> prices = service.findPricesForDate(todaySE);

            if (prices.isEmpty()) {
                System.out.println("No price data available.");
                return;
            }

            //--Display results using Menu--
            Menu.allHourlyPricesMenu(userName, zoneName, prices);

        } catch (Exception e) {
            System.out.println("Error fetching hourly prices: " + e.getMessage());
        }
    }
}
