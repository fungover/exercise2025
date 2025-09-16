package org.example.service;

import org.example.model.PriceData;
import java.util.List;
import org.example.ui.Menu;

public class AveragePrice {
    /**
     * Fetches price data for a zone, computes the average SEK/kWh, and displays it in the menu.
     *
     * Attempts to retrieve all PriceData for the provided zoneId, returns early with a console
     * message if no data is available, and delegates successful results to Menu.averagePriceMenu.
     * Any exceptions encountered are caught and reported to the console.
     *
     * @param userName the name of the current user shown in the result menu
     * @param zoneName the human-readable name of the pricing zone shown in the result menu
     * @param zoneId   the identifier of the pricing zone used to fetch price data
     */
    public static void showAveragePrice(String userName, String zoneName, String zoneId) {
        try {
            GetPrices service = new GetPrices(zoneId);
            List<PriceData> prices = service.findAllPrices();
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
