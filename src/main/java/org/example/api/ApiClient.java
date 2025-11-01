package org.example.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.example.utils.JsonMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;

public class ApiClient {

    private final ObjectMapper objectMapper = JsonMapper.get(); // Get a configured ObjectMapper instance from JsonMapper utility class.
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();


    public String getPrices(String zone) throws java.io.IOException, InterruptedException {

        LocalDate swedenDate =  LocalDate.now(ZoneId.of("Europe/Stockholm")); // Get the current date in Sweden timezone

        String todayJson = getSingleDay(swedenDate, zone); // Fetch today's prices
        String tomorrowJson = getSingleDay(swedenDate.plusDays(1), zone); // Fetch tomorrow's prices

        return combineJsonArrays(todayJson, tomorrowJson); // Combine both days' prices into a single JSON array
    }

    private String getSingleDay(LocalDate date, String zone) throws java.io.IOException, InterruptedException { // Fetch prices for a single day
        if (zone == null || !zone.matches("SE[1-4]")) {
            throw new IllegalArgumentException("Invalid zone: " + zone + ". Valid zones are SE1, SE2, SE3, SE4."); // Validate the zone format (must be SE1, SE2, SE3, or SE4).
        }
        String url = String.format(
                "https://www.elprisetjustnu.se/api/v1/prices/%d/%02d-%02d_%s.json",
                date.getYear(), date.getMonthValue(), date.getDayOfMonth(), zone //Construct URL based on year, month, day and selected zone. (example SE1).
        );

        HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(15))
                .GET()
                .build();

        HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() == 404) return "[]"; // If the response status is 404 (Not Found), return an empty JSON array.
        if (resp.statusCode() != 200) {
            String body = resp.body();
            String snippet = body == null ? "" : body.substring(0, Math.min(body.length(), 512));
            throw new RuntimeException("HTTP " + resp.statusCode() + " for GET " + url + " - body: " + snippet); // If the response status is not 200 (OK), throw an exception with details.
        }

        return resp.body(); // Return the response body (JSON string).
    }

    private String combineJsonArrays(String jsonToday, String jsonTomorrow) { //Method to combine two JSON arrays into one.
        try {

            String todayTrimmed = jsonToday == null ? "[]" : jsonToday.trim(); // Trim whitespace and handle null case for today's JSON.
            String tomorrowTrimmed = jsonTomorrow == null ? "[]" : jsonTomorrow.trim(); // Trim whitespace and handle null case for tomorrow's JSON.

            ArrayNode combinedArray = objectMapper.createArrayNode(); // Create a new empty JSON array.

            var todayNode = objectMapper.readTree(todayTrimmed); // Parse today's JSON string into a JsonNode.
            if (todayNode != null && !todayNode.isNull()) { // Check if the node is not null.
                if (!todayNode.isArray()) throw new IllegalArgumentException("Expected array for todays payload"); // Validate that the parsed node is an array.
                combinedArray.addAll((ArrayNode)  todayNode); // Add all elements from today's array to the combined array.
            }

            var tomorrowNode = objectMapper.readTree(tomorrowTrimmed); // Parse tomorrow's JSON string into a JsonNode.
            if (tomorrowNode != null && !tomorrowNode.isNull()) { // Check if the node is not null.
                if (!tomorrowNode.isArray()) throw new IllegalArgumentException("Expected array for tomorrows payload"); // Validate that the parsed node is an array.
                combinedArray.addAll((ArrayNode) tomorrowNode); // Add all elements from tomorrow's array to the combined array.
            }

            return objectMapper.writeValueAsString(combinedArray); // Convert the combined array back to a JSON string and return it.

        } catch (Exception e) {
            throw new RuntimeException("Failed to merge JSON arrays: " + e.getMessage(), e); // Throw a runtime exception if merging fails.
        }
    }
}