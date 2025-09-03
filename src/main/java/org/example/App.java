package org.example;

import java.util.Scanner;
import java.util.List;
import java.io.IOException;

public class App {
    static void main() {
        String zone = zoneSelection();
        System.out.println("Selected zone: " + zone);

        API api = new API();

        try {
            List<ElectricityPrice> prices = api.fetchPrices(zone);
            System.out.println("API response:");
            for (ElectricityPrice price : prices) {
                System.out.println(price.time_start + " " + price.time_end + " — " + price.SEK_per_kWh + "(SEK/kWh) - " + price.EUR_per_kWh + "(EUR/kWh) - " + price.EXR);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error fetching prices: " + e.getMessage());
        }
    }

    public static String zoneSelection() {
        System.out.println("Welcome! Please select what zone you would like to see the electrical prices in.");
        System.out.println("(1) - Luleå / Norra Sverige");
        System.out.println("(2) - Sundsvall / Norra Mellansverige");
        System.out.println("(3) - Stockholm / Södra Mellansverige");
        System.out.println("(4) - Malmö / Södra Sverige");
        System.out.println("Please select what zone you would like to see with the corresponding number");

        Scanner scanner = new Scanner(System.in);
        String selection = scanner.nextLine();

        return "SE" + selection;
    }
}
