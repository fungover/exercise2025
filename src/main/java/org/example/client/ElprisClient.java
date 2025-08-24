package org.example.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.PricePoint;
import org.example.model.PriceZone;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

// HTTP calls -> DayPrices. Will talk to the API.
public class ElprisClient {
    private final HttpClient client =  HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List <PricePoint> fetchTodayPrices(PriceZone zone) {
        LocalDate today = LocalDate.now();

        String url = String.format(
                "https://www.elprisetjustnu.se/api/v1/prices/%d/%02d-%02d_%s.json",
                today.getYear(),
                today.getMonthValue(),
                today.getDayOfMonth(),
                zone.name()
        );

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            var listType = new TypeToken<List<PriceDto>>() {}.getType();
            List<PriceDto> dtos = gson.fromJson(response.body(), listType);

            return dtos.stream()
                    .map(dto -> {
                        OffsetDateTime odt = OffsetDateTime.parse(dto.time_start());
                        return new PricePoint(odt.toLocalDateTime(), dto.SEK_per_kWh());
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch prices", e);
        }
    }
}
