package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ElprisApiClient {
    private static final String BASE = "https://www.elprisetjustnu.se/api/v1/prices";

    private final HttpClient client = HttpClient.newHttpClient();

    /**
     * Fetches raw JSON for given date and zone.
     */
    public String fetchRaw(int year, String monthDay, String zone) throws IOException, InterruptedException {
        String url = String.format("%s/%d/%s_%s.json", BASE, year, monthDay, zone);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Failed to fetch: " + response.statusCode());
        }
        return response.body();
    }
}
