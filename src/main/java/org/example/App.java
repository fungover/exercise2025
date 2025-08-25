package org.example;

import org.example.cli.Args;
import org.example.client.ElprisClient;
import org.example.model.PricePoint;
import org.example.model.PriceZone;
import org.example.service.PriceService;
import org.example.service.PriceServiceImpl;
import org.example.service.StatsCalculator;

import java.util.List;

// Will wire everything together.
// Read arguments such as zone, cvs, and date,
// call services to fetch prices from the API,
// run calculators and prints results to the console.
public class App {
    public static void main(String[] args) {
        PriceZone zone = PriceZone.SE3;

        PriceService service = new PriceServiceImpl(new ElprisClient());
        List<PricePoint> prices = service.getTodayPrices(zone);

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
