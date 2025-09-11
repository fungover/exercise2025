package org.example;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {

        // Argumenthantering
        // args[0] = elområde (SE1, SE2, SE3, SE4)
        // args[1] = (valfritt) sökväg till CSV-fil med förbrukningsdata

        // Zonval
        String[] validZones = {"SE1", "SE2", "SE3", "SE4"};
        String zone = "SE1"; // default

        if (args.length >= 1) {
            zone = args[0].toUpperCase();
        }

        // Kontrollerar att zonen är giltig, annars återställ till SE1
        boolean isValid = false;
        for (String z : validZones) {
            if (z.equals(zone)) {
                isValid = true;
                break;
            }
        }

        if (!isValid) {
            System.out.println("Ogiltig zon: " + zone + ". Använder SE1.");
            zone = "SE1";
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
        List<PricePoint> pricesTomorrow = PriceFetcher.fetchPricesOrEmpty(urlTomorrow);

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

        // Om användaren skickar in ett andra argument -> CSV fil med konsumption
        if (args.length >= 2) {
            String csvPath = args[1];
            calculateTotalCostFromCsv(zone, csvPath);
        }
    }

    // Bygger rätt API-url för ett visst datum och zon
    private static String buildUrl(LocalDate date, String zone) {
        String year = String.valueOf(date.getYear());
        String mmdd = date.format(DateTimeFormatter.ofPattern("MM-dd"));
        return "https://www.elprisetjustnu.se/api/v1/prices/" + year + "/" + mmdd + "_" + zone + ".json";
    }

    private static void calculateTotalCostFromCsv(String zone, String csvPath) {
        try {
            // Läser in förbrukningsdata från CSV
            List<ConsumptionPoint> consumptionData = CsvConsumptionReader.read(csvPath);

            // Om filen är tom -> avsluta direkt
            if (consumptionData.isEmpty()) {
                System.out.println("CSV-filen var tom eller ogiltig.");
                return;
            }

            // Hämta ut alla unika datum som finns i CSV-filen
            // För att se vilka dagar som behöver hämtas elpriser för från API:t
            Set<LocalDate> csvDates = new HashSet<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            for (ConsumptionPoint c : consumptionData) {
                LocalDate date = LocalDate.parse(c.getTime(), formatter);
                csvDates.add(date);
            }

            // Hämta priser för varje datum och samla i en lista
            List<PricePoint> pricePointsForCsvDates = new ArrayList<>();
            for (LocalDate date : csvDates) {
                // Hämta en dags priser för rätt zon
                List<PricePoint> oneDay = PriceFetcher.fetchPrices(buildUrl(date, zone));
                pricePointsForCsvDates.addAll(oneDay);
            }

            // Bygger en "uppslagskarta" (Map) där man kan hitta priset för varje timme
            // Nyckeln blir "yyyy-MM-dd HH:mm" och värdet priset i kr/kWh
            double totalCost = getTotalCost(pricePointsForCsvDates, consumptionData);

            // Skriv bara ut slutresultatet
            System.out.println();
            System.out.println("Kostnadsberäkning från CSV");
            System.out.println("Total elkostnad för perioden i CSV-filen: "
                    + String.format("%.2f", totalCost) + "kr");

        } catch (Exception e) {
            System.err.println("Fel vid CSV-baserad kostnadsberäkning: " + e.getMessage());
        }
    }

    // Räknar ut den totala elkostnaden för en lista av förbrukningspunkter
    // genom att matcha varje timmes förbrukning mot timpriser i en uppslagskarta.
    private static double getTotalCost(List<PricePoint> pricePointsForCsvDates, List<ConsumptionPoint> consumptionData) {
        Map<String, Double> priceByHour = new HashMap<>();
        for (PricePoint p : pricePointsForCsvDates) {
            String key = p.getDate() + " " + p.getHour(); // ex. "2025-08-01 00:00"
            priceByHour.put(key, p.getPrice());
        }

        // Beräkna total kostnad genom att gå igenom varje timme i konsumtionsfilen
        double totalCost = 0.0;
        for (ConsumptionPoint c : consumptionData) {
            Double price = priceByHour.get(c.getTime()); // matcha CSV-tid mot API-tid
            if (price != null) {
                totalCost += price * c.getConsumption(); // pris * förbrukning
            }
        }
        return totalCost;
    }
}
