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
import java.time.Duration;
import java.util.Locale;


import java.time.LocalDate;
import java.util.*;


public class ElprisFetch {

private static final HttpClient HTTP = HttpClient.newBuilder().build();
private static final Gson GSON = new Gson();

    public List<Elpris> getPrice(String zon, LocalDate datum) {
        final String zone = zon == null ? "" : zon.trim().toUpperCase(Locale.ROOT);
        String url = String.format(
                "https://www.elprisetjustnu.se/api/v1/prices/%d/%02d-%02d_%s.json",
                datum.getYear(),
                datum.getMonthValue(),
                datum.getDayOfMonth(),
                zon
        );

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .header("User-Agent", "exercise2025/1.0 (+https://github.com/fungover/exercise2025)")
                    .build();

            HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 404) {
                // no prices for tomorrow
                System.out.printf("Inga priser publicerade ännu för %s (%s). Hoppar över.%n", datum, zone);
                return Collections.emptyList();
            }
                if (response.statusCode() != 200) {
                System.err.printf("HTTP %d for %s%n", response.statusCode(), url);
                return Collections.emptyList();


            }
            String json = response.body().trim();

            if (!json.startsWith("[")) {
                System.out.println("Ingen data för " + datum + " (" + zon + "): " + url);
                return Collections.emptyList();
            }


            Elpris[] prices = GSON.fromJson(json, Elpris[].class);

            return Arrays.asList(prices);
        } catch (Exception e) {
            System.out.println("Error when fetching " + url + e.getMessage());
            return Collections.emptyList();

        }
    }
}
