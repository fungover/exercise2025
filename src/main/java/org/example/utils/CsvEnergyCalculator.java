package org.example.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CsvEnergyCalculator {

    public static void calculateFromCsv(String csvFilePath, BigDecimal electricityPrice) throws IOException {

            BigDecimal totalConsumption = BigDecimal.ZERO;

            BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String consumptionStr = parts[1].replace("\"", "").replace(",", ".");
                BigDecimal consumption = new BigDecimal(consumptionStr);
                totalConsumption = totalConsumption.add(consumption);
            }

            reader.close();

            BigDecimal totalCost = totalConsumption.multiply(electricityPrice)
                    .setScale(2, RoundingMode.HALF_UP);

            System.out.printf("Total consumption: %s kWh%n", totalConsumption.toPlainString());
            System.out.printf("Total cost: %s SEK%n", totalCost.toPlainString());

        }
    }

