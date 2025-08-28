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

import static java.net.http.HttpClient.newHttpClient;

public class API {
    private static final String BASE = "https://www.elprisetjustnu.se/api/v1/prices";
    // HTTP client for making requests
    private final HttpClient http = newHttpClient();
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
                .header("Accept", "application/json")
                .GET()
                .build();
    // Send the request and get the response
        HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() != 200) return List.of();
    // Parse the JSON response into a list of PriceEntry objects
        return mapper.readValue(res.body(), new TypeReference<>() {
        });

    }

}

