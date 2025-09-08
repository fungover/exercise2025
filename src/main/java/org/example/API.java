package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class API {
    public static List<ElectricityPrice> fetchPrices(String zone, String day, String month, String year) throws IOException, InterruptedException {
        int d = Integer.parseInt(day);
        int m = Integer.parseInt(month);
        if (d < 1 || d > 31 || m < 1 || m > 12) {
            throw new IllegalArgumentException("Invalid day/month: " + day + "/" + month);
        }
        String dd = String.format("%02d", d);
        String mm = String.format("%02d", m);
        String zz = zone.trim().toUpperCase(java.util.Locale.ROOT);
        if (!java.util.Set.of("SE1", "SE2", "SE3", "SE4").contains(zz)) {
            throw new IllegalArgumentException("Inbalid zone: " + zone);
        }
        String apiUrl = "https://www.elprisetjustnu.se/api/v1/prices/" + year + "/" + mm + "-" + dd + "_" + zone + ".json";

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(java.time.Duration.ofSeconds(10))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Accept", "application/json")
                .header("User-Agent", "exercise2025-cli/1.0")
                .timeout(java.time.Duration.ofSeconds(10))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), new TypeReference<List<ElectricityPrice>>() {});
        } else if (response.statusCode() >= 500) {
            throw new IOException("Upstream error HTTP " + response.statusCode());
        } else {
            return java.util.List.of();
        }
    }
}