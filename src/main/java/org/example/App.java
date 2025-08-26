package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class App {
    public static void main(String[] args) {
        try {
            JsonArray prices = ElectricityPricesHttpClient.fetchElectricityPrices(2025, 8, 26, "SE3");
            for (JsonElement element : prices) {
                JsonObject obj = element.getAsJsonObject();
                System.out.println("Time: " + obj.get("time_start").getAsString() +
                        " – Price: " + obj.get("SEK_per_kWh").getAsDouble() + " SEK/kWh");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
