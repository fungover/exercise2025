package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class PriceFetcher {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Fetches a JSON array of prices from the given URL and returns it as a List of Price objects.
     *
     * <p>The method performs an HTTP GET request to the specified endpoint (Accept: application/json)
     * and deserializes the response body into a List&lt;Price&gt; using Jackson.</p>
     *
     * @param url the URL of the endpoint that returns a JSON array representing Price objects
     * @return a List of Price deserialized from the response body
     * @throws RuntimeException if an I/O error or interruption occurs while sending the request or parsing the response
     */
    public static List<Price> fetchPrices(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .timeout(Duration.ofSeconds(30))
                    .header("Accept","application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return mapper.readValue(response.body(), new TypeReference<List<Price>>(){});
            } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error while fetching prices.",e);
        }
    }
}
