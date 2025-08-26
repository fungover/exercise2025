package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Läser en CSV med format:
// "Datum";"El kWh"
// "2025-08-01 00:00";"0,23"
public class CsvConsumptionReader {

    // Läser filen och returnerar en lista med ConsumptionPoint
    public static List<ConsumptionPoint> readConsumption(String filename) throws IOException {
        List<ConsumptionPoint> list = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(filename));

        try {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                // hoppar över rubrikraden
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                // tar bort citattecken
                String cleaned = line.replace("\"", "");

                // delar upp på semikolon → [datum, el-kwh]
                String[] parts = cleaned.split(";");

                String time = parts[0];
                String kwhStr = parts[1];

                kwhStr = kwhStr.replace(",", ".");

                double consumption = Double.parseDouble(kwhStr);

                list.add(new ConsumptionPoint(time, consumption));
            }
        } finally {
            br.close();
        }

        return list;
    }
}
