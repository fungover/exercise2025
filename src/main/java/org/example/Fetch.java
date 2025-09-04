package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.google.gson.Gson;


class Fetch {
    public static PricePerHour[] fetch(LocalDate date, String priceZone) throws IOException, InterruptedException {

        String baseUrl = "https://www.elprisetjustnu.se/api/v1/prices/";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM-dd");
        String dateString = date.format(formatter) + "_" + priceZone + ".json";


        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + dateString))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            System.out.println("\nIngen data tillgänglig för " + date + " i " + priceZone + " (HTTP " + response.statusCode() + ")\n");
            return new PricePerHour[0];
        }


        String body = response.body();

        Gson gson = new Gson();
        if (body.trim().startsWith("[")) {
            return gson.fromJson(response.body(), PricePerHour[].class);
        } else {
            System.out.println("\nIngen data tillgänglig för " + date + " i " + priceZone + "\n");
            return new PricePerHour[0]; // return empty array instead of crashing if there is no available prices.
        }

    }
}