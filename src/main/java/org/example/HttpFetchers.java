package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

public class HttpFetchers {

    private final HttpClient client = HttpClient.newHttpClient();

    public String fetchPricesForDay(LocalDate date, String zone) {
        try {
            PriceRequests request = new PriceRequests(
                    date.getYear(),
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    zone
            );

            String url = request.buildUrl();

            var httpRequest = HttpRequest.newBuilder(URI.create(url)).GET().build();
            var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) return response.body();
            if (response.statusCode() == 404) return null;

            throw new IllegalStateException("HTTP " + response.statusCode() + " for " + url);

        } catch (Exception e) {
            throw new RuntimeException("API request failed: " + e.getMessage(), e);
        }
    }
}
