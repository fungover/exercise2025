package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {

    public static String fetchPrices(String year, String month, String day, String priceArea)
            throws IOException, InterruptedException {
        String url = String.format(
                "https://www.elprisetjustnu.se/api/v1/prices/%s/%s-%s_%s.json",
                year, month, day, priceArea
        );

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
