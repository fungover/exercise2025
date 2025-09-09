package org.example;

import org.example.model.Hour;
import org.example.service.ElectricityPriceService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class App {
	static void main(String[] args) throws IOException, InterruptedException {
		final Scanner sc = new Scanner(System.in);
		boolean menuOn = true;
		System.out.print("Electricity zone: SE");
		String input = sc.nextLine().trim().toUpperCase();
		String zone = "SE" + input;
		if (!zone.matches("SE[1-4]")) {
			System.out.println("Invalid input");
			menuOn = false;
		}
		if (!menuOn) {
			return;
		}
		ElectricityPriceService service = new ElectricityPriceService(zone);

		while (menuOn) {
			System.out.println("\n[1] Today");
			System.out.println("[2] Tomorrow");
			System.out.println("[3] Quit");
			System.out.print("Your choice: ");
			String dayInput = sc.nextLine().trim().toUpperCase();

			switch (dayInput) {
				case "1":
					System.out.println("\nToday: ");
					List<Hour> prices = service.getElectricityPrices(0);
					if (prices == null || prices.isEmpty()) {
						System.out.println("No price data available. Try again later.");
						break;
					}
					System.out.println("[1] Print lowest price");
					System.out.println("[2] Print highest price");
					System.out.println("[3] Print mean price");
					System.out.println("[4] Calculate best charging period");
					System.out.print("Your choice: ");
					String tdyInput = sc.nextLine();
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
								int chargeHours = Integer.parseInt(sc.nextLine().trim());
								if (chargeHours < 1 || chargeHours > prices.size()) {
									System.out.printf("Enter 1..%d%n", prices.size());
									break;
								}
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
					if (tomorrow == null || tomorrow.isEmpty()) {
						System.out.println("No price data available. Try again later.");
						break;
					}
					System.out.println("\nTomorrow: ");
					System.out.println("[1] Print lowest price");
					System.out.println("[2] Print highest price");
					System.out.println("[3] Print mean price");
					System.out.println("[4] Calculate best charging period");
					System.out.print("Your choice: ");
					String tmrInput = sc.nextLine();
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
								int chargeHours = Integer.parseInt(sc.nextLine().trim());
								if (chargeHours < 1 || chargeHours > tomorrow.size()) {
									System.out.printf("Enter 1..%d%n", tomorrow.size());
									break;
								}
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
