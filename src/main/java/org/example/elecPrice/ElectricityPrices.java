package org.example.elecPrice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ElectricityPrices {
	private final String BASE_URL = "https://www.elprisetjustnu.se/api/v1/prices/2025/08-28_SE3.json";
	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;

	public ElectricityPrices() {
		httpClient = HttpClient.newHttpClient();
		objectMapper = new ObjectMapper();
	}

	public List<Hour> getElectricityPrices() throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
						.uri(URI.create(BASE_URL))
						.GET()
						.build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		return objectMapper.readValue(response.body(), new TypeReference<>() {
		});
	}

	public void run() throws IOException, InterruptedException {

		ElectricityPrices adapter = new ElectricityPrices();
		List<Hour> electricityPrices = adapter.getElectricityPrices();
		for (Hour hourPeriod : electricityPrices) {
			int now = hourPeriod.formatHour(hourPeriod.time_start());
			int then = hourPeriod.formatHour(hourPeriod.time_end());
			System.out.println("Timespan: " + (now < 10 ? "0" : "") + now + " - " + (then < 10 ? "0" : "") + then);
			System.out.printf("%.2f%n", hourPeriod.SEK_per_kWh());
		}
	}
}
