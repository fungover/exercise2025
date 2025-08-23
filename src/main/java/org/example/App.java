package org.example;

import org.example.cli.Args;
import org.example.client.ElprisClient;
import org.example.model.PriceZone;

// Will wire everything together.
// Read arguments such as zone, cvs, and date,
// call services to fetch prices from the API,
// run calculators and prints results to the console.
public class App {
    public static void main(String[] args) {
        PriceZone zone = Args.parseZone(args);

        ElprisClient client = new ElprisClient();
        client.fetchTodayPrices(zone).forEach(System.out::println);

        System.out.println("The startup is working!");
    }
}
