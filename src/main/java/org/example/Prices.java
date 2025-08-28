package org.example;

import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.List;

public class Prices {
    public static void showAllPrices(List<PriceEntry> prices) {
        if (prices == null || prices.isEmpty()) {
            System.out.println("Inga priser tillgängliga.");
            return;
        }

        System.out.println("Alla priser:");
        for (PriceEntry entry : prices) {
            System.out.println(entry.startTime() + " - " + entry.pricePerKWh() + " kr/kWh");
        }
    }

    public static void showAveragePrice(List<PriceEntry> prices) {
        // If there aren't any prices available, show a message to the user
        if (prices == null || prices.isEmpty()) {
            System.out.println("Inga priser tillgängliga.");
            return;
        }
     // Add all prices in list and count meanprice
        double sum = 0;
        for (PriceEntry entry : prices) {
            sum += entry.pricePerKWh();
        }

        double average = sum / prices.size();
        System.out.printf("Medelpris: %.2f kr/kWh%n", average);
    }

    private static void showMinAndMaxPrice() {
        System.out.println("Du valde: Visa billigaste och dyraste timmar");
    }

    private static void findBestChargingTime() {
        System.out.println("Du valde: Hitta bästa tid att ladda elbil");

    }
}
