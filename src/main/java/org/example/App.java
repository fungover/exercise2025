package org.example;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {

        // Startläge
        // Om inga argument skickas in startas interaktiv meny
        if (args.length == 0) {
            runInteractiveMode();
            return;
        }

        // Om argument finns -> kör argumentläge
        runArgumentMode(args);
    }

    // Interaktiv meny
    private static void runInteractiveMode() throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("-------------------------------------");
        System.out.println("------ Startar elprisverktyget ------");
        System.out.println("-------------------------------------");

        // Fråga om zon
        System.out.print("Ange zon (SE1, SE2, SE3, SE4): ");
        String zone = scanner.nextLine().trim().toUpperCase();
        if (zone.isEmpty()) {
            zone = "SE1";
        }

        // Kontrollera att zonen är giltig
        List<String> validZones = List.of("SE1", "SE2", "SE3", "SE4");
        if (!validZones.contains(zone)) {
            System.out.println("Felaktig zon angiven: " + zone + ". Standardzon SE1 används istället.");
            zone = "SE1";
        }

        // Fråga om CSV
        System.out.print("Vill du analysera en CSV-fil med förbrukningsdata? (ja/nej): ");
        String answer = scanner.nextLine().trim().toLowerCase();

        if (answer.equals("ja")) {
            System.out.println("--- CSV-analys vald ---");
            System.out.println("Ange filnamnet på CSV-filen (utan sökväg).");
            System.out.println("OBS: Filen måste ligga i katalogen: src/main/resources/");
            String fileName = scanner.nextLine().trim();

            String csvPath = "src/main/resources/" + fileName;

            // Kontrollera att filen finns
            java.io.File file = new java.io.File(csvPath);
            if (!file.exists() || fileName.isEmpty()) {
                System.out.println("Filen hittades inte: " + csvPath);
                System.out.println("Avbryter CSV-analysen och kör endast zon-analys.");
                runArgumentMode(new String[]{zone});
            } else {
                System.out.println("Läser fil: " + csvPath);
                runArgumentMode(new String[]{zone, csvPath});
            }
        } else {
            // Ingen CSV → kör bara zon-analys
            runArgumentMode(new String[]{zone});
        }

        scanner.close();
    }

    // Argumentläge (args används)
    private static void runArgumentMode(String[] args) throws Exception {

        // args[0] = elområde (SE1, SE2, SE3, SE4)
        // args[1] = (valfritt) sökväg till CSV-fil med förbrukningsdata

        String[] validZones = {"SE1", "SE2", "SE3", "SE4"};
        String zone = "SE1"; // default

        if (args.length >= 1) {
            zone = args[0].toUpperCase();
        }

        // Kontrollera att zonen är giltig
        if (!Arrays.asList(validZones).contains(zone)) {
            System.out.println("Ogiltig zon: " + zone + ". Använder SE1.");
            zone = "SE1";
        }

        // Om CSV-fil anges -> kör endast CSV-kalkyl och returnera
        if (args.length >= 2) {
            String csvPath = args[1];
            calculateTotalCostFromCsv(zone, csvPath);
            return; // hoppa ur, kör inte prisanalysen
        }

        // Annars kör prisanalysen (utan CSV)
        System.out.println("Zon: " + zone);

        ZoneId sweden = ZoneId.of("Europe/Stockholm");
        LocalDate today = LocalDate.now(sweden);
        LocalDate tomorrow = today.plusDays(1);

        String urlToday = buildUrl(today, zone);
        String urlTomorrow = buildUrl(tomorrow, zone);

        List<PricePoint> pricesToday = PriceFetcher.fetchPrices(urlToday);
        List<PricePoint> pricesTomorrow = PriceFetcher.fetchPricesOrEmpty(urlTomorrow);

        double avgPrice = PriceUtils.averagePrice(pricesToday);
        PricePoint cheapestPrice = PriceUtils.cheapestPrice(pricesToday);
        PricePoint mostExpensivePrice = PriceUtils.mostExpensivePrice(pricesToday);

        System.out.println("Medelpris: " + String.format("%.4f", avgPrice) + "kr/kWh");
        System.out.println("Billigaste timme: " + cheapestPrice.getHour() + " (" +
                String.format("%.4f", cheapestPrice.getPrice()) + "kr/kWh)");
        System.out.println("Dyraste timme: " + mostExpensivePrice.getHour() + " (" +
                String.format("%.4f", mostExpensivePrice.getPrice()) + "kr/kWh)");
        System.out.println();

        List<PricePoint> prices = new ArrayList<>();
        prices.addAll(pricesToday);
        prices.addAll(pricesTomorrow);

        System.out.println("Optimalt laddningsintervall idag eller imorgon: ");
        int best2hStart = PriceUtils.findBestWindowStart(prices, 2);
        System.out.println(PriceUtils.windowSummary(prices, best2hStart, 2));

        int best4hStart = PriceUtils.findBestWindowStart(prices, 4);
        System.out.println(PriceUtils.windowSummary(prices, best4hStart, 4));

        int best8hStart = PriceUtils.findBestWindowStart(prices, 8);
        System.out.println(PriceUtils.windowSummary(prices, best8hStart, 8));
    }


    // Bygger rätt API-url för ett visst datum och zon
    private static String buildUrl(LocalDate date, String zone) {
        String year = String.valueOf(date.getYear());
        String mmdd = date.format(DateTimeFormatter.ofPattern("MM-dd"));
        return "https://www.elprisetjustnu.se/api/v1/prices/" + year + "/" + mmdd + "_" + zone + ".json";
    }

    // Beräknar elkostnad och statistik från en CSV-fil
    private static void calculateTotalCostFromCsv(String zone, String csvPath) {
        try {
            // === Läs in förbrukningsdata ===
            List<ConsumptionPoint> consumptionData = CsvConsumptionReader.read(csvPath);

            if (consumptionData.isEmpty()) {
                System.out.println("CSV-filen var tom eller ogiltig.");
                return;
            }

            // Samla unika datum som finns i CSV
            Set<LocalDate> csvDates = new HashSet<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            for (ConsumptionPoint c : consumptionData) {
                LocalDate date = java.time.LocalDateTime.parse(c.getTime(), formatter).toLocalDate();
                csvDates.add(date);
            }

            // Hämta priser för dessa datum
            List<PricePoint> pricePointsForCsvDates = new ArrayList<>();
            for (LocalDate date : csvDates) {
                List<PricePoint> oneDay = PriceFetcher.fetchPrices(buildUrl(date, zone));
                pricePointsForCsvDates.addAll(oneDay);
            }

            // Bygg en uppslagskarta med priser per timme
            Map<String, Double> priceByHour = new HashMap<>();
            for (PricePoint p : pricePointsForCsvDates) {
                String key = p.getDate() + " " + p.getHour();
                priceByHour.put(key, p.getPrice());
            }

            // Filtrera fram matchade priser mot de i CSV filen
            List<PricePoint> matchedPrices = new ArrayList<>();
            for (ConsumptionPoint c : consumptionData) {
                Double price = priceByHour.get(c.getTime());
                if (price != null) {
                    matchedPrices.add(new PricePoint(
                            c.getTime().substring(0, 10), // yyyy-MM-dd
                            c.getTime().substring(11),    // HH:mm
                            price
                    ));
                }
            }

            // Räkna ut totalkostnad
            double totalCost = getTotalCost(pricePointsForCsvDates, consumptionData);

            System.out.println();
            System.out.println("--- CSV-analys för perioden i filen ---");
            System.out.println("Total elkostnad: " + String.format("%.2f", totalCost) + "kr");

            // Räkna ut statistik på de matchade timmarna
            double avgCsvPrice = PriceUtils.averagePrice(matchedPrices);
            PricePoint cheapestCsv = PriceUtils.cheapestPrice(matchedPrices);
            PricePoint mostExpensiveCsv = PriceUtils.mostExpensivePrice(matchedPrices);

            System.out.println("Medelpris: " + String.format("%.4f", avgCsvPrice) + "kr/kWh");
            System.out.println("Billigaste timme: " + cheapestCsv.getDate() + " " + cheapestCsv.getHour()
                    + " (" + String.format("%.4f", cheapestCsv.getPrice()) + "kr/kWh)");
            System.out.println("Dyraste timme: " + mostExpensiveCsv.getDate() + " " + mostExpensiveCsv.getHour()
                    + " (" + String.format("%.4f", mostExpensiveCsv.getPrice()) + "kr/kWh)");

        } catch (Exception e) {
            System.err.println("Fel vid CSV-baserad kostnadsberäkning: " + e.getMessage());
        }
    }


    // Hjälpmetod: matchar varje rad i CSV mot pris och räknar ut totalkostnaden
    private static double getTotalCost(List<PricePoint> pricePointsForCsvDates,
                                       List<ConsumptionPoint> consumptionData) {
        // Bygger upp en "uppslagskarta" (Map) för pris per timme
        Map<String, Double> priceByHour = new HashMap<>();
        for (PricePoint p : pricePointsForCsvDates) {
            String key = p.getDate() + " " + p.getHour(); // ex. "2025-08-01 00:00"
            priceByHour.put(key, p.getPrice());
        }

        // Gå igenom alla timmar i konsumtionsfilen och summera
        double totalCost = 0.0;
        for (ConsumptionPoint c : consumptionData) {
            Double price = priceByHour.get(c.getTime());
            if (price != null) {
                totalCost += price * c.getConsumption();
            }
        }
        return totalCost;
    }
}