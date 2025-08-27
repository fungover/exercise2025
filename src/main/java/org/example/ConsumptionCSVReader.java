package org.example;

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
