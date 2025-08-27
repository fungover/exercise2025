package org.example;

import java.time.LocalDate;

public class App {
    static void main(String[] args) {
         System.out.println("Välkommen!");
         System.out.println("Här kan du se elpriser per timme för olika områden i Sverige.");
         System.out.println("\nTillgängliga priszoner:");
         System.out.println("SE1 = Luleå / Norra Sverige");
         System.out.println("SE2 = Sundsvall / Norra Mellansverige");
         System.out.println("SE3 = Stockholm / Södra Mellansverige");
         System.out.println("SE4 = Malmö / Södra Sverige");

         String priceArea = UserInput.getValidatedPriceArea();

         LocalDate today = LocalDate.now();

        ApiClient.ElectricityPrice[] todayPrices = null;
        try {
            PriceDisplay.printPricesForDate(today, priceArea, "dagen");
            todayPrices = fetchPricesForDate(today, priceArea);
        } catch (Exception e) {
            System.out.println("Kunde inte hämta dagens priser: " + e.getMessage());
            return;
        }

        LocalDate tomorrow = today.plusDays(1);
        ApiClient.ElectricityPrice[] tomorrowPrices = null;
        try {
            PriceDisplay.printPricesForDate(tomorrow, priceArea, "morgondagen");
            tomorrowPrices = fetchPricesForDate(tomorrow, priceArea);
        } catch (Exception e) {
            System.out.printf("%nPriser för morgondagen (%s) är ännu inte publicerade.%n", tomorrow);
        }

        PriceDisplay.printStatisticsAndBestChargingTimes(todayPrices, tomorrowPrices);
    }

    private static ApiClient.ElectricityPrice[] fetchPricesForDate(LocalDate date, String priceArea)
            throws Exception {
        String year = String.valueOf(date.getYear());
        String month = String.format("%02d", date.getMonthValue());
        String day = String.format("%02d", date.getDayOfMonth());

        return ApiClient.fetchPrices(year, month, day, priceArea);
    }
}
