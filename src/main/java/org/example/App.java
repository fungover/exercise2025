package org.example;

import org.example.client.ElprisClient;
import org.example.model.PricePoint;
import org.example.model.PriceZone;
import org.example.service.ChargingPlanner;
import org.example.service.PriceService;
import org.example.service.PriceServiceImpl;
import org.example.service.StatsCalculator;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which price zone do you want? (SE1 / Luleå / Norra Sverige, SE2 / Sundsvall / Norra Mellansverige, SE3 / Stockholm / Södra Mellansverige, SE4 / Malmö / Södra Sverige):");

        PriceZone zone = null;
        while (zone == null) {
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                zone = PriceZone.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid zone. Please enter SE1 / Luleå / Norra Sverige, SE2 / Sundsvall / Norra Mellansverige, SE3 / Stockholm / Södra Mellansverige, SE4 / Malmö / Södra Sverige:");
            }
        }

        ElprisClient client = new ElprisClient();
        PriceService service = new PriceServiceImpl(client);
        StatsCalculator stats = new StatsCalculator();

        List<PricePoint> prices = service.getAvailablePrices(zone);

        if (prices.isEmpty()) {
            System.out.println("No prices available");
            return;
        }

        double mean = StatsCalculator.mean(prices);
        PricePoint cheapest = StatsCalculator.min(prices);
        PricePoint mostExpensive = StatsCalculator.max(prices);

        System.out.printf("Mean price: %.3f SEK/kWh%n", mean);
        System.out.printf("Cheapest: %s → %.3f SEK/kWh%n",
                cheapest.start(), cheapest.price());
        System.out.printf("Most expensive: %s → %.3f SEK/kWh%n",
                mostExpensive.start(), mostExpensive.price());

        int [] durations = {2, 4, 8};
        System.out.println("\nBest charging windows:");
        for (int hours : durations) {
            var window = ChargingPlanner.findBestWindow(prices, hours);
            if (window != null) {
                System.out.printf("%d hours: %s → %s (Total: %.3f SEK)%n",
                        hours,
                        window.getStart(),
                        window.getEnd(),
                        window.getTotalPrice());
            } else {
                System.out.printf("%d hours: Not enough data available%n", hours);
            }
            }
        }
    }
