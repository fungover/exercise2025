package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.PriceData;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GetPrices {
  private static final String API_URL_PATTERN = "https://www.elprisetjustnu.se/api/v1/prices/%d/%s_%s.json";
  private static final String DEFAULT_ZONE = "SE3"; // Stockholm

  private final HttpClient client;
  private final ObjectMapper objectMapper;
  private final String zoneId;

  /**
   * Creates a GetPrices instance configured for the default zone ("SE3").
   */
  public GetPrices() {
    this(DEFAULT_ZONE);
  }

  /**
   * Creates a GetPrices instance for the given zone.
   *
   * Initializes the HTTP client and JSON object mapper and stores the zone identifier used when building API requests.
   *
   * @param zoneId zone identifier (e.g., "SE3") used in the API URL
   */
  public GetPrices(String zoneId) {
    this.client = HttpClient.newHttpClient();
    this.objectMapper = new ObjectMapper();
    this.zoneId = zoneId;
  }

  /**
   * Fetches and returns price data for today and, if available, tomorrow, combined into a single list.
   *
   * This method always attempts to fetch today's prices and appends them to the result list.
   * It then attempts to fetch tomorrow's prices; if tomorrow's data is not yet available the method
   * continues and returns only today's prices (the missing tomorrow data is reported to stdout).
   *
   * @return a combined list of PriceData for today and tomorrow (tomorrow's entries may be absent)
   * @throws RuntimeException if fetching today's prices or another unexpected error fails
   */
  public List<PriceData> findAllPrices() {
    List<PriceData> allPrices = new ArrayList<>();

    try {
      // Get today's prices
      LocalDate today = LocalDate.now();
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

      return allPrices;

    } catch (Exception e) {
      throw new RuntimeException("Error fetching price data: " + e.getMessage(), e);
    }
  }

  /**
   * Fetches price data for the given date and returns it as a list of PriceData.
   *
   * The date is formatted as "MM-dd" and used, together with the date's year and the instance's
   * zoneId, to build the API URL. Performs a synchronous HTTP GET and deserializes a 200 response
   * body into List<PriceData>.
   *
   * @param date the date to fetch prices for (used as YYYY and `MM-dd` in the API path)
   * @return a list of PriceData parsed from the API response
   * @throws RuntimeException if the HTTP response status is not 200 or if any error occurs while
   *                          sending the request or parsing the response
   */
  private List<PriceData> fetchPricesForDate(LocalDate date) {
    try {
      String dateStr = date.format(DateTimeFormatter.ofPattern("MM-dd"));
      String url = String.format(API_URL_PATTERN,
          date.getYear(), dateStr, zoneId);

      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(url))
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

  /**
   * Legacy (backward-compatible) method that fetches the raw JSON price payload for today for the configured zone.
   *
   * <p>Constructs the API URL for the current date and zone, performs an HTTP GET, and returns the response body
   * if the response status is 200.</p>
   *
   * @return the raw JSON response body containing today's price data
   * @throws RuntimeException if the HTTP response status is not 200 or if any error occurs while building/sending the request or reading the response
   */
  public String findAll() {
    try {
      LocalDate today = LocalDate.now();
      String dateStr = today.format(DateTimeFormatter.ofPattern("MM-dd"));
      String url = String.format(API_URL_PATTERN,
          today.getYear(), dateStr, zoneId);

      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(url))
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
