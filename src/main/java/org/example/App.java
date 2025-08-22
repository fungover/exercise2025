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

         String priceArea;
         if (System.console() != null) {
             priceArea = System.console().readLine("\nAnge önskad zon (SE1, SE2, SE3 eller SE4): ");
         } else {
             System.out.println("\nIngen console tillgänglig (IDE?), använder default: SE3");
             priceArea = "SE3";
         }

         priceArea = priceArea.trim().toUpperCase();

         LocalDate today = LocalDate.now();
         String year = String.valueOf(today.getYear());
         String month = String.format("%02d", today.getMonthValue());
         String day = String.format("%02d", today.getDayOfMonth());

         try {
             ApiClient.PriceData[] prices = ApiClient.fetchPrices(year, month, day, priceArea);

             System.out.printf("%nElpriser för %s den %s-%s-%s:%n", priceArea, year, month, day);
             for (ApiClient.PriceData price : prices) {
                 System.out.printf("%s | Pris: %.2f öre/kWh%n", price.formattedHourRange(), price.SEK_per_kWh() * 100);
             }


         } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.err.println("The request was interrupted: " + ie.getMessage());
        } catch (IOException ioe) {
            System.err.println("Network/IO error: " + ioe.getMessage());
        }
    }
}
