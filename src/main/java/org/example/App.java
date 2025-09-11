package org.example;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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


        // Datum i svensk tidzone (idag och imorgon)
        ZoneId sweden = ZoneId.of("Europe/Stockholm");
        LocalDate today = LocalDate.now(sweden);
        LocalDate tomorrow = today.plusDays(1);

        // Bygg API-URL: /api/v1/prices/{år}/{MM-dd}/_{ZON}.json
        String urlToday = buildUrl(today, zone);
        String urlTomorrow = buildUrl(tomorrow, zone);

        // Hämtar dagens priser som alltid finns därmed använder strikt metod som kastar fel om status !200
        List<PricePoint> pricesToday = PriceFetcher.fetchPrices(urlToday);

        // Hämtar morgondagens priser som kan saknas därmed använder snäll metod som returnerar tom lista vid 404
        List<PricePoint> pricesTomorrow = PriceFetcher.fetchPrices(urlTomorrow);

        // Medelpris, billigast, dyrast pris kr/kWh baserat på dagens priser
        double avgPrice = PriceUtils.averagePrice(pricesToday);
        PricePoint cheapestPrice = PriceUtils.cheapestPrice(pricesToday);
        PricePoint mostExpensivePrice = PriceUtils.mostExpensivePrice(pricesToday);

        System.out.println("Medelpris: " + String.format("%.2f", avgPrice) + "kr/kWh");
        System.out.println("Billigaste timme: " + cheapestPrice.getHour() + " ("
                + String.format("%.2f", cheapestPrice.getPrice()) + " kr/kWh)");
        System.out.println("Dyraste timme: " + mostExpensivePrice.getHour() + " ("
                + String.format("%.2f", mostExpensivePrice.getPrice()) + "kr/kWh)");

        System.out.println();

        // Slår ihop dagens och morgondagens priser för fönster-beräkniongar
        List<PricePoint> prices = new ArrayList<>();
        prices.addAll(pricesToday);
        prices.addAll(pricesTomorrow);

        // Slidingwindows 2h/4h/8h
        System.out.println("Optimalt laddningsintervall idag eller imorgon: ");
        int best2hStart = PriceUtils.findBestWindowStart(prices, 2);
        System.out.println(PriceUtils.windowSummary(prices, best2hStart, 2));

        int best4hStart = PriceUtils.findBestWindowStart(prices, 4);
        System.out.println(PriceUtils.windowSummary(prices, best4hStart, 4));

        int best8hStart = PriceUtils.findBestWindowStart(prices, 8);
        System.out.println(PriceUtils.windowSummary(prices, best8hStart, 8));
    }

    // Bygger rätt API-url för ett visst datum och zon
    private static String buildUrl(LocalDate date, String zone) {
        String year = String.valueOf(date.getYear());
        String mmdd = date.format(DateTimeFormatter.ofPattern("MM-dd"));
        return "https://www.elprisetjustnu.se/api/v1/prices/" + year + "/" + mmdd + "_" + zone + ".json";
    }
}
