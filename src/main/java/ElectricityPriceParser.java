import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

public class ElectricityPriceParser {

    public static List<ElectricityPrice> parseJsonToElectricityPrice(String jsonString) {
        try {
            // Ta bort hakparenteser och dela upp objekten
            String cleanJson = jsonString.trim()
                    .replaceFirst("^\\[", "")
                    .replaceFirst("\\]$", "");

            return Arrays.stream(cleanJson.split("\\{"))
                    .map(obj -> obj.replaceAll("[{}]", ""))
                    .map(ElectricityPriceParser::parseObjectString)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.out.println("Fel vid JSON-parsing: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static ElectricityPrice parseObjectString(String objectString) {
        // Parse SEK_per_kWh
        double pris = Arrays.stream(objectString.split(","))
                .filter(field -> field.contains("SEK_per_kWh"))
                .map(field -> field.split(":")[1].trim())
                .mapToDouble(Double::parseDouble)
                .findFirst()
                .orElse(0.0);


        // Parse time_start
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

        int hour = parseHourFromTimeString(timeStart);
        String time = String.format("%02d:00-%02d:00", hour, (hour + 1) % 24);

        return new ElectricityPrice(pris, hour, time);
    }

    private static int parseHourFromTimeString(String timeString) {
        try {
            // "2025-08-25T14:00:00+02:00"
            int tIndex = timeString.indexOf('T');
            if (tIndex >= 0 && timeString.length() >= tIndex + 3) {
                String timePart = timeString.substring(tIndex + 1, tIndex + 3);
                return Integer.parseInt(timePart);
            }
        } catch (Exception e) {
            System.out.println("Fel vid analys av timme: " + e.getMessage());
        }
        return 0;
    }

    public static void analyzeElectricityPrices(List<ElectricityPrice > prices) {
        System.out.printf("Totalt %d timpriser hämtade%n", prices.size());

        if (prices.isEmpty()) {
            System.out.println("Inga priser att analysera");
            return;
        }

        // Find the lowest and highest price
        ElectricityPrice lowestPrice = prices.stream()
                .min((p1, p2) -> Double.compare(p1.sekPerKwh(), p2.sekPerKwh()))
                .orElseThrow();

        ElectricityPrice highestPrice = prices.stream()
                .max((p1, p2) -> Double.compare(p1.sekPerKwh(), p2.sekPerKwh()))
                .orElseThrow();

        // averagePrice
        double averagePrice = prices.stream()
                .mapToDouble(ElectricityPrice::sekPerKwh)
                .average()
                .orElse(0.0);


        System.out.printf("Lägsta pris:      %s%n", lowestPrice);
        System.out.printf("Högsta pris:      %s%n", highestPrice);
        System.out.printf("genomsnittligt pris:  %.4f SEK/kWh%n", averagePrice);
    }

    /*
     * Finds best charging time for electric car with Sliding Window algorithm
     */
    public static void findBestChargingTime(List<ElectricityPrice> prices, int hours) {
        if (prices.isEmpty() || hours <= 0) {
            System.out.println("Ogiltiga indata för laddningsanalys");
            return;
        }

        // Sort by hour for sliding window
        List<ElectricityPrice> sortedByHour = prices.stream()
                .sorted((p1, p2) -> Integer.compare(p1.hour(), p2.hour()))
                .toList();

        System.out.printf("\n=== BÄSTA LADDNINGSTID (%d hours) ===\n", hours);

        if (hours > sortedByHour.size()) {
            System.out.println("För få timmar tillgängliga för den begärda laddningsperioden");
            return;
        }

        double bestCost = Double.MAX_VALUE;
        int bestStartIndex = 0;
        List<List<ElectricityPrice>> allWindows = new ArrayList<>();

        // Calculate all possible periods
        for (int i = 0; i <= sortedByHour.size() - hours; i++) {
            List<ElectricityPrice> window = sortedByHour.subList(i, i + hours);
            double totalCost = window.stream()
                    .mapToDouble(ElectricityPrice::sekPerKwh)
                    .sum();

            allWindows.add(new ArrayList<>(window));

            if (totalCost < bestCost) {
                bestCost = totalCost;
                bestStartIndex = i;
            }
        }

        // Find the best window
        List<ElectricityPrice> bestWindow = sortedByHour.subList(bestStartIndex, bestStartIndex + hours);
        double averagePrices = bestCost / hours;

        System.out.printf("Bästa period för %d timmars laddning:\n", hours);
        System.out.printf("Start: %02d:00 - Slut: %02d:00\n",
                bestWindow.getFirst().hour(),
                (bestWindow.get(hours - 1).hour() + 1) % 24);
        System.out.printf("Total kostnad: %.4f SEK/kWh\n", bestCost);
        System.out.printf("Genomsnittligt pris: %.4f SEK/kWh\n", averagePrices);
    }
}