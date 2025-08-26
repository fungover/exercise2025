package org.example;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class PriceDisplay {

    public static void printPricesForDate(LocalDate date, String priceArea, String label) {
        String year = String.valueOf(date.getYear());
        String month = String.format("%02d", date.getMonthValue());
        String day = String.format("%02d", date.getDayOfMonth());

        try {
            ApiClient.ElectricityPrice[] prices = ApiClient.fetchPrices(year, month, day, priceArea);

            System.out.printf("%nElpriser för %s (%s den %s-%s-%s):%n",
                    priceArea, label, year, month, day);
            

            for (ApiClient.ElectricityPrice price : prices) {
                System.out.printf("%s | Pris: %.2f öre/kWh%n",
                        price.formattedHourRange(),
                        price.SEK_per_kWh() * 100);
            }
            

            System.out.println("\nPrisstatistik:");
            System.out.printf("Medelpris: %.2f öre/kWh%n", PriceCalculator.calculateAveragePrice(List.of(prices)) * 100);
            
            ApiClient.ElectricityPrice minPrice = PriceCalculator.findMinPrice(prices);
            System.out.printf("Lägsta pris: %.2f öre/kWh (%s)%n", 
                    minPrice.SEK_per_kWh() * 100, minPrice.formattedHourRange());
            
            ApiClient.ElectricityPrice maxPrice = PriceCalculator.findMaxPrice(prices);
            System.out.printf("Högsta pris: %.2f öre/kWh (%s)%n", 
                    maxPrice.SEK_per_kWh() * 100, maxPrice.formattedHourRange());

        } catch (IOException ioe) {
            throw new RuntimeException("Kunde inte hämta priser för " + date + ": " + ioe.getMessage());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Avbrutet anrop för " + date, ie);
        }
    }
}