package org.example;
import org.example.elecPrice.ElectricityPrices;
import java.io.IOException;
public class App {
	static void main(String[] args) throws IOException, InterruptedException {
		ElectricityPrices electricityPrices = new ElectricityPrices();
		electricityPrices.run();
	}
}
