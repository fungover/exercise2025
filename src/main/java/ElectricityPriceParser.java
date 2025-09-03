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

        return new ElectricityPrice(pris, timme, tid);
    }

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

    public static void analyzeElectricityPrices(List<ElectricityPrice> prices) {
        System.out.printf("Totalt %d timpriser hämtade%n", prices.size());

        if (prices.isEmpty()) {
            System.out.println("Inga priser att analysera");
            return;
        }

        // Hitta lägsta och högsta pris med Streams
        ElectricityPrice lowestPrice = prices.stream()
                .min((p1, p2) -> Double.compare(p1.sekPerKwh(), p2.sekPerKwh()))
                .orElseThrow();

        ElectricityPrice highestPrice = prices.stream()
                .max((p1, p2) -> Double.compare(p1.sekPerKwh(), p2.sekPerKwh()))
                .orElseThrow();

        // Beräkna averagePrice med Streams
        double averagePrice = prices.stream()
                .mapToDouble(ElectricityPrice::sekPerKwh)
                .average()
                .orElse(0.0);

        // Visa grundläggande statistik
        System.out.printf("Lägsta pris:      %s%n", lowestPrice);
        System.out.printf("Högsta pris:      %s%n", highestPrice);
        System.out.printf("genomsnittligt pris:  %.4f SEK/kWh%n", averagePrice);
        System.out.printf("Prisskillnad:     %.4f SEK/kWh (%.1f%%)%n",
                highestPrice.sekPerKwh() - lowestPrice.sekPerKwh(),
                ((highestPrice.sekPerKwh() - lowestPrice.sekPerKwh()) / lowestPrice.sekPerKwh()) * 100);

        // Visa ytterligare analys
        //showAdvancedAnalysis(prices, averagePrice);
    }

    /*
     * Avancerad analys med Streams
     */
    /*private static void showAdvancedAnalysis(List<ElectricityPrice> prices, double averagePrice) {
        System.out.println("\n=== YTTERLIGARE ANALYS ===");

        // De 3 billigaste timmarna (tidigaste vid lika pris)
        List<ElectricityPrice> billigaste = prices.stream()
                .sorted((p1, p2) -> {
                    int priceCompare = Double.compare(p1.sekPerKwh(), p2.sekPerKwh());
                    return priceCompare != 0 ? priceCompare : Integer.compare(p1.timme(), p2.timme());
                })
                .limit(3)
                .toList();

        System.out.println("De 3 billigaste hours (tidigaste vid lika pris):");
        billigaste.forEach(pris -> System.out.println("  " + pris));

        // De 3 dyraste timmarna (tidigaste vid lika pris)
        List<ElectricityPrice> dyraste = prices.stream()
                .sorted((p1, p2) -> {
                    int priceCompare = Double.compare(p2.sekPerKwh(), p1.sekPerKwh());
                    return priceCompare != 0 ? priceCompare : Integer.compare(p1.timme(), p2.timme());
                })
                .limit(3)
                .toList();

        System.out.println("\nDe 3 dyraste hours (tidigaste vid lika pris):");
        dyraste.forEach(pris -> System.out.println("  " + pris));

        // hours under averagePriceet
        long antalUnderaveragePrice = prices.stream()
                .filter(p -> p.sekPerKwh() < averagePrice)
                .count();

        System.out.printf("\nhours under averagePriceet: %d av %d (%.1f%%)%n",
                antalUnderaveragePrice, prices.size(),
                (antalUnderaveragePrice * 100.0) / prices.size());

        // Bästa perioder för elförbrukning
        findBestConsumptionPeriods(prices, averagePrice);
    }*/

    /**
     * Hittar bästa laddningstid för elbil med Sliding Window-algoritm
     */
    public static void findBestChargingTime(List<ElectricityPrice> prices, int hours) {
        if (prices.isEmpty() || hours <= 0) {
            System.out.println("Ogiltiga indata för laddningsanalys");
            return;
        }

        // Sortera efter timme för sliding window
        List<ElectricityPrice> sortedByHour = prices.stream()
                .sorted((p1, p2) -> Integer.compare(p1.timme(), p2.timme()))
                .toList();

        System.out.printf("\n=== BÄSTA LADDNINGSTID (%d hours) ===\n", hours);

        if (hours > sortedByHour.size()) {
            System.out.println("För få timmar tillgängliga för den begärda laddningsperioden");
            return;
        }

        double bestCost = Double.MAX_VALUE;
        int bestStartIndex = 0;
        List<List<ElectricityPrice>> allWindows = new ArrayList<>();

        // Sliding Window - räkna alla möjliga perioder
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

        // Hitta det bästa fönstret
        List<ElectricityPrice> bestWindow = sortedByHour.subList(bestStartIndex, bestStartIndex + hours);
        double averagePrices = bestCost / hours;

        System.out.printf("Bästa period för %d timmars laddning:\n", hours);
        System.out.printf("Start: %02d:00 - Slut: %02d:00\n",
                bestWindow.getFirst().timme(),
                (bestWindow.get(hours - 1).timme() + 1) % 24);
        System.out.printf("Total kostnad: %.4f SEK/kWh\n", bestCost);
        System.out.printf("Genomsnittligt pris: %.4f SEK/kWh\n", averagePrices);

        //System.out.println("\ntimmar i bästa perioden:");
        //bestWindow.forEach(pris -> System.out.println("  " + pris));

        // Visa jämförelse med andra alternativ
        //showChargingAlternatives(allWindows, hours, bestCost);
    }

    /*
     * Visar alternativa laddningsperioder för jämförelse
     */
    /*
    private static void showChargingAlternatives(List<List<ElectricityPrice>> allWindows, int hours, double bestCost) {
        System.out.println("\nAndra alternativ (de 3 bästa):");

        allWindows.stream()
                .sorted((f1, f2) -> Double.compare(
                        f1.stream().mapToDouble(ElectricityPrice::sekPerKwh).sum(),
                        f2.stream().mapToDouble(ElectricityPrice::sekPerKwh).sum()))
                .limit(3)
                .forEach(window -> {
                    double totalCost = window.stream().mapToDouble(ElectricityPrice::sekPerKwh).sum();
                    double averagePrice = totalCost / hours;
                    System.out.printf("  %02d:00-%02d:00: %.4f SEK/kWh (averagePrice: %.4f)\n",
                            window.getFirst().timme(),
                            (window.get(hours - 1).timme() + 1) % 24,
                            totalCost,
                            averagePrice);
                });

        // Beräkna besparingar
        double worstCost= allWindows.stream()
                .mapToDouble(window -> window.stream().mapToDouble(ElectricityPrice::sekPerKwh).sum())
                .max()
                .orElse(bestCost);

        if (worstCost > bestCost) {
            double besparing = worstCost- bestCost;
            double besparingProcent = (besparing / worstCost) * 100;
            System.out.printf("\nMöjlig besparing: %.4f SEK/kWh (%.1f%%) jämfört med sämsta tidpunkten\n",
                    besparing, besparingProcent);
        }
    }
    */

    /*
     * Hittar bästa perioder för elförbrukning (funktionell approach)
     */
    /*private static void findBestConsumptionPeriods(List<ElectricityPrice> prices, double averagePrice) {
        System.out.println("\nBästa perioder för elförbrukning (under genomsnittspriset):");

        List<ElectricityPrice> sortedByHour = prices.stream()
                .sorted((p1, p2) -> Integer.compare(p1.timme(), p2.timme()))
                .toList();

        // Hitta perioder under averagePriceet
        List<String> periods = new ArrayList<>();
        int start = -1;

        for (int i = 0; i < sortedByHour.size(); i++) {
            ElectricityPrice pris = sortedByHour.get(i);

            if (pris.sekPerKwh() < averagePrice) {
                if (start == -1) start = i;
            } else {
                if (start != -1 && i - start >= 2) { // Minst 2 hours i rad
                    double avgPrice = sortedByHour.subList(start, i).stream()
                            .mapToDouble(ElectricityPrice::sekPerKwh)
                            .average().orElse(0.0);

                    periods.add(String.format("  %s - %s (%.4f SEK/kWh averagePrice)",
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
                    .mapToDouble(ElectricityPrice::sekPerKwh)
                    .average().orElse(0.0);

            periods.add(String.format("  %s - 24:00 (%.4f SEK/kWh averagePrice)",
                    sortedByHour.get(start).tid().split("-")[0],
                    avgPrice));
        }

        if (periods.isEmpty()) {
            System.out.println("  Inga längre perioder under averagePriceet hittades");
        } else {
            periods.forEach(System.out::println);
        }
    }*/
}