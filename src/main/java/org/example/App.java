package org.example;

import org.example.cli.Args;
import org.example.client.ElprisClient;
import org.example.model.PricePoint;
import org.example.model.PriceZone;
import org.example.service.PriceService;
import org.example.service.PriceServiceImpl;
import org.example.service.StatsCalculator;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which price zone do you want? (SE1, SE2, SE3, SE4):");

        PriceZone zone = null;
        while (zone == null) {
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                zone = PriceZone.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid zone. Please enter SE1, SE2, SE3, or SE4:");
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
    }
}
