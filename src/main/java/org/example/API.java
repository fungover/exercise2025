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
    public List<ElectricityPrice> fetchPrices(String zone, String day, String month, String year) throws IOException, InterruptedException {
        String dd = day.length() == 1 ? "0" + day : day;
        String mm = month.length() == 1 ? "0" + month : month;
        String zz = zone.toUpperCase();
        String apiUrl = "https://www.elprisetjustnu.se/api/v1/prices/" + year + "/" + mm + "-" + dd + "_" + zz + ".json";

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(java.time.Duration.ofSeconds(10))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Accept", "application/json")
                .timeout(java.time.Duration.ofSeconds(10))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), new TypeReference<List<ElectricityPrice>>() {});
        } else {
            return java.util.List.of();
        }
    }
}