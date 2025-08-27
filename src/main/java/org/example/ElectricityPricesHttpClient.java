package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ElectricityPricesHttpClient {

    public static List<JsonObject> fetchElectricityPrices(int year, int month, int day, String priceArea) throws Exception {
        String url = String.format(
                "https://www.elprisetjustnu.se/api/v1/prices/%04d/%02d-%02d_%s.json",
                year, month, day, priceArea
        );

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP error: " + response.statusCode());
        }

        JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
        List<JsonObject> result = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            result.add(jsonArray.get(i).getAsJsonObject());
        }
        return result;
    }
}
