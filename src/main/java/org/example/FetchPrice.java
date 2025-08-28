package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;

public class FetchPrice {
    private final DecimalFormat dFormat = new DecimalFormat("00");
    private final LocalDate presentDate = LocalDate.now();
    private final String year = String.valueOf(Year.now().getValue());
    private final String month = dFormat.format(Month.from(presentDate).getValue());
    private final String today = dFormat.format(MonthDay.now().getDayOfMonth());
    private final String tomorrow = dFormat.format(MonthDay.of(
            Integer.parseInt(month), Integer.parseInt(today) + 1).getDayOfMonth()
    );

    private String fetchPrice(String day) {
        String payload;
            String uri = createUri(day);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Content-Type", "application/json")
                    .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            payload = response.body();
        } catch (IOException | InterruptedException e) {
            payload = e.getMessage();
            System.out.println(payload);
        }
        return payload;
    }

    private String createUri(String day) {
        return "https://www.elprisetjustnu.se/api/v1/prices/" + year +
                "/" + month + "-" + day + "_SE3.json";
    }

    public String getTodayPrices() {
       return fetchPrice(today);
    }

    public String getTomorrowPrices() {
        return fetchPrice(tomorrow);
    }
}
