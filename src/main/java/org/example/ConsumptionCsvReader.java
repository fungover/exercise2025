package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Reads hourly consumption data from a CSV file.
 * Expected format: time_start,consumption_kWh
 */
public class ConsumptionCsvReader {

    public static Map<String, Double> loadConsumption(String resourceName) throws IOException {
        Map<String, Double> consumption = new HashMap<>();

        try (InputStream is = App.class.getResourceAsStream("/" + resourceName);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            if (is == null) {
                throw new IOException("Resource not found: " + resourceName);
            }

            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                if (first && line.toLowerCase().contains("consumption")) {
                    first = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length < 2) continue;

                String timeStart = parts[0].trim();
                double kWh = Double.parseDouble(parts[1].trim());
                consumption.put(timeStart, kWh);
            }
        }
        return consumption;
    }

}
