package org.example.service;

import org.example.model.PriceData;
import org.example.ui.Menu;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

public class CSVImport {

    /**
     * Imports hourly consumption values from a CSV (format: `hour,consumption`), matches each hour to
     * the zone's per-hour price data, and presents a summary of total consumption and cost.
     *
     * <p>The method:
     * - fetches per-hour prices for the given zoneId;
     * - prompts the user (via the supplied Scanner) for a CSV file path;
     * - parses each data line, skipping header lines and lines with invalid numbers;
     * - ignores rows with out-of-range hours or negative consumption;
     * - accumulates total consumption, total cost, and a count of processed data points;
     * - delegates result display to Menu.csvImportMenu(...).
     *
     * @param userName  user display name used when showing the summary
     * @param zoneName  human-readable zone name used when showing the summary
     * @param zoneId    identifier used to retrieve per-hour pricing for the zone
     * @param scanner   input scanner used to read the CSV file path from the user
     */
    public static void showCSVImport(String userName, String zoneName, String zoneId, Scanner scanner) {
        try {
            //--Get price data--
            GetPrices service = new GetPrices(zoneId);
            List<PriceData> prices = service.findAllPrices();

            if (prices.isEmpty()) {
                System.out.println("No price data available.");
                return;
            }

            //--Ask for CSV file path--
            System.out.print("Enter CSV file path (format: hour,consumption): ");
            String filePath = scanner.nextLine();

            //--Read CSV and calculate cost--
            double totalCost = 0.0;
            double totalConsumption = 0.0;
            int dataPoints = 0;

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("hour") || line.contains("consumption")) continue;

                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        try {
                            int hour = Integer.parseInt(parts[0].trim());
                            double consumption = Double.parseDouble(parts[1].trim());

                            if (hour >= 0 && hour < prices.size() && consumption >= 0) {
                                double cost = consumption * prices.get(hour).getSEK_per_kWh();
                                totalCost += cost;
                                totalConsumption += consumption;
                                dataPoints++;
                            }
                        } catch (NumberFormatException e) {
                            //--Skip invalid lines--
                        }
                    }
                }
            }

            //--Display results--
            Menu.csvImportMenu(userName, zoneName, dataPoints, totalConsumption, totalCost);

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
