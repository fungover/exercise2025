package org.example.elecPrice;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ElectricityPrices {
	private final String BASE_URL = "https://www.elprisetjustnu.se/api/v1/prices/2025/08-28_SE3.json";

	private final HttpClient httpClient;

	public ElectricityPrices() {
		httpClient = HttpClient.newHttpClient();
	}

	public String getElectricityPrices() throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
						.uri(URI.create(BASE_URL))
						.GET()
						.build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		return response.body();
	}
}
