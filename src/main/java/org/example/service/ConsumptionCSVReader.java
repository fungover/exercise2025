/**
 * Reads consumption data from a CSV file.
 * Expected format per line:  yyyy-MM-dd HH:mm,kWh
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
     try (BufferedReader br = new BufferedReader(new FileReader(path))) {
         String line;
         while ((line = br.readLine()) != null) {
         String[] parts = line.split(",");
         ZonedDateTime time = LocalDateTime.parse(parts[0], formatter)
                 .atZone(ZoneId.of("Europe/Stockholm"));
         double kWh = Double.parseDouble(parts[1]);
         list.add(new Consumption(time, kWh));
         }
     }
     return list;
    }
}
