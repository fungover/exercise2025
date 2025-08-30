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
  private String apiUrl;
  private List<Price> prices;

  public Api(String apiUrl){
    this.apiUrl = apiUrl;
  }

  public void setUrl(String url) {
    this.apiUrl = url;
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

    this.prices = Arrays.asList(prices);

    System.out.println("Prices are downloaded successfully");
  }

  public void showMeanPrice() {
    if (prices == null || prices.isEmpty()) {
      System.out.println("No prices loaded. Use option 1 first.");
      return;
    }

    double mean = prices.stream()
            .mapToDouble(Price::SEK_per_kWh)
            .average()
            .orElse(0.0);

    System.out.println("Mean price is: " + mean);
  }
}

record Price(double SEK_per_kWh, String time_start, String time_end){}
