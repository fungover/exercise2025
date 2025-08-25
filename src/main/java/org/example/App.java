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

        // Välj zon (SE1/SE2/SE3/SE4)
        String zone = "SE1";

        // Skapa HttpClient en gång
        HttpClient client = HttpClient.newHttpClient();

        // Datum i svensk tidzon
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Stockholm"));
        LocalDate tomorrow = today.plusDays(1);

        // Hämta dagens priser
        List<PricePoint> pricesToday = fetchPrices(client, today, zone);

        // Försök hämta morgondagens priser
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

        // Sanity check hur många priser vi fick
        System.out.println("Totalt antal priser hämtat: " + allPrices.size());

        // Sliding Window – bästa 2h, 4h, 8h fönster
        int start2 = PriceUtils.findBestWindowStart(futurePrices, 2);
        PriceUtils.printWindowSummary(futurePrices, start2, 2);

        int start4 = PriceUtils.findBestWindowStart(futurePrices, 4);
        PriceUtils.printWindowSummary(futurePrices, start4, 4);

        int start8 = PriceUtils.findBestWindowStart(futurePrices, 8);
        PriceUtils.printWindowSummary(futurePrices, start8, 8);
    }

    // Hjälpmetod för att hämta priser för en viss dag
    private static List<PricePoint> fetchPrices(HttpClient client, LocalDate date, String zone) throws Exception {
        String year = String.valueOf(date.getYear());
        String monthDay = date.format(java.time.format.DateTimeFormatter.ofPattern("MM-dd"));

        String url = "https://www.elprisetjustnu.se/api/v1/prices/"
                + year + "/" + monthDay + "_" + zone + ".json";

        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), new TypeReference<List<PricePoint>>() {});
        } else {
            return List.of();
        }
    }
}
