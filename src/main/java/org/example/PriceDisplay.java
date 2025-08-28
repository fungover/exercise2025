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
            printPricesForDate(date, priceArea, label, prices);
        } catch (IOException ioe) {
            System.out.println("Kunde inte hämta priser för " + priceArea + " (" + label + " " + date + "): " + ioe.getMessage());
            return;
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.out.println("Avbrutet anrop för " + priceArea + " (" + label + " " + date + "). Försök igen senare.");
            return;
        }

    public static void printPricesForDate(LocalDate date, String priceArea, String label, ApiClient.ElectricityPrice[] prices) {
        String year = String.valueOf(date.getYear());
        String month = String.format("%02d", date.getMonthValue());
        String day = String.format("%02d", date.getDayOfMonth());

        System.out.printf("%nElpriser för %s (%s den %s-%s-%s):%n", priceArea, label, year, month, day);

        for (ApiClient.ElectricityPrice price : prices) {
            System.out.printf("%s | Pris: %.2f öre/kWh%n", price.formattedHourRange(), price.SEK_per_kWh() * 100);
        }
    }

    public static void printStatisticsAndBestChargingTimes(
            ApiClient.ElectricityPrice[] todayPrices,
            ApiClient.ElectricityPrice[] tomorrowPrices) {

        if (todayPrices != null && todayPrices.length > 0) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("\nPrisstatistik för idag:");
            System.out.printf("Medelpris: %.2f öre/kWh%n", PriceCalculator.calculateAveragePrice(List.of(todayPrices)) * 100);

            ApiClient.ElectricityPrice minPrice = PriceCalculator.findMinPrice(List.of(todayPrices));
            System.out.printf("Lägsta pris: %.2f öre/kWh (%s)%n", minPrice.SEK_per_kWh() * 100, minPrice.formattedHourRange());

            ApiClient.ElectricityPrice maxPrice = PriceCalculator.findMaxPrice(List.of(todayPrices));
            System.out.printf("Högsta pris: %.2f öre/kWh (%s)%n", maxPrice.SEK_per_kWh() * 100, maxPrice.formattedHourRange());
        }

        if (tomorrowPrices != null && tomorrowPrices.length > 0) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("\nPrisstatistik för morgondagen:");
            System.out.printf("Medelpris: %.2f öre/kWh%n", PriceCalculator.calculateAveragePrice(List.of(tomorrowPrices)) * 100);

            ApiClient.ElectricityPrice minPrice = PriceCalculator.findMinPrice(List.of(tomorrowPrices));
            System.out.printf("Lägsta pris: %.2f öre/kWh (%s)%n", minPrice.SEK_per_kWh() * 100, minPrice.formattedHourRange());

            ApiClient.ElectricityPrice maxPrice = PriceCalculator.findMaxPrice(List.of(tomorrowPrices));
            System.out.printf("Högsta pris: %.2f öre/kWh (%s)%n", maxPrice.SEK_per_kWh() * 100, maxPrice.formattedHourRange());
        }

        List<ApiClient.ElectricityPrice> allPrices = PriceCalculator.combineAllAvailablePrices(todayPrices, tomorrowPrices);

        if (!allPrices.isEmpty()) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("\nBästa laddspann för elbil:");

            int[] chargingDurations = {2, 4, 8};

            for (int duration : chargingDurations) {
                PriceCalculator.ChargingWindow bestWindow =
                        PriceCalculator.findBestChargingWindow(allPrices, duration);

                if (bestWindow != null) {
                    System.out.printf("%d timmar: %s (%.2f öre/kWh i snitt)%n",
                            duration,
                            bestWindow.getFormattedTimeRange(),
                            bestWindow.averagePrice() * 100);
                } else {
                    System.out.printf("\n%d timmar: Inte tillräckligt med data tillgänglig%n", duration);
                }
            }

            System.out.println("\nTips baserat på: " + (tomorrowPrices != null ? "dagens och morgondagens" : "endast dagens") + " priser");
        }
    }
}