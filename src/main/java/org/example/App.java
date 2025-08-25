package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class App {
    static void main(String[] args) throws Exception {

        //Välj zon (SE1/SE2/SE3/SE4)
        String zone = "SE4";

        // Datum i svensk tidzon.
        LocalDate date = LocalDate.now(ZoneId.of("Europe/Stockholm"));
        String year = date.format(DateTimeFormatter.ofPattern("yyyy"));
        String monthDay = date.format(DateTimeFormatter.ofPattern("MM-dd"));


        // Bygg API-url
        String url = "https://www.elprisetjustnu.se/api/v1/prices/"
                + year + "/" + monthDay + "_" + zone + ".json";


        // Skicka HTTP-request och hämta svaret som JSON
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Kunde inte hämta data (HTTP " + response.statusCode() + ")");
            System.out.println(response.body());
            return;
        }

        // Konvertera JSON till lista av PricePoint
        ObjectMapper objectMapper = new ObjectMapper();
        List<PricePoint> prices = objectMapper.readValue(response.body(), new TypeReference<List<PricePoint>>() {});

        // Prel utskrift för att se att hätmningen fungerar
        System.out.println("Antal priser: " + prices.size() + "  Datum: " + date + "  Zon: " + zone);

        int rowsToShow = prices.size();
        if (rowsToShow > 4) {
            rowsToShow = 4;
        }

        for (int i = 0; i < rowsToShow; i++) {
            PricePoint p = prices.get(i);
            System.out.println(p.time() + " till " + + p.price() + "kr/kWh");
        }
    }
}
