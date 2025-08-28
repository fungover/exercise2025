package org.example;

import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        ElprisApiClient apiClient = new ElprisApiClient();
        ElprisParser parser = new ElprisParser();

        String json = apiClient.fetchRaw(2025, "08-27", "SE3");
        List<Price> prices = parser.parsePrices(json);

        System.out.println("Antal timmar: " + prices.size());
        System.out.println("Första timmen: " + prices.getFirst());
        System.out.println("Sista timmen:  " + prices.getLast());

        double mean = PriceStats.mean(prices);
        System.out.printf("Medelpris (24h): %.4f SEK/kWh%n", mean);

        Price cheapest = PriceStats.cheapest(prices);
        Price expensive = PriceStats.mostExpensive(prices);
        System.out.println("Billigaste timmen:  " + cheapest);
        System.out.println("Dyraste timmen:    " + expensive);

        // Sliding windows
        PriceStats.bestWindow(prices, 2);
        PriceStats.bestWindow(prices, 4);
        PriceStats.bestWindow(prices, 8);
    }
}
