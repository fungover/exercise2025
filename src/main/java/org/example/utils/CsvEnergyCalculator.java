package org.example.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CsvEnergyCalculator {

    public static void calculateFromCsv(String csvFilePath, BigDecimal electricityPrice) throws IOException { // Method to calculate total consumption and cost from a CSV file.
        BigDecimal totalConsumption = BigDecimal.ZERO; // Initialize total consumption to 0.

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) { // Using try-with-resources to ensure the reader is closed automatically.

            reader.readLine(); // Skip the header line.
            String line; // Variable to hold each line read from the file.

            while ((line = reader.readLine()) != null) { // Read each line until the end of the file.

                if (line.isBlank()) continue; // Skip empty lines.

                String[] parts = line.split(";"); // Split the line by semicolon to get individual columns.


                if (parts.length < 2) { // Ensure there are at least two columns (timestamp and consumption).
                    System.err.printf("Skipping malformed row (expected 2+ columns): %s%n", line); // Log and skip malformed rows.
                    continue;
                }

                String consumptionStr = parts[1].replace("\"", "").trim().replace(",", "."); // Get the consumption value, remove quotes, trim whitespace, and replace commas with dots for decimal format.
                if (consumptionStr.isEmpty()) continue; // Skip if consumption is empty.

                try {
                    BigDecimal consumption = new BigDecimal(consumptionStr); // Parse the consumption value to BigDecimal.
                    totalConsumption = totalConsumption.add(consumption); // Add the consumption to the total.
                } catch (NumberFormatException nfe) { // Handle invalid number formats.
                    System.err.printf("Skipping unparsable consumption '%s' in row: %s%n", consumptionStr, line); // Log and skip unparsable consumption values.
                }
            }
        }

        BigDecimal totalCost = totalConsumption.multiply(electricityPrice).setScale(2, RoundingMode.HALF_UP); // Calculate total cost and round to 2 decimal places.
        System.out.printf("Total consumption: %s kWh%n", totalConsumption.toPlainString()); // Display total consumption.
        System.out.printf("Total cost: %s SEK%n", totalCost.toPlainString()); // Display total cost.
    }

}