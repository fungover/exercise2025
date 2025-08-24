package org.example.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CsvEnergyCalculator {

    public static void calculateFromCsv(String csvFilePath, BigDecimal electricityPrice) throws IOException {

            BigDecimal totalConsumption = BigDecimal.ZERO; // Sets total consumption to zero initially.

            BufferedReader reader = new BufferedReader(new FileReader(csvFilePath)); // Filereader opens the file, BufferedReader makes it more efficient to read.
            reader.readLine(); // reads the first line (header) and ignores it. ("Datum";El kWh") in our csv file.

            String line; // Variable to hold each line read from the file.

        //
            while ((line = reader.readLine()) != null) { // Reads each line in our CSV file until the end (as long as its not null).
                String[] parts = line.split(";"); // Splits the line into parts using semicolon as the delimiter.

                /**
                 * Replace removes unwanted characters from the string. In our case we remove quotes and replace comma with dot
                 * since BigDecimal needs dot as decimal separator.
                 */
                String consumptionStr = parts[1].replace("\"", "").replace(",", ".");
                BigDecimal consumption = new BigDecimal(consumptionStr); //Converts the cleaned string to BigDecimal.
                totalConsumption = totalConsumption.add(consumption); // Adds the consumption value to the total consumption.
            }

            reader.close(); // Closing the reader.

            BigDecimal totalCost = totalConsumption.multiply(electricityPrice) //count the total cost by multiplying total consumption with the price per kWh.
                    .setScale(2, RoundingMode.HALF_UP); // Rounds the result to 2 decimal places.

            System.out.printf("Total consumption: %s kWh%n", totalConsumption.toPlainString());
            System.out.printf("Total cost: %s SEK%n", totalCost.toPlainString());

        }
    }

