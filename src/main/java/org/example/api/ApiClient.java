package org.example.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

public class ApiClient {

    /**
     * Get electricity prices for today and tomorrow (if available)
     * This gets called only if the user choose a zone (SE1 - SE4) in ZoneSelection.
     */
    public String getPrices(LocalDate startDate, String zone) throws Exception {
        String todayJson = getSingleDay(startDate, zone); // Fetch today's prices
        String tomorrowJson = getSingleDay(startDate.plusDays(1), zone); // Fetch tomorrow's prices

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
        if (jsonToday.equals("[]") && jsonTomorrow.equals("[]")) { // If both arrays are empty, return an empty array.
            return "[]";
        }
        if (jsonToday.equals("[]")) { // If only today's array is empty, return tomorrow's array.
            return jsonTomorrow;
        }
        if (jsonTomorrow.equals("[]")) { // If only tomorrow's array is empty, return today's array.
            return jsonToday;
        }

        return jsonToday.substring(0, jsonToday.length() - 1) + "," + jsonTomorrow.substring(1); // Combine the two arrays by removing the closing bracket from the first and the opening bracket from the second, and joining them with a comma.
    }
}