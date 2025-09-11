/**
 * Reads consumption data from a CSV file.
 * Expected format per line: yyyy-MM-dd HH:mm,kWh
 * Parses each line into a Consumption object with a ZonedDateTime (Europe/Stockholm time zone) and a kWh value.
 */


package org.example.service;

import org.example.model.Consumption;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConsumptionCSVReader {
    public static List<Consumption> readCSV(String path) throws IOException {
        List<Consumption> list = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
     try (BufferedReader br = java.nio.file.Files.newBufferedReader(java.nio.file.Path.of(path),
             java.nio.charset.StandardCharsets.UTF_8)) {
         String line;
         int lineNo = 0;
         while ((line = br.readLine()) != null) {
             lineNo++;
             line = line.trim();
             if (line.isEmpty()) continue; //Skip blanks
         String[] parts = line.split(",");
         if (parts.length < 2) continue; // Skip malformed
             String ts = parts[0].trim();
             String kWhStr = parts[1].trim().replace(",", "."); // Allow Swedish commas
             try {

         ZonedDateTime time = LocalDateTime.parse(ts, formatter)
                 .atZone(ZoneId.of("Europe/Stockholm"));
         double kWh = Double.parseDouble(kWhStr);
         list.add(new Consumption(time, kWh));
         } catch (RuntimeException ex) {
             System.out.printf("Error at line %d: %s%n", lineNo, ex.getMessage());
             }
         }
     }
     return list;
    }
}
