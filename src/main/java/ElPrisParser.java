import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Klass för att parsa JSON-data från elpris-API:et med funktionell programmering
 */
public class ElPrisParser {

    /**
     * Parsar JSON till ElPris-lista med enbart Java Streams
     */
    public static List<ElPris> parseJsonToElPris(String jsonString) {
        try {
            // Ta bort hakparenteser och dela upp objekten
            String cleanJson = jsonString.trim()
                    .replaceFirst("^\\[", "")
                    .replaceFirst("\\]$", "");

            return Arrays.stream(cleanJson.split("\\{"))
                    .map(obj -> obj.replaceAll("[{}]", ""))
                    .map(ElPrisParser::parseObjectString)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.out.println("Fel vid JSON-parsing: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Parsar en objekt-sträng till ElPris med Streams
     */
    private static ElPris parseObjectString(String objectString) {
        // Parsa SEK_per_kWh med Streams
        double pris = Arrays.stream(objectString.split(","))
                .filter(field -> field.contains("SEK_per_kWh"))
                .map(field -> field.split(":")[1].trim())
                .mapToDouble(Double::parseDouble)
                .findFirst()
                .orElse(0.0);

        // Parsa time_start med Streams
        String timeStart = Arrays.stream(objectString.split(","))
                .filter(field -> field.contains("time_start"))
                .map(field -> {
                    String[] parts = field.split(":");
                    // Rekonstruera tid-strängen: "2025-08-25T14:00:00+02:00"
                    return parts[1].replace("\"", "").trim() + ":" +
                            parts[2] + ":" + parts[3];
                })
                .findFirst()
                .orElse("");

        int timme = parseHourFromTimeString(timeStart);
        String tid = String.format("%02d:00-%02d:00", timme, (timme + 1) % 24);

        return new ElPris(pris, timme, tid);
    }

    /**
     * Extraherar timme från tid-sträng
     */
    private static int parseHourFromTimeString(String timeString) {
        try {
            // timeString ser ut som: "2025-08-25T14:00:00+02:00"
            int tIndex = timeString.indexOf('T');
            if (tIndex >= 0 && timeString.length() >= tIndex + 3) {
                String timePart = timeString.substring(tIndex + 1, tIndex + 3);
                return Integer.parseInt(timePart);
            }
        } catch (Exception e) {
            System.out.println("Fel vid parsing av timme: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Utökad analys med funktionell programmering
     */
    public static void analyzeElectricityPrices(List<ElPris> priser) {
        System.out.printf("Totalt %d timpriser hämtade%n", priser.size());

        if (priser.isEmpty()) {
            System.out.println("Inga priser att analysera");
            return;
        }

        // Hitta lägsta och högsta pris med Streams
        ElPris lagstaPris = priser.stream()
                .min((p1, p2) -> Double.compare(p1.sekPerKwh(), p2.sekPerKwh()))
                .orElseThrow();

        ElPris hogstaPris = priser.stream()
                .max((p1, p2) -> Double.compare(p1.sekPerKwh(), p2.sekPerKwh()))
                .orElseThrow();

        // Beräkna genomsnitt med Streams
        double genomsnitt = priser.stream()
                .mapToDouble(ElPris::sekPerKwh)
                .average()
                .orElse(0.0);

        // Visa grundläggande statistik
        System.out.printf("Lägsta pris:      %s%n", lagstaPris);
        System.out.printf("Högsta pris:      %s%n", hogstaPris);
        System.out.printf("Genomsnittspris:  %.4f SEK/kWh%n", genomsnitt);
        System.out.printf("Prisskillnad:     %.4f SEK/kWh (%.1f%%)%n",
                hogstaPris.sekPerKwh() - lagstaPris.sekPerKwh(),
                ((hogstaPris.sekPerKwh() - lagstaPris.sekPerKwh()) / lagstaPris.sekPerKwh()) * 100);

        // Visa ytterligare analys
        showAdvancedAnalysis(priser, genomsnitt);
    }

    /**
     * Avancerad analys med Streams
     */
    private static void showAdvancedAnalysis(List<ElPris> priser, double genomsnitt) {
        System.out.println("\n=== YTTERLIGARE ANALYS ===");

        // De 3 billigaste timmarna
        List<ElPris> billigaste = priser.stream()
                .sorted((p1, p2) -> Double.compare(p1.sekPerKwh(), p2.sekPerKwh()))
                .limit(3)
                .toList();

        System.out.println("De 3 billigaste timmarna:");
        billigaste.forEach(pris -> System.out.println("  " + pris));

        // De 3 dyraste timmarna
        List<ElPris> dyraste = priser.stream()
                .sorted((p1, p2) -> Double.compare(p2.sekPerKwh(), p1.sekPerKwh()))
                .limit(3)
                .toList();

        System.out.println("\nDe 3 dyraste timmarna:");
        dyraste.forEach(pris -> System.out.println("  " + pris));

        // Timmar under genomsnittet
        long antalUnderGenomsnitt = priser.stream()
                .filter(p -> p.sekPerKwh() < genomsnitt)
                .count();

        System.out.printf("\nTimmar under genomsnittet: %d av %d (%.1f%%)%n",
                antalUnderGenomsnitt, priser.size(),
                (antalUnderGenomsnitt * 100.0) / priser.size());

        // Bästa perioder för elförbrukning
        findBestConsumptionPeriods(priser, genomsnitt);
    }

    /**
     * Hittar bästa perioder för elförbrukning (funktionell approach)
     */
    private static void findBestConsumptionPeriods(List<ElPris> priser, double genomsnitt) {
        System.out.println("\nBästa perioder för elförbrukning (under genomsnittet):");

        List<ElPris> sortedByHour = priser.stream()
                .sorted((p1, p2) -> Integer.compare(p1.timme(), p2.timme()))
                .toList();

        // Hitta perioder under genomsnittet
        List<String> periods = new ArrayList<>();
        int start = -1;

        for (int i = 0; i < sortedByHour.size(); i++) {
            ElPris pris = sortedByHour.get(i);

            if (pris.sekPerKwh() < genomsnitt) {
                if (start == -1) start = i;
            } else {
                if (start != -1 && i - start >= 2) { // Minst 2 timmar i rad
                    double avgPrice = sortedByHour.subList(start, i).stream()
                            .mapToDouble(ElPris::sekPerKwh)
                            .average().orElse(0.0);

                    periods.add(String.format("  %s - %s (%.4f SEK/kWh genomsnitt)",
                            sortedByHour.get(start).tid().split("-")[0],
                            pris.tid().split("-")[0],
                            avgPrice));
                }
                start = -1;
            }
        }

        // Kolla sista perioden
        if (start != -1 && sortedByHour.size() - start >= 2) {
            double avgPrice = sortedByHour.subList(start, sortedByHour.size()).stream()
                    .mapToDouble(ElPris::sekPerKwh)
                    .average().orElse(0.0);

            periods.add(String.format("  %s - 24:00 (%.4f SEK/kWh genomsnitt)",
                    sortedByHour.get(start).tid().split("-")[0],
                    avgPrice));
        }

        if (periods.isEmpty()) {
            System.out.println("  Inga längre perioder under genomsnittet hittades");
        } else {
            periods.forEach(System.out::println);
        }
    }
}