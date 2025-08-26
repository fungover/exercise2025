package org.example.service;

import org.example.model.PricePoint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvConsumptionCalculator {

    private static final String DEFAULT_FILE = "data/consumption.csv";

    public Map<String, Double> readConsumption(String filePath) {
        Map<String, Double> consumption = new HashMap<>();
        String pathToUse = (filePath == null || filePath.isEmpty()) ? DEFAULT_FILE : filePath;

        try (BufferedReader br = new BufferedReader(new FileReader(Paths.get(pathToUse).toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String timestamp = parts[0].trim();
                    double kWh = Double.parseDouble(parts[1].trim());
                    consumption.put(timestamp, kWh);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file: " + pathToUse, e);
        }

        return consumption;
    }

    public double calculateTotalCost(List<PricePoint> prices, Map<String, Double> consumption) {
        double total = 0.0;

        for (PricePoint price : prices) {
            String key = price.start().toString();
            if (consumption.containsKey(key)) {
                total += price.price() * consumption.get(key);
            }
        }

        return total;
    }
}