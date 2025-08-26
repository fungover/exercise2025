/* HTTP responsibility, the class that talks to the API */
package org.example.electricity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ElPriceCli {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .findAndRegisterModules();
    private static final HttpClient HTTP = HttpClient.newHttpClient();

    public static List<PriceData> fetchDay(LocalDate date, String zone) {
        try {
            String url = "Https://www.elprisetjustnu.se/api/v1/prices/%d/%02d-%02d_%s.json"
                    .formatted(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), zone.toUpperCase());

            HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();
            HttpResponse<String> res = HTTP.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() == 404) return List.of(); //Nothing published
            if (res.statusCode() != 200) throw new RuntimeException("API-error" + res.statusCode());

            PriceData[] arr = MAPPER.readValue(res.body(), PriceData[].class);
            return Arrays.asList(arr);
        } catch (Exception e) {
            throw new RuntimeException("Could not fetch prices" + e.getMessage(), e);
        }
    }

    //Utility class
    private ElPriceCli() {
    }
}