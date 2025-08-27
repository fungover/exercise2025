package org.example.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZoneId;

public class ApiClient {

    private final ObjectMapper objectMapper = new ObjectMapper();


    public String getPrices(LocalDate startDate, String zone) throws Exception {

        LocalDate swedenDate =  LocalDate.now(ZoneId.of("Europe/Stockholm")); // Get current date in Sweden timezone

        String todayJson = getSingleDay(swedenDate, zone); // Fetch today's prices
        String tomorrowJson = getSingleDay(swedenDate.plusDays(1), zone); // Fetch tomorrow's prices

        return combineJsonArrays(todayJson, tomorrowJson); // Combine both days' prices into a single JSON array
    }

    private String getSingleDay(LocalDate date, String zone) throws Exception { // Fetch prices for a single day
        String url = String.format(
                "https://www.elprisetjustnu.se/api/v1/prices/%d/%02d-%02d_%s.json",
                date.getYear(), date.getMonthValue(), date.getDayOfMonth(), zone //Construct URL based on year, month, day and selected zone. (example SE1).
        );

        HttpClient client = HttpClient.newHttpClient(); // Create a new HttpClient instance.
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build(); // Build the HTTP GET request.
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString()); // Send the request and get the response as a String.

        if (resp.statusCode() == 404) return "[]"; // If the response status is 404 (Not Found), return an empty JSON array.
        if (resp.statusCode() != 200) throw new RuntimeException("HTTP " + resp.statusCode()); // If the status is not 200 (OK), throw an exception.

        return resp.body(); // Return the response body (JSON string).
    }

    private String combineJsonArrays(String jsonToday, String jsonTomorrow) { //Method to combine two JSON arrays into one.
        try {

            String todayTrimmed = jsonToday == null ? "[]" : jsonToday.trim(); // Trim whitespace and handle null case for today's JSON.
            String tomorrowTrimmed = jsonTomorrow == null ? "[]" : jsonTomorrow.trim(); // Trim whitespace and handle null case for tomorrow's JSON.

            ArrayNode combinedArray = objectMapper.createArrayNode(); // Create a new empty JSON array.

            if (!"[]".equals(todayTrimmed)) {
                ArrayNode todayArray = (ArrayNode) objectMapper.readTree(todayTrimmed); // Parse today's JSON string into a JSON array.
                combinedArray.addAll(todayArray);
            }
            if (!"[]".equals(tomorrowTrimmed)) {
                ArrayNode tomorrowArray = (ArrayNode) objectMapper.readTree(tomorrowTrimmed); // Parse tomorrow's JSON string into a JSON array.
                combinedArray.addAll(tomorrowArray);
            }
            return objectMapper.writeValueAsString(combinedArray); // Convert the combined JSON array back to a JSON string and return it.

        } catch (Exception e) {
            throw new RuntimeException("Failed to merge JSON arrays: " + e.getMessage(), e); // Throw a runtime exception if merging fails.
        }
    }
}