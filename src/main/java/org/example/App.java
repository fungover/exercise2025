package org.example;

import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        ElprisApiClient apiClient = new ElprisApiClient();
        ElprisParser parser = new ElprisParser();

        // Hämta rådata från API
        String json = apiClient.fetchRaw(2025, "08-27", "SE3");

        // Gör om JSON ➝ Price-objekt
        List<Price> prices = parser.parsePrices(json);

        // Skriv ut info
        System.out.println("Antal timmar: " + prices.size());
        System.out.println("Första timmen: " + prices.getFirst());
        System.out.println("Sista timmen:  " + prices.getLast());

        // Medelpris
        double mean = PriceStats.mean(prices);
        System.out.printf("Medelpris (24h): %.4f SEK/kWh%n", mean);

        // Billigaste & dyraste
        Price cheapest = PriceStats.cheapest(prices);
        Price expensive = PriceStats.mostExpensive(prices);
        System.out.println("Billigaste timmen:  " + cheapest);
        System.out.println("Dyraste timmen:    " + expensive);
    }
}
