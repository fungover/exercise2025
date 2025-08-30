package cheap.electricity.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class Api {
  private final String apiUrl;

  public Api(String apiUrl){
    this.apiUrl = apiUrl;
  }

  public void getPrices() throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(this.apiUrl))
            .timeout(Duration.ofSeconds(10))
            .header("Accept", "application/json")
            .build();

    var response = client.send(request, HttpResponse.BodyHandlers.ofString());
    String json = response.body();

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    Price[] prices = mapper.readValue(json, Price[].class);

    List<Price> priceList = Arrays.asList(prices);

    System.out.println("Prices are downloaded successfully");
  }
}

record Price(double SEK_per_kWh, String time_start, String time_end){}
