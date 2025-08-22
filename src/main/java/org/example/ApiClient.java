package org.example;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ApiClient {

    public static PriceData[] fetchPrices(String year, String month, String day, String priceArea)
            throws IOException, InterruptedException {
        String url = String.format(
                "https://www.elprisetjustnu.se/api/v1/prices/%s/%s-%s_%s.json",
                year, month, day, priceArea
        );

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMinutes(1))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(response.body(), PriceData[].class);
    }

    public record PriceData(
            double SEK_per_kWh,
            String time_start,
            String time_end
    ) {
        public String formattedHourRange() {
            ZonedDateTime start = ZonedDateTime.parse(time_start);
            ZonedDateTime end = ZonedDateTime.parse(time_end);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return String.format("kl %s - %s", start.format(formatter), end.format(formatter));
        }
        }
    }

