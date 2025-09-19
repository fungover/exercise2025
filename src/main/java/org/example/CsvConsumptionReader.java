package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvConsumptionReader {

    // Läser in en CSV-fil med förbrukningsdata och returnerar en lista av ConsumptionPoint
    public static List<ConsumptionPoint> read(String filePath) throws Exception {

        // Här samlar vi alla datapunkter rad för rad från CSV
        List<ConsumptionPoint> points = new ArrayList<>();

        // Öppnar filen med en BufferedReader och stänger filen när vi är klara
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line; // Håller varje rad i filen
            boolean firstLine = true; // Vi hoppar över första raden som är en header

            // Läser rad för rad tills filen tar slut
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Hoppa över första raden
                }

                // Dela upp raden på semikolon
                // "2025-08-01 00:00";"0,23" blir parts[0] = "2025-08-01 00:00", parts[1] = "0,23"
                String[] parts = line.split(";");

                // Ta bort eventuella citattecken runt texten
                String dateTime = parts[0].replace("\"", "");
                String kWhString = parts[1].replace("\"", "");

                // Byt komma mot punkt så java kan tolka siffran
                kWhString = kWhString.replace(",", ".");

                // Gör om texten till en double
                double consumption = Double.parseDouble(kWhString);

                // Skapar ett objekt och lägger det i listan
                points.add(new ConsumptionPoint(dateTime, consumption));
            }
        }

        // Returnerar listan med alla inlästa rader
        return points;
    }
}
