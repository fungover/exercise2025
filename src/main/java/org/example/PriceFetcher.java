package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

// Hjälpklass för att hämta priser från API:t
public class PriceFetcher {

    // Hämtar priser från en URL och returnerar en lista av PricePoint.
    // Kastar Exception om status inte är 200 (bra för "idag" som ska finnas).
    public static List<PricePoint> fetchPrices(String url) throws Exception {
        // Skapar en HTTP-klient som kan prata med webben
        HttpClient client = HttpClient.newHttpClient();

        // Bygger ett GET-anrop till API:t
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Skickar anropet och får svaret tillbaka som textsträng (JSON-format)
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Om svaret är OK (200)
        if (response.statusCode() == 200) {
            // Skapar en ObjectMapper som kan konvertera JSON till Java-objekt
            ObjectMapper mapper = new ObjectMapper();

            // JSON-texten tolkas och görs om till en lista av PricePoint-objekt
            return mapper.readValue(response.body(), new TypeReference<>() {});
        } else {
            throw new Exception("Kunde inte hämta priser, statuskod: " + response.statusCode());
        }
    }

    // Ny: snäll variant – returnerar tom lista om API:t svarar 404 (imorgon saknas ofta).
    public static List<PricePoint> fetchPricesOrEmpty(String url) {
        try {
            return fetchPrices(url); // Försök först den strikta metoden
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message != null && message.contains("statuskod: 404")) {
                return new ArrayList<>(); // Imorgon saknas → inga priser ännu
            }
            // Annat fel -> skicka vidare så vi ser vad som hände
            throw new RuntimeException("Fel vid hämtning: " + message, ex);
        }
    }
}
