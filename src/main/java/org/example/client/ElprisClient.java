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

public class ElprisClient {
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(java.time.Duration.ofSeconds(10))
            .build();
    private final Gson gson = new Gson();

    public List <PricePoint> fetchDayPrices(PriceZone zone, LocalDate date) {

        String url = String.format(
                "https://www.elprisetjustnu.se/api/v1/prices/%d/%02d-%02d_%s.json",
                date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth(),
                zone.name()
        );

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(java.time.Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(java.nio.charset.StandardCharsets.UTF_8));
            if (response.statusCode() / 100 != 2) {
                throw new RuntimeException("Non-2xx status " + response.statusCode() + " for " + url);
                }

            var listType = new TypeToken<List<PriceDto>>() {}.getType();
            List<PriceDto> dtos = gson.fromJson(response.body(), listType);

            return (dtos == null ? java.util.List.<PriceDto>of() : dtos).stream()
                    .map(dto -> {
                        OffsetDateTime odt = OffsetDateTime.parse(dto.timeStart());
                        return new PricePoint(
                                odt,
                                dto.sekPerKWh()
                        );
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch prices", e);
        }
    }

}
