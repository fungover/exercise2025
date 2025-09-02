package org.example;

import org.example.model.Hour;
import org.example.service.ElectricityPriceService;

import java.io.IOException;
import java.util.List;

public class App {
	static void main(String[] args) throws IOException, InterruptedException {
		boolean menuOn = true;
		System.out.print("Electricity zone: SE");
		String input = System.console().readLine().trim().toUpperCase();
		String zone = "SE" + input;
		if (!zone.matches("SE[1-4]")) {
			System.out.println("Invalid input");
			menuOn = false;
		}
		ElectricityPriceService service = new ElectricityPriceService(zone);

		while (menuOn) {
			System.out.println("\n[1] Today");
			System.out.println("[2] Tomorrow");
			System.out.println("[3] Quit");
			System.out.print("Your choice: ");
			String dayInput = System.console().readLine().trim().toUpperCase();

			switch (dayInput) {
				case "1":
					System.out.println("\nToday: ");
					List<Hour> prices = service.getElectricityPrices(0);
					System.out.println("[1] Print lowest price");
					System.out.println("[2] Print highest price");
					System.out.println("[3] Print mean price");
					System.out.println("[4] Calculate best charging period");
					String tdyInput = System.console().readLine();
					switch (tdyInput) {
						case "1":
							service.printLowestPrice(prices, 0);
							break;
						case "2":
							service.printHighestPrice(prices, 0);
							break;
						case "3":
							service.printMeanPrice(prices, 0);
							break;
						case "4":
							try {
								System.out.print("Charge time (hours): ");
								String chargeInput = System.console().readLine();
								int chargeHours = Integer.parseInt(chargeInput);
								service.chargingHours(prices, chargeHours);
							} catch (NumberFormatException e) {
								System.out.println("Enter a number");
							}
							break;
						default:
							System.out.println("Invalid input");
							break;

					}
					break;
				case "2":
					List<Hour> tomorrow = service.getElectricityPrices(1);
					System.out.println("\nTomorrow: ");
					System.out.println("[1] Print lowest price");
					System.out.println("[2] Print highest price");
					System.out.println("[3] Print mean price");
					System.out.println("[4] Calculate best charging period");
					String tmrInput = System.console().readLine();
					switch (tmrInput) {
						case "1":
							service.printLowestPrice(tomorrow, 1);
							break;
						case "2":
							service.printHighestPrice(tomorrow, 1);
							break;
						case "3":
							service.printMeanPrice(tomorrow, 1);
							break;
						case "4":
							try {
								System.out.print("Charge time (hours): ");
								String chargeInput = System.console().readLine();
								int chargeHours = Integer.parseInt(chargeInput);
								service.chargingHours(tomorrow, chargeHours);
							} catch (NumberFormatException e) {
								System.out.println("Enter a number");
							}
							break;
						default:
							System.out.println("Invalid input");
							break;
					}
					break;
				case "3":
					menuOn = false;
					break;
				default:
					System.out.println("Invalid choice");
					break;
			}
		}
	}

}
