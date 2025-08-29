package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ElprisApiClient {
    private static final String BASE = "https://www.elprisetjustnu.se/api/v1/prices";
    private HttpClient client = HttpClient.newHttpClient();

    public String fetchRaw(int year, String monthDay, String zone) throws IOException, InterruptedException {
        String url = BASE + "/" + year + "/" + monthDay + "_" + zone + ".json";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Fel vid hämtning: " + response.statusCode());
        }
        return response.body();
    }
}
