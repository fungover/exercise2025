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

}
