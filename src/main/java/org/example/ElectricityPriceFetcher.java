package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ElectricityPriceFetcher {
    private static final ZoneId zoneId = ZoneId.of("Europe/Stockholm");
    private static LocalDate today = LocalDate.now(zoneId);
    private static LocalDate tomorrow = today.plusDays(1);
    private final static HttpClient httpClient = HttpClient.newHttpClient();
    private final static ObjectMapper mapper = new ObjectMapper();

    private static String buildUrl(LocalDate date, String zone) {
        // Use %02d for minimum 2 in width adds 0 before if needed.
        return String.format("https://www.elprisetjustnu.se/api/v1/prices/%d/%02d-%02d_%s.json",
                date.getYear(), date.getMonthValue(), date.getDayOfMonth(), zone);
    }

    public static List<PriceEntry> fetchPrices(LocalDate date, String zone) throws IOException, InterruptedException {
        String url = buildUrl(date, zone);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            return List.of();
        }

        return mapper.readValue(response.body(), new TypeReference<List<PriceEntry>>() {});
    }

    public static List<PriceEntry> getTodayAndTomorrow(String zone) throws IOException, InterruptedException {
        List<PriceEntry> combined = new ArrayList<>();
        combined.addAll(fetchPrices(today, zone));
        combined.addAll(fetchPrices(tomorrow, zone));

        return combined;
    }

    public static void downloadTodayAndTomorrow(String zone) throws IOException, InterruptedException {
        List<PriceEntry> combined = getTodayAndTomorrow(zone);
        Path output = Path.of("output", "prices_" + zone + ".csv");

        if (output.getParent() != null) {
            Files.createDirectories(output.getParent());
        }

        try (var w = Files.newBufferedWriter(output)) {
            w.write("time_start;time_end;SEK_per_kWh;EUR_per_kWh;EXR");
            w.newLine();

            for (PriceEntry p : combined) {
                w.write(p.time_start() + ";" + p.time_end() + ";" + p.SEK_per_kWh() + ";" + p.EUR_per_kWh() + ";" + p.EXR());
                w.newLine();
            }
        }
    }

}
