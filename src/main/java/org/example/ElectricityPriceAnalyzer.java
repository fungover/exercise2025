package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;

public class ElectricityPriceAnalyzer {
    private static final Path CSV_PATH = Path.of("output", "prices.csv");
    private static final ZoneId zoneId = ZoneId.of("Europe/Stockholm");


    public static boolean hasPriceForToday() throws IOException {
        if (!Files.exists(CSV_PATH)) {
            return false;
        }

        LocalDate today = LocalDate.now(zoneId);

        try (var reader =  Files.newBufferedReader(CSV_PATH)) {
            String line;
            boolean skipFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue;
                }

                String[] columns = line.split(";");
                if (columns.length > 0) {
                    String timeStart = columns[0];
                    if (timeStart.startsWith(today.toString())) {
                        return true;
                    }

                }

            }
        }
        return false;
    }
}
