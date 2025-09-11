import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ConsumptionAnalyzer {

    public static Map<Integer, Double> readConsumptionStream(String fileName) {
        Map<Integer, Double> result = new HashMap<>();

        String[] pathsToTry = {
                fileName,                           // Working directory
                "src/main/java/" + fileName,
        };

        String foundPath = null;

        // Find the file
        for (String path : pathsToTry) {
            java.io.File file = new java.io.File(path);
            if (file.exists()) {
                System.out.println("Hittade fil: " + file.getAbsolutePath());
                foundPath = path;
                break;
            }
        }

        if (foundPath == null) {
            System.out.println("Kunde inte hitta " + fileName + " i någon av dessa platser:");
            for (String path : pathsToTry) {
                System.out.println("  " + new java.io.File(path).getAbsolutePath());
            }
            return result;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(foundPath))) {
            br.lines()
                    .filter(line -> !line.trim().isEmpty())
                    .filter(line -> !line.trim().startsWith("#"))
                    .map(line -> line.replaceFirst("^\uFEFF", ""))
                    .map(line -> line.split(";"))
                    .filter(values -> values.length >= 2 && !values[1].trim().isEmpty())
                    .forEach(values -> {
                        try {
                            int hour = Integer.parseInt(values[0].trim());
                            double consumption = Double.parseDouble(values[1].trim().replace(",", "."));

                            if (hour >= 0 && hour <= 23 && consumption >= 0) {
                                result.put(hour, consumption);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Ogiltig data: " + Arrays.toString(values));
                        }
                    });
        } catch (IOException e) {
            System.out.println("Fel vid läsning av fil: " + e.getMessage());
        }

        System.out.printf("Läste %d timmar från %s%n", result.size(), foundPath);
        return result;
    }
}