package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Hour;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.time.LocalDate;
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
						.GET()
						.build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		try {

			return objectMapper.readValue(response.body(), new TypeReference<>() {
			});
		} catch (Exception e) {
			return null;
		}
	}

	public String chargingHours(List<Hour> periods, int hours) {
		double min_sum = Integer.MAX_VALUE;
		int bestHour = 0;
		for (int i = 0; i < periods.size() - hours + 1; i++) {
			double current = 0.0;
			int currentHour = periods.get(i).formatHour(periods.get(i).time_start());
			for (int j = 0; j < hours; j++) {
				current += periods.get(i + j).SEK_per_kWh();
			}
			if (current < min_sum) {
				min_sum = current;
				bestHour = currentHour;
			}
		}
		return String.format("Cheapest %dh charging period begins at %02d:00%n", hours,  bestHour);
	}
}
