package org.example;

import org.example.client.ElprisClient;
import org.example.model.PricePoint;
import org.example.model.PriceZone;
import org.example.service.*;

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

        int[] durations = {2, 4, 8};
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
        System.out.print("Do you want to import consumption data (CSV)? (y/n): ");
        String importCsv = scanner.nextLine().trim().toLowerCase();

        if (importCsv.equals("y")) {
            System.out.print("Enter path to CSV file (press Enter for default 'data/consumption.csv'): ");
            String path = scanner.nextLine().trim();

            if (path.isEmpty()) {
                path = "data/consumption.csv";
            }

            try {
                var consumption = new CsvConsumptionCalculator().readConsumption(path);
                double totalCost = new CsvConsumptionCalculator().calculateTotalCost(prices, consumption);
                System.out.printf("Total cost based on your consumption: %.3f SEK%n", totalCost);
            } catch (Exception e) {
                System.out.println("Failed to read CSV file: " + path);
                System.out.println("Please make sure the file exists and is formatted correctly.");
            }
        }

    }
}

