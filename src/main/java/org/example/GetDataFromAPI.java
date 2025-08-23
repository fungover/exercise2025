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
    public static void main(String[] args) {

        try {
            /*System.out.println(fetchDataPrices(LocalDate.of(2025, 8, 20), "SE3"));*/
            String json = fetchDataPrices(LocalDate.of(2025, 8, 20), "SE3");
            ObjectMapper objectMapper = new ObjectMapper();
            List<ElectricityPrice> prices = objectMapper.readValue(
                    json, new TypeReference<List<ElectricityPrice>>() {}
            );
            for (ElectricityPrice price : prices) {
                System.out.println("Time Start: " + price.getTime_end() + ", Price (SEK/kWh): " + price.getEUR_per_kWh());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     *
     * @param date Param to fetch data for a specific date
     * @param zone Param to fetch data for a specific price zone (SE1, SE2, SE3)
     * @return Returns the data as a String
     * @throws IOException          Thrown if an I/O error occurs when sending or receiving
     * @throws InterruptedException Thrown if the operation is interrupted
     */
    static String fetchDataPrices(LocalDate date, String zone) throws IOException, InterruptedException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .timeout(Duration.ofMinutes(2))
                    .uri(URI.create(buildURL(date, zone)))
                    .build();

            HttpResponse<String> response = CLIENT.send(request, BodyHandlers.ofString());

            if (response.statusCode() == 200) return response.body();
            if (response.statusCode() == 404) return null;

            throw new IOException("Unexpected status code: " + response.statusCode());
        } catch (IOException | InterruptedException e) {
            System.err.println("Request failed: " + e.getMessage());
            throw e;
        }
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
