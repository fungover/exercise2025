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
		if (!zone.matches("SE[1234]")) {
			System.out.println("Invalid input");
			menuOn = false;
		}
		ElectricityPriceService service = new ElectricityPriceService(zone);

		while (menuOn) {
			System.out.println("[1] Today");
			System.out.println("[2] Tomorrow");
			System.out.println("[3] Quit");
			System.out.print("Your choice: ");
			String dayInput = System.console().readLine().trim().toUpperCase();

			switch (dayInput) {
				case "1":
					List<Hour> prices = service.getElectricityPrices(0);
					service.printPrices(prices, 0);
					try	{
						System.out.print("Charge time (hours): ");
						String chargeInput = System.console().readLine();
						int chargeHours = Integer.parseInt(chargeInput);
						service.chargingHours(prices, chargeHours);
					}  catch (NumberFormatException e) {
						System.out.println("Enter a number");
					}
					break;
				case "2":
					List<Hour> tomorrow = service.getElectricityPrices(1);
					try {
						service.printPrices(tomorrow, 1);
						try	{
							System.out.print("Charge time (hours): ");
							String chargeInput = System.console().readLine();
							int chargeHours = Integer.parseInt(chargeInput);
							service.chargingHours(tomorrow, chargeHours);
						}  catch (NumberFormatException e) {
							System.out.println("Enter a number");
						}
					} catch (Exception e) {
						System.out.println("Tomorrows prices unavailable");
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
