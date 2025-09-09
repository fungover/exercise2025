package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        // Välj zon (SE1/SE2/SE3/SE4) som argument nr 1
        java.util.Set<String> VALID_ZONES = java.util.Set.of("SE1", "SE2", "SE3", "SE4");
        String zone = (args.length > 0) ? args[0].toUpperCase() : "SE1";
        if (!VALID_ZONES.contains(zone)) {
            System.err.println("Ogiltig zon '" + zone + "'. Tillåtna zoner: " + VALID_ZONES + ". Faller tillbaka till SE1.");
            zone = "SE1";
        }
        // Välj CSV som argument nr 2 (tex: SE1 lulea-energi-homeadress-consumption.csv)
        String csvPath;
        if (args.length > 1) {
            csvPath = args[1];
        } else {
            csvPath = null;
        }

        System.out.println("Vald zon: " + zone);

        // Skapa HttpClient en gång
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(java.time.Duration.ofSeconds(10))
                .build();
        // CSV-läge - Hämtar historiska priser för datumen i CSV och räknar total kostnad
        if (csvPath != null) {
            System.out.println("CSV angiven: " + csvPath);
            calculateTotalCostFromCsv(client, zone, csvPath);
            return;
        }

        // Standardläge - Kör baskrav (idag och imorgon) - Datum i svensk tidzon
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Stockholm"));
        LocalDate tomorrow = today.plusDays(1);

        // Hämta dagens priser och ev morgondagens priser om dessa finns tillgängliga
        List<PricePoint> pricesToday = fetchPrices(client, today, zone);
        List<PricePoint> pricesTomorrow = fetchPrices(client, tomorrow, zone);

        // Slå ihop allt i en lista
        List<PricePoint> allPrices = new ArrayList<>();
        allPrices.addAll(pricesToday);
        allPrices.addAll(pricesTomorrow);

        // framtida priser från nu och framåt
        List<PricePoint> futurePrices = PriceUtils.filterFuturePrices(allPrices);

        // Räkna på dagens 24h-period
        if (!pricesToday.isEmpty()) {
            double avg = PriceUtils.averagePrice(pricesToday);
            PricePoint cheapest = PriceUtils.cheapestPrice(pricesToday);
            PricePoint mostExpensive = PriceUtils.mostExpensivePrice(pricesToday);

            System.out.println("Medelpris (idag): " + PriceUtils.formatPrice(avg) + " kr/kWh");
            System.out.println("Billigast timme: "
                    + PriceUtils.formatDateTime(cheapest) + " "
                    + PriceUtils.formatPrice(cheapest.price()) + " kr/kWh");
            System.out.println("Dyrast timme: "
                    + PriceUtils.formatDateTime(mostExpensive) + " "
                    + PriceUtils.formatPrice(mostExpensive.price()) + " kr/kWh");
        } else {
            System.out.println("Kunde inte hämta priser för idag.");
        }

        // Sanity check hur många priser vi hämtat
        System.out.println("Totalt antal priser hämtat: " + allPrices.size());

        // Sliding Window – bästa 2h, 4h, 8h fönster
        int start2 = PriceUtils.findBestWindowStart(futurePrices, 2);
        PriceUtils.printWindowSummary(futurePrices, start2, 2);

        int start4 = PriceUtils.findBestWindowStart(futurePrices, 4);
        PriceUtils.printWindowSummary(futurePrices, start4, 4);

        int start8 = PriceUtils.findBestWindowStart(futurePrices, 8);
        PriceUtils.printWindowSummary(futurePrices, start8, 8);
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();

    // Hjälpmetod för att hämta priser för en viss dag
    private static List<PricePoint> fetchPrices(HttpClient client, LocalDate date, String zone) throws Exception {
        String year = String.valueOf(date.getYear());
        String monthDay = date.format(java.time.format.DateTimeFormatter.ofPattern("MM-dd"));

        String url = "https://www.elprisetjustnu.se/api/v1/prices/"
                + year + "/" + monthDay + "_" + zone + ".json";

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .header("Accept", "application/json")
                .timeout(java.time.Duration.ofSeconds(10))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return MAPPER.readValue(response.body(), new TypeReference<List<PricePoint>>() {});
        }
        System.err.println("Kunde inte hämta priser (" + response.statusCode() + "): " + url);
        return List.of();
    }

    // Läser en CSV-fil med elförbrukning och
    // hämtar priser för de datumen och räknar ut total kostnad (pris * kWh per timme).
    private static void calculateTotalCostFromCsv(HttpClient client, String zone, String csvPath) {
        try {
            // Läs in förbrukning från CSV
            List<ConsumptionPoint> consumption = CsvConsumptionReader.readConsumption(csvPath);

            // Samla unika datum från CSV så vi vet vilka dagar vi måste hämta priser för
            java.util.Set<java.time.LocalDate> csvDates = new java.util.HashSet<>();
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            for (ConsumptionPoint c : consumption) {
                LocalDate date = LocalDate.parse(c.time(), formatter);
                csvDates.add(date);
            }

            // Hämta priser för varje datum och samla i en lista
            List<PricePoint> pricePointsForCsvDates = new ArrayList<>();
            for (java.time.LocalDate date : csvDates) {
                List<PricePoint> oneDay = fetchPrices(client, date, zone);
                pricePointsForCsvDates.addAll(oneDay);
            }

            // Bygg uppslag enligt "yyyy-MM-dd HH:mm" -> pris kr/kWh
            java.util.Map<String, Double> priceByHour = new java.util.HashMap<>();
            for (PricePoint p : pricePointsForCsvDates) {
                String key = PriceUtils.formatDateTime(p);
                priceByHour.put(key, p.price());
            }

            // Summera kostnad för varje CSV rad som har pris
            double totalCost = 0;
            for (ConsumptionPoint c : consumption) {
                Double price = priceByHour.get(c.time());
                if (price != null) {
                    totalCost += price * c.consumption();
                }
            }

            System.out.printf("Total kostnad för CSV-perioden: %.2fkr%n", totalCost);
        } catch (Exception e) {
            System.err.println("Fel vid CSV baserad kostnadsberäkning: " + e.getMessage());
        }
    }
}
