package org.SpinalGlitter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class API {
    private static final String BASE = "https://www.elprisetjustnu.se/api/v1/prices";
    // HTTP client for making requests
    private final HttpClient http = HttpClient.newBuilder().connectTimeout(java.time.Duration.ofSeconds(10)).build();
    // JSON object mapper for parsing responses
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    // Fetch price entries for a specific day and zone
    public List<PriceEntry> fetchDay(LocalDate date, String zone) throws IOException, InterruptedException {
        // Construct the URL for the API request
        String url = String.format("%s/%d/%s_%s.json",
                BASE, date.getYear(),
                date.format(DateTimeFormatter.ofPattern("MM-dd")),
                zone);

        // Build the HTTP GET request
        HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                .timeout(java.time.Duration.ofSeconds(15))
                .header("Accept", "application/json")
                .header("User-Agent", "exercise2025-cli/1.0 (+https://github.com/fungover/exercise2025)")
                .GET()
                .build();
        // Send the request and get the response
        HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() == 404) {
            return List.of();
        } else if (res.statusCode() != 200) {;
            throw new IOException("Unexpected status " + res.statusCode() + " from API");
        }

        // Parse the JSON response into a list of PriceEntry objects
        return mapper.readValue(res.body(), new TypeReference<>() {
        });

    }

}

