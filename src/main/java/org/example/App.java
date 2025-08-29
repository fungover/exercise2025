package org.example;

import org.example.elecPrice.ElectricityPrices;

import java.io.IOException;

public class App {
	public static void main(String[] args) throws IOException, InterruptedException {
		ElectricityPrices electricityPrices = new ElectricityPrices();
		System.out.println(electricityPrices.getElectricityPrices());
	}
}
