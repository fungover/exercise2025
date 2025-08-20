package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class App {
     static void main(String[] args) {
        try {
            String year = "2025";
            String month = "08";
            String day = "20";
            String priceArea = "SE2";

            String url = String.format(
                    "https://www.elprisetjustnu.se/api/v1/prices/%s/%s-%s_%s.json",
                    year, month, day, priceArea
            );
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.err.println("The request was interrupted: " + ie.getMessage());
        } catch (IOException ioe) {
            System.err.println("Network/IO error: " + ioe.getMessage());
        }
    }
    }
