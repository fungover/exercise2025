package org.example.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;

public record PriceApiClient(HttpClient httpClient) {
    public PriceApiClient() {
        this(HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build());
    }

    /**
     * Sends the HTTP request and returns the JSON response as a String.
     */
    public String fetchPrices(LocalDate date, String zone) throws IOException, InterruptedException {
        URI url = buildUrl(date, zone);
        HttpRequest request = requestBuilder(url).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        if (status == 404) {
            return "[]";
        }
        if (status != 200) {
            throw new IOException("Request failed with HTTP status code: " + status);
        }

        return response.body();
    }

    /**
     * Shared builder so every request gets a timeout and Accept header.
     */
    private static HttpRequest.Builder requestBuilder(URI uri) {
        return HttpRequest.newBuilder(uri)
                .timeout(Duration.ofSeconds(10))
                .header("Accept", "application/json");
    }

    /**
     * Builds the API URL for a given date and price zone.
     */
    private URI buildUrl(LocalDate date, String zone) {
        String url = String.format(
                "https://www.elprisetjustnu.se/api/v1/prices/%d/%02d-%02d_%s.json",
                date.getYear(), date.getMonthValue(), date.getDayOfMonth(), zone
        );
        return URI.create(url);
    }
}
