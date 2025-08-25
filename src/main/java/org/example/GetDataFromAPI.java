package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDate;
import java.time.Duration;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class GetDataFromAPI {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    /**
     *
     * @param date Param to fetch data for a specific date
     * @param zone Param to fetch data for a specific price zone (SE1, SE2, SE3)
     * @return Returns the data as a String
     */
    static String fetchDataPrices(LocalDate date, String zone) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .timeout(Duration.ofMinutes(2))
                    .uri(URI.create(buildURL(date, zone)))
                    .build();

            HttpResponse<String> response = CLIENT.send(request, BodyHandlers.ofString());

            return response.statusCode() == 200 ? response.body() : null;
        } catch (IOException | InterruptedException e) {
            System.err.println("Request failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Convert JSON data to a list of ElectricityPrice objects
     *
     * @param json The JSON data as a String
     * @return Returns a list of ElectricityPrice objects
     * @throws IOException if the JSON data cannot be parsed
     */
    static List<ElectricityPrice> ConvertDataToObjects(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ElectricityPrice> prices = objectMapper.readValue(
                json, new TypeReference<List<ElectricityPrice>>() {
                }
        );
        return prices;
    }

    /**
     * Build the URL for the API request
     *
     * @param date Param to fetch data for a specific date
     * @param zone Param to fetch data for a specific price zone (SE1, SE2, SE3)
     * @return Returns the URL as a String
     */
    public static String buildURL(LocalDate date, String zone) {
        String year = String.valueOf(date.getYear());
        String monthDay = date.format(java.time.format.DateTimeFormatter.ofPattern("MM-dd"));
        return "https://www.elprisetjustnu.se/api/v1/prices/" + year + "/" + monthDay + "_" + zone + ".json";
    }
}
