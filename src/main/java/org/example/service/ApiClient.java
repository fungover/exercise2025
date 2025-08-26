package org.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {
    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);

    public static HttpResponse<String> getPrices(String url_string) {
        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url_string))
                    .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            logger.error("Prices could not be fetched from {}", url_string, e);
        }
        return response;
    }

    public static String urlBuilder(String date, String zone) {
        String year = String.valueOf(java.time.LocalDate.now().getYear());
        return "https://www.elprisetjustnu.se/api/v1/prices/" + year + "/" + date + "_" + zone + ".json";
    }
}
