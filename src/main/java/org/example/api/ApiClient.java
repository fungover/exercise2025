package org.example.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

public class ApiClient {

    /**
     * Get electricity prices for today and tomorrow (if available)
     */
    public String getPrices(LocalDate startDate, String zone) throws Exception {
        String todayJson = getSingleDay(startDate, zone);
        String tomorrowJson = getSingleDay(startDate.plusDays(1), zone);

        return combineJsonArrays(todayJson, tomorrowJson);
    }

    private String getSingleDay(LocalDate date, String zone) throws Exception {
        String url = String.format(
                "https://www.elprisetjustnu.se/api/v1/prices/%d/%02d-%02d_%s.json",
                date.getYear(), date.getMonthValue(), date.getDayOfMonth(), zone
        );

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() == 404) return "[]";
        if (resp.statusCode() != 200) throw new RuntimeException("HTTP " + resp.statusCode());

        return resp.body();
    }

    private String combineJsonArrays(String jsonToday, String jsonTomorrow) {
        if (jsonToday.equals("[]") && jsonTomorrow.equals("[]")) {
            return "[]";
        }
        if (jsonToday.equals("[]")) {
            return jsonTomorrow;
        }
        if (jsonTomorrow.equals("[]")) {
            return jsonToday;
        }

        return jsonToday.substring(0, jsonToday.length() - 1) + "," + jsonTomorrow.substring(1);
    }
}