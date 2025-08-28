/**
 * Service for fetching hourly electricity prices from elprisetjustnu.se API.
 * Given a price zone and date, it builds the API URL, send HTTP request,
 * parses the JSON response and return a list of Elpris objects.
 */

package org.example.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import org.example.model.Elpris;

import java.time.LocalDate;
import java.util.*;

public class ElprisFetch {

    public List<Elpris> getPrice(String zon, LocalDate datum) {
        String url = String.format(
                "https://www.elprisetjustnu.se/api/v1/prices/%d/%02d-%02d_%s.json",
                datum.getYear(),
                datum.getMonthValue(),
                datum.getDayOfMonth(),
                zon
        );

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body().trim();

            if (!json.startsWith("[")) {
                System.out.println("Ingen data för " + datum + " (" + zon + ")");
                return Collections.emptyList();
            }

            Gson gson = new Gson();
            Elpris[] prices = gson.fromJson(response.body(), Elpris[].class);

            return Arrays.asList(prices);
        } catch (Exception e) {
            System.out.println("Error when fetching: " + e.getMessage());
            return Collections.emptyList();

        }
    }
}
