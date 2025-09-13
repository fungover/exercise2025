package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FetchPrice {
    private final FetchDates dates = new FetchDates();
    private final String year = dates.getYear();
    private final String month = dates.getMonth();
    private final String today = dates.getToday();
    private final String tomorrowDay = dates.getTomorrowDay();
    private final String tomorrowMonth = dates.getTomorrowMonth();
    private final String zone;

    public FetchPrice(String zone) {
        this.zone = zone;
    }

    private String fetchPrice(String day, String month) {
        String payload;
            String uri = createUri(day, month);
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            payload = response.body();
        } catch (IOException | InterruptedException e) {
            payload = e.getMessage();
            System.out.println(payload);
        }
        return payload;
    }

    private String createUri(String day, String month) {
        return "https://www.elprisetjustnu.se/api/v1/prices/" + year +
                "/" + month + "-" + day + "_SE" + zone + ".json";
    }

    public String fetchTodayPrices() {
       return fetchPrice(today, month);
    }

    public String fetchTomorrowPrices() {
        return fetchPrice(tomorrowDay, tomorrowMonth);
    }
}
