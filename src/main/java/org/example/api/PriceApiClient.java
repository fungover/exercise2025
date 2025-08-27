package org.example.api;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

public final class PriceApiClient {

    private final HttpClient httpClient = HttpClient.newHttpClient();

    /** Builds the API URL for a given date and price zone. */
    public URI buildUrl(LocalDate date, String zone) {
        String url = String.format(
                "https://www.elprisetjustnu.se/api/v1/prices/%d/%02d-%02d_%s.json",
                date.getYear(), date.getMonthValue(), date.getDayOfMonth(), zone
        );
        return URI.create(url);
    }

    /** Sends the HTTP request and returns the JSON response as a String. */
    public String fetchPrices(LocalDate date, String zone) throws IOException, InterruptedException {
        URI url = buildUrl(date, zone);
        HttpRequest request = HttpRequest.newBuilder(url).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        if (status == 404) {
            return "[]";
        }
        if (status != 200) {
            throw new IOException("Request failed with HTTP status code: " + status);
        }

        return response.body();
    }
}
