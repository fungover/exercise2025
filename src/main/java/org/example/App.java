package org.example;

import java.io.IOException;
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
         printPricesForDate(today, priceArea, "dagen");

         LocalDate tomorrow = today.plusDays(1);
         try {
             printPricesForDate(tomorrow, priceArea, "morgondagen");
         } catch (Exception e) {
             System.out.printf("%n⚠️ Priser för morgondagen (%s) är ännu inte publicerade.%n", tomorrow);
         }
     }

    private static void printPricesForDate(LocalDate date, String priceArea, String label) {
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

        } catch (IOException ioe) {
            throw new RuntimeException("Kunde inte hämta priser för " + date + ": " + ioe.getMessage());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Avbrutet anrop för " + date, ie);
        }
    }
}
