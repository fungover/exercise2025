package org.example;

import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.List;

public class Prices {
    public static void showAllPrices(String area) {
        try {
            // Hämtar dagens datum
            LocalDate today = LocalDate.now();

            // Anropar HttpClient för att hämta alla priser
            List<JsonObject> prices = ElectricityPricesHttpClient.fetchElectricityPrices(
                    today.getYear(),
                    today.getMonthValue(),
                    today.getDayOfMonth(),
                    area
            );

            // Visar info i konsolen
            System.out.println("Hämtade " + prices.size() + " timpriser för zon " + area + ":");
            for (JsonObject obj : prices) {
                String time = obj.get("time_start").getAsString();
                double price = obj.get("SEK_per_kWh").getAsDouble();
                System.out.println(time + " - " + price + " kr/kWh");
            }

        } catch (Exception e) {
            System.out.println("Fel vid hämtning: " + e.getMessage());
        }
    }

    private static void showAveragePrice() {
        System.out.println("Du valde: Visa medelpris för idag");
    }

    private static void showMinAndMaxPrice() {
        System.out.println("Du valde: Visa billigaste och dyraste timmar");
    }

    private static void findBestChargingTime() {
        System.out.println("Du valde: Hitta bästa tid att ladda elbil");

    }
}
