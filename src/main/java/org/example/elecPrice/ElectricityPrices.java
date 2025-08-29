package org.example.elecPrice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;

public class ElectricityPrices {
	private String zone;
	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;

	public ElectricityPrices() {
		httpClient = HttpClient.newHttpClient();
		objectMapper = new ObjectMapper();
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public List<Hour> getElectricityPrices() throws IOException, InterruptedException {
		LocalDate today = LocalDate.now();
		int year = today.getYear();
		int month = today.getMonthValue();
		int day = today.getDayOfMonth();
		String URL = String.format("https://www.elprisetjustnu.se/api/v1/prices/%s/%s-%s_%s.json",
						year,
						(month < 10 ? "0" : "") + month,
						(day < 10 ? "0" : "") + day,
						zone);
		HttpRequest request = HttpRequest.newBuilder()
						.uri(URI.create(URL))
						.GET()
						.build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		return objectMapper.readValue(response.body(), new TypeReference<>() {
		});
	}

	public void run() throws IOException, InterruptedException {
		System.out.print("Electricity zone: ");
		String zone = System.console().readLine().trim().toUpperCase();
		ElectricityPrices adapter = new ElectricityPrices();
		adapter.setZone(zone);
		List<Hour> electricityPrices = adapter.getElectricityPrices();
		for (Hour hourPeriod : electricityPrices) {
			int now = hourPeriod.formatHour(hourPeriod.time_start());
			int then = hourPeriod.formatHour(hourPeriod.time_end());
			System.out.println("Timespan: " + (now < 10 ? "0" : "") + now + " - " + (then < 10 ? "0" : "") + then);
			System.out.printf("%.2f%n", hourPeriod.SEK_per_kWh());
		}
	}
}
