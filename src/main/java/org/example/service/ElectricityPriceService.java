package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Hour;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class ElectricityPriceService {
	private final String zone;
	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;

	public ElectricityPriceService(String zone) {
		this.zone = zone;
		this.httpClient = HttpClient.newHttpClient();
		this.objectMapper = new ObjectMapper();
	}

	public List<Hour> getElectricityPrices(int day) throws IOException, InterruptedException {
		LocalDate today = LocalDate.now();
		String URL = String.format(
						"https://www.elprisetjustnu.se/api/v1/prices/%d/%02d-%02d_%s.json",
						today.getYear(), today.plusDays(day).getMonthValue(), today.plusDays(day).getDayOfMonth(), zone
		);

		HttpRequest request = HttpRequest.newBuilder()
						.uri(URI.create(URL))
						.header("Accept", "application/json")
						.timeout(Duration.ofSeconds(30))
						.GET()
						.build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() != 200) {
			throw new IOException("Unexpected HTTP " + response.statusCode() + " for " + URL);
		}
		return objectMapper.readValue(response.body(), new TypeReference<List<Hour>>() {
		});
	}

	public void chargingHours(List<Hour> periods, int hours) {
		if (periods == null || periods.isEmpty() || hours <= 0 || hours > periods.size()) {
			System.out.println("No valid periods/hours to compute charging window.");
			return;
		}
		double current = 0.0;
		for (int j = 0; j < hours; j++) {
			current += periods.get(j).SEK_per_kWh();
		}
		double minSum = current;
		int bestIndex = 0;
		for (int i = 1; i <= periods.size() - hours; i++) {
			current += periods.get(i + hours - 1).SEK_per_kWh();
			current -= periods.get(i - 1).SEK_per_kWh();
			if (current < minSum) {
				minSum = current;
				bestIndex = i;
			}
		}
		int bestHour = periods.get(bestIndex).startHour();
		System.out.printf("Cheapest %dh charging period begins at %02d:00%n", hours, bestHour);
	}

	public void printLowestPrice(List<Hour> prices, int day) {
		double lowest = prices.get(0).SEK_per_kWh();
		int lowestHour = 0;
		for (Hour h : prices) {
			if (h.SEK_per_kWh() < lowest) {
				lowest = h.SEK_per_kWh();
				lowestHour = h.startHour();
			}
		}

		System.out.printf("Lowest price: %.2f kr/kWh at %02d:00", lowest, lowestHour);
	}

	public void printHighestPrice(List<Hour> prices, int day) {
		double highest = prices.get(0).SEK_per_kWh();
		int highestHour = 0;
		for (Hour h : prices) {
			if (h.SEK_per_kWh() > highest) {
				highest = h.SEK_per_kWh();
				highestHour = h.startHour();
			}
		}
		System.out.printf("Highest price: %.2f kr/kWh at %02d:00", highest, highestHour);
	}

	public void printMeanPrice(List<Hour> prices, int day) {
		if (prices == null || prices.isEmpty()) {
			System.out.println("No prices available.");
			return;
		}
		double total = 0.0;
		for (Hour h : prices) {
			total += h.SEK_per_kWh();
		}
		LocalDate date = LocalDate.now(ZoneId.of("Europe/Stockholm")).plusDays(day);
		System.out.printf("Average price on %s: %.2f kr/kWh%n", date, total / prices.size());
	}
}
