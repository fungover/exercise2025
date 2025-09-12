package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PriceFetcher {

    // Gemensam HTTP-klient som återanvänds för alla anrop.
    // Timeout skyddar mot att programmet fastnar om servern inte svarar.
    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    // Gemensam ObjectMapper som återanvänds för att tolka JSON.
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // Hämtar priser från en URL och returnerar en lista av PricePoint.
    // Kastar Exception om status inte är 200 (bra för "idag" som ska finnas).
    public static List<PricePoint> fetchPrices(String url) throws Exception {
        // Bygger ett GET-anrop till API:t
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Skickar anropet med den gemensamma klienten och får svaret som text (JSON-format)
        HttpResponse<String> response = CLIENT.send(
                request,
                HttpResponse.BodyHandlers.ofString(java.nio.charset.StandardCharsets.UTF_8)
        );

        // Om svaret är OK (200) → tolka JSON som en lista av PricePoint
        if (response.statusCode() == 200) {
            return MAPPER.readValue(response.body(), new TypeReference<List<PricePoint>>() {});
        } else {
            // Annars kasta fel (t.ex. 500 eller 404 för "idag", vilket inte ska hända)
            throw new Exception("Kunde inte hämta priser, statuskod: " + response.statusCode());
        }
    }

    // Variant av fetchPrices som istället returnerar tom lista om 404 inträffar.
    // Detta används för t.ex. "imorgon" där priser ibland inte finns ännu.
    public static List<PricePoint> fetchPricesOrEmpty(String url) {
        try {
            // Bygger ett GET-anrop till API:t
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // Skickar anropet med den gemensamma klienten
            HttpResponse<String> response = CLIENT.send(
                    request,
                    HttpResponse.BodyHandlers.ofString(java.nio.charset.StandardCharsets.UTF_8)
            );

            // Om morgondagens priser inte finns ännu → returnera tom lista
            if (response.statusCode() == 404) {
                return new ArrayList<>();
            }

            // Om något annat fel än 200 eller 404 → kasta fel
            if (response.statusCode() != 200) {
                throw new RuntimeException("Fel vid hämtning, statuskod: " + response.statusCode());
            }

            // Om status är OK (200) → tolka JSON som en lista av PricePoint
            return MAPPER.readValue(response.body(), new TypeReference<List<PricePoint>>() {});
        } catch (Exception e) {
            // Om något annat går fel (t.ex. nätverksproblem) → kasta fel vidare
            throw new RuntimeException("Fel vid hämtning", e);
        }
    }
}