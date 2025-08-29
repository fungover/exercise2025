package org.example;

import org.example.model.Hour;
import org.example.service.ElectricityPriceService;

import java.io.IOException;
import java.util.List;

public class App {
	static void main(String[] args) throws IOException, InterruptedException {
		System.out.print("Electricity zone: ");
		String zone = System.console().readLine().trim().toUpperCase();

		ElectricityPriceService service = new ElectricityPriceService(zone);
		List<Hour> prices = service.getElectricityPrices();

		double lowest = prices.getFirst().SEK_per_kWh();
		int lowestHour = 0;
		double total = 0.0;
		for (Hour h : prices) {
			if (h.SEK_per_kWh() < lowest) {
				lowest = h.SEK_per_kWh();
				lowestHour = h.formatHour(h.time_start());
			}
			total +=  h.SEK_per_kWh();
		}

		double avg = total /  prices.size();
		System.out.printf("Lowest price: %.2f kr/kWh at %02d:00%n", lowest, lowestHour);
		System.out.printf("Mean price: %.2f kr/kWh%n", avg);
	}
}
