package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.Duration;


public class HttpFetchers {

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public String fetchPricesForDay(LocalDate date, String zone) {
        try {
            PriceRequests request = new PriceRequests(
                    date.getYear(),
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    zone
            );

            String url = request.buildUrl();

            var httpRequest = HttpRequest.newBuilder(URI.create(url)).GET().build();
            var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) return response.body();
            if (response.statusCode() == 404) return null;

            throw new IllegalStateException("HTTP " + response.statusCode() + " for " + url);

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("API request interrupted", ie);
        } catch (java.io.IOException ioe) {
            throw new java.io.UncheckedIOException("API request failed", ioe);
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            throw new RuntimeException("API request failed", e);
        }
    }
}

