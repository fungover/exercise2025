package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        // Lista med zoner som är tillåtna, defaultzon SE1
        String[] validZones = {"SE1", "SE2", "SE3", "SE4"};
        String zone = "SE1";

        // Om användaren skickat in argument, använd det istället
        if (args.length > 0) {
            zone = args[0].toUpperCase();
        }

        // Kontroller att zonen är giltig, annars återställ till SE1
        boolean isValid = false;
        for (String z : validZones) {
            if (z.equals(zone)) {
                isValid = true;
                break;
            }
        }

        if (!isValid) {
            System.out.println("Ogiltig zon: " + zone + ". Använder SE1.");
        }

        System.out.println("Zon: " + zone);


        // Skapar dagens datum i formatet MM-dd
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd"));
        String year = String.valueOf(LocalDate.now().getYear());

        // Bygg upp rätt URL till API:t
        String url = "https://www.elprisetjustnu.se/api/v1/prices/" + year + "/" + today + "_" + zone + ".json";

        // Hämta riktiga priser från API:t
        List<PricePoint> prices = PriceFetcher.fetchPrices(url);

        // Medelpris, billigast, dyrast pris kr/kWh
        double avgPrice = PriceUtils.averagePrice(prices);
        PricePoint cheapestPrice = PriceUtils.cheapestPrice(prices);
        PricePoint mostExpensivePrice = PriceUtils.mostExpensivePrice(prices);

        System.out.println("Medelpris: " + String.format("%.2f", avgPrice) + "kr/kWh");
        System.out.println("Billigaste timme: " + cheapestPrice.getHour() + " ("
                + String.format("%.2f", cheapestPrice.getPrice()) + " kr/kWh)");
        System.out.println("Dyraste timme: " + mostExpensivePrice.getHour() + " ("
                + String.format("%.2f", mostExpensivePrice.getPrice()) + "kr/kWh)");

        System.out.println();

        // Slidingwindows 2h/4h/8h
        int best2hStart = PriceUtils.findBestWindowStart(prices, 2);
        System.out.println(PriceUtils.windowSummary(prices, best2hStart, 2));

        int best4hStart = PriceUtils.findBestWindowStart(prices, 4);
        System.out.println(PriceUtils.windowSummary(prices, best4hStart, 4));

        int best8hStart = PriceUtils.findBestWindowStart(prices, 8);
        System.out.println(PriceUtils.windowSummary(prices, best8hStart, 8));
    }
}
