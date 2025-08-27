package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.time.*;

public class Price {
    private final DecimalFormat dateFormat = new DecimalFormat("00");
    private final LocalDate today = LocalDate.now();
    private final String year = String.valueOf(Year.now().getValue());
    private final String month = dateFormat.format(Month.from(today).getValue());
    private final String day = dateFormat.format(MonthDay.now().getDayOfMonth());

    String uri = "https://www.elprisetjustnu.se/api/v1/prices/"
                + year + "/" + month + "-" + day + "_SE3.json";

    HttpClient client =  HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .header("Content-Type", "application/json")
            .build();


    public Price() throws IOException, InterruptedException {
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String payload = response.body();
        System.out.println(payload);
    }
}
