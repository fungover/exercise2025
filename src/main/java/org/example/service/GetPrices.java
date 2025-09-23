package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.PriceData;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GetPrices {
  private static final String API_URL_PATTERN = "https://www.elprisetjustnu.se/api/v1/prices/%d/%s_%s.json";
  private static final String DEFAULT_ZONE = "SE3"; // Stockholm

  private final HttpClient client;
  private final ObjectMapper objectMapper;
  private final String zoneId;

  public GetPrices() {
    this(DEFAULT_ZONE);
  }

  public GetPrices(String zoneId) {
    this.client = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(5))
        .build();
    this.objectMapper = new ObjectMapper();
    this.zoneId = zoneId;
  }

  public List<PriceData> findAllPrices() {
    List<PriceData> allPrices = new ArrayList<>();

    try {
      // Get today's prices
      ZoneId SE = ZoneId.of("Europe/Stockholm");
      LocalDate today = ZonedDateTime.now(SE).toLocalDate();
      List<PriceData> todayPrices = fetchPricesForDate(today);
      allPrices.addAll(todayPrices);

      // --get tomorrow's price if available--
      try {
        LocalDate tomorrow = today.plusDays(1);
        List<PriceData> tomorrowPrices = fetchPricesForDate(tomorrow);
        allPrices.addAll(tomorrowPrices);
      } catch (Exception e) {
        System.out.println("Tomorrow's prices not yet available.");
      }

      // Sort by timestamp to ensure chronological order
      allPrices.sort(Comparator.comparing((PriceData price) -> {
        try {
          return OffsetDateTime.parse(price.getTime_start());
        } catch (Exception e) {
          // Fallback to string comparison if parsing fails
          return OffsetDateTime.MIN;
        }
      }));

      return allPrices;

    } catch (Exception e) {
      throw new RuntimeException("Error fetching price data: " + e.getMessage(), e);
    }
  }

  public List<PriceData> fetchPricesForDate(LocalDate date) {
    try {
      String dateStr = date.format(DateTimeFormatter.ofPattern("MM-dd"));
      String url = String.format(API_URL_PATTERN,
          date.getYear(), dateStr, zoneId);

      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(url))
          .timeout(Duration.ofSeconds(10))
          .header("User-Agent", "Electricity-Scout/1.0 (+https://github.com/fungover/exercise2025)")
          .GET()
          .build();

      HttpResponse<String> response = client.send(request,
          HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        TypeReference<List<PriceData>> typeReference = new TypeReference<List<PriceData>>() {
        };
        return objectMapper.readValue(response.body(), typeReference);
      } else {
        throw new RuntimeException("Failed to fetch data for " + date + " with status code: " + response.statusCode());
      }

    } catch (Exception e) {
      throw new RuntimeException("Error fetching price data for " + date + ": " + e.getMessage(), e);
    }
  }

  public List<PriceData> findPricesForDate(LocalDate date) {
    return fetchPricesForDate(date);
  }

  // --Legacy method for backward compatibility--
  public String findAll() {
    try {
      ZoneId SE = ZoneId.of("Europe/Stockholm");
      LocalDate today = ZonedDateTime.now(SE).toLocalDate();
      String dateStr = today.format(DateTimeFormatter.ofPattern("MM-dd"));
      String url = String.format(API_URL_PATTERN,
          today.getYear(), dateStr, zoneId);

      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(url))
          .timeout(Duration.ofSeconds(10))
          .header("User-Agent", "Electricity-Scout/1.0 (+https://github.com/fungover/exercise2025)")
          .GET()
          .build();

      HttpResponse<String> response = client.send(request,
          HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        return response.body();
      } else {
        throw new RuntimeException("Failed to fetch data with status code: " + response.statusCode());
      }

    } catch (Exception e) {
      throw new RuntimeException("Error fetching price data: " + e.getMessage(), e);
    }
  }
}
