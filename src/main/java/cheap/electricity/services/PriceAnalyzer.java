package cheap.electricity.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

public class PriceAnalyzer {
  private String apiUrl;
  private List<Price> prices;

  public PriceAnalyzer(String apiUrl){
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

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    int status = response.statusCode();
    if (status < 200 || status >= 300) {
      throw new IOException("Failed to fetch prices. HTTP " + status + " from " + this.apiUrl);
      }
    String json = response.body();

    ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    Price[] parsed = mapper.readValue(json, Price[].class);

    this.prices = Arrays.asList(parsed);

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

  public void HighLowPrice() {
    if (prices == null || prices.isEmpty()) {
      System.out.println("No prices loaded. Use option 1 first.");
      return;
    }

    Price minPrice = prices.stream()
            .min((p1, p2) -> {
              int cmp = Double.compare(p1.SEK_per_kWh(), p2.SEK_per_kWh());
              if (cmp != 0) return cmp;
              ZonedDateTime t1 = ZonedDateTime.parse(p1.time_start());
              ZonedDateTime t2 = ZonedDateTime.parse(p2.time_start());
              return t1.compareTo(t2);
            })
            .orElse(null);

    Price maxPrice = prices.stream()
            .max((p1, p2) -> {
              int cmp = Double.compare(p1.SEK_per_kWh(), p2.SEK_per_kWh());
              if (cmp != 0) return cmp;
              ZonedDateTime t1 = ZonedDateTime.parse(p1.time_start());
              ZonedDateTime t2 = ZonedDateTime.parse(p2.time_start());
              return t2.compareTo(t1);
            })
            .orElse(null);

    System.out.printf("Min price: %.4f SEK/kWh at %s%n", minPrice.SEK_per_kWh(), minPrice.time_start());
    System.out.printf("Max price: %.4f SEK/kWh at %s%n", maxPrice.SEK_per_kWh(), maxPrice.time_start());
  }

  public void showBestChargingTime() {
    if (prices == null || prices.isEmpty()) {
      System.out.println("No prices loaded.");
      return;
    }

    int[] durations = {2, 4, 8};

    for (int duration : durations) {
      double minSum = Double.MAX_VALUE;
      int startIndex = 0;

      double currentSum = prices.subList(0, duration).stream()
              .mapToDouble(Price::SEK_per_kWh)
              .sum();

      minSum = currentSum;
      startIndex = 0;

      for (int i = 1; i <= prices.size() - duration; i++) {
        currentSum = currentSum - prices.get(i - 1).SEK_per_kWh() + prices.get(i + duration - 1).SEK_per_kWh();
        if (currentSum < minSum) {
          minSum = currentSum;
          startIndex = i;
        }
      }

      Price start = prices.get(startIndex);
      Price end = prices.get(startIndex + duration - 1);
      System.out.printf("Best time to charge for %d hours: from %s to %s, total cost: %.4f SEK%n",
              duration, start.time_start(), end.time_end(), minSum);
    }
  }
}

record Price(double SEK_per_kWh, String time_start, String time_end){}
