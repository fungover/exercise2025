package org.example.service;

import org.example.model.PriceData;
import org.example.ui.Menu;
import java.util.List;

public class AllHourlyPrices {

    /**
     * Fetches hourly price data for a zone and displays it via the menu.
     *
     * Fetches prices for the given zone identifier and, if any data is returned,
     * delegates rendering to Menu.allHourlyPricesMenu(userName, zoneName, prices).
     * If no prices are available, prints a brief message. Any exception raised
     * during retrieval is caught and printed; the method does not rethrow.
     *
     * @param userName human-readable name of the current user (used by the menu)
     * @param zoneName human-readable name of the zone (used by the menu)
     * @param zoneId   identifier of the zone to fetch prices for
     */
    public static void showAllHourlyPrices(String userName, String zoneName, String zoneId) {
        try {
            GetPrices service = new GetPrices(zoneId);
            List<PriceData> prices = service.findAllPrices();

            if (prices.isEmpty()) {
                System.out.println("No price data available.");
                return;
            }

            //--Display results using Menu--
            Menu.allHourlyPricesMenu(userName, zoneName, prices);

        } catch (Exception e) {
            System.out.println("❌ Error fetching hourly prices: " + e.getMessage());
        }
    }
}
