package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZoneId;

public class ElectricityPriceFetcher {
    private final ZoneId zoneId = ZoneId.of("Europe/Stockholm");
    private LocalDate today = LocalDate.now(zoneId);
    private LocalDate tomorrow = today.plusDays(1);
    private final HttpClient httpClient = HttpClient.newHttpClient();

    private String buildUrl(LocalDate date, String zone) {
        // Use %02d for minimum 2 in width adds 0 before if needed.
        return String.format("https://www.elprisetjustnu.se/api/v1/prices/%d/%02d-%02d_%s.json",
                date.getYear(), date.getMonthValue(), date.getDayOfMonth(), zone);
    }

    public void fetchPrices(LocalDate date, String zone) throws IOException, InterruptedException {
        String url = buildUrl(date, zone);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println(response.body());
        }

    }

    public void getTodayAndTomorrow(String zone) throws IOException, InterruptedException {
        fetchPrices(today, zone);
        fetchPrices(tomorrow, zone);
    }

}
