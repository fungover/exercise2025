package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.PriceHour;

import java.net.http.*;
import java.net.URI;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ElectricityPriceService {
    private final HttpClient client = HttpClient.newHttpClient();

    public String fetchTodayRaw(String zoneCode) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM-dd");
        String pathDate = today.format(formatter);

        String url = "https://www.elprisetjustnu.se/api/v1/prices/"
                + pathDate + "_" + zoneCode + ".json";

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .header("User-Agent", "ElpriserCLI/1.0")
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.out.println("API error: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            System.out.println("HTTP request failed: " + e.getMessage());
            return null;
        }
    }

    public List<PriceHour> fetchToday(String zoneCode) {
        String raw = fetchTodayRaw(zoneCode);
        if (raw == null) return List.of();

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(raw, new TypeReference<List<PriceHour>>() {});
        } catch (Exception e) {
            System.out.println("JSON parse error: " + e.getMessage());
            return List.of();
        }
    }
}
