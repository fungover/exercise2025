package org.example;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class HttpFetch {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();


    public static class Price {
        public String time_start;
        public String time_end;
    }

    public static List<Price> fetchPrices(LocalDate date, String zone) throws Exception {
        String url = String.format(
                "https://www.elprisetjustnu.se/api/v1/prices/%d/%02d-%02d_%s.json",
                date.getYear(), date.getMonthValue(), date.getDayOfMonth(), zone
        );

        HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Kunde inte hämta priser: " + response.statusCode());
        }

        Price[] arr = gson.fromJson(response.body(), Price[].class);
        return Arrays.asList(arr);
    }



    public static String fetchJsonForDay(LocalDate date, String zone) {
        try {
            String url = String.format(
                    "https://www.elprisetjustnu.se/api/v1/prices/%d/%02d-%02d_%s.json",
                    date.getYear(), date.getMonthValue(), date.getDayOfMonth(), zone
            );
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                System.out.println("Failed to fetch prices: HTTP " + response.statusCode());
                return null;
            }
            return response.body();
        } catch (Exception e) {
            System.out.println("Failed to fetch prices: " + e.getMessage());
            return null;
        }
    }

    public static PriceEntry[] parseEntries(String json) {
        if (json == null || json.isBlank()) {
            return new PriceEntry[0];
        }
        return gson.fromJson(json, PriceEntry[].class);
    }

}
