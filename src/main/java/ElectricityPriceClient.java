import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ElectricityPriceClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(java.time.Duration.ofSeconds(10))
                .build();

        LocalDate today = LocalDate.now(ZoneId.of("Europe/Stockholm"));
        String year = today.format(DateTimeFormatter.ofPattern("yyyy"));
        String monthDay = today.format(DateTimeFormatter.ofPattern("MM-dd"));

        LocalDate tomorrow = today.plusDays(1);
        String yearTomorrow = tomorrow.format(DateTimeFormatter.ofPattern("yyyy"));
        String monthDayTomorrow = tomorrow.format(DateTimeFormatter.ofPattern("MM-dd"));

        System.out.println("I vilken Elprisområde bor du? Ange en siffra mellan 1 och 4:");
        System.out.println("1 = Luleå / Norra Sverige");
        System.out.println("2 = Sundsvall / Norra mellansverige");
        System.out.println("3 = Stockholm / Södra mellansverige");
        System.out.println("4 = Malmö / Södra Sverige");

        int area = getUserAreaChoice(scanner);
        displayAreaChoice(area);

        // Ask what the user wants to do
        int mode = getAnalysisMode(scanner);

        if (mode == 1) {
            // Original functionality - charging optimization
            int chargingHours = getChargingHoursChoice(scanner);

            String urlToday = "https://www.elprisetjustnu.se/api/v1/prices/" + year + "/" + monthDay + "_SE" + area + ".json";
            String urlTomorrow = "https://www.elprisetjustnu.se/api/v1/prices/" + yearTomorrow + "/" + monthDayTomorrow + "_SE" + area + ".json";

            fetchAndAnalyze(client, urlToday, "DAGENS PRISER", chargingHours);

            LocalTime now = LocalTime.now(ZoneId.of("Europe/Stockholm"));
            if (now.isAfter(LocalTime.of(14, 0))) {
                fetchAndAnalyze(client, urlTomorrow, "MORGONDAGENS PRISER", chargingHours);
            } else {
                System.out.println("\nMorgondagens priser är inte tillgängliga före kl. 14:00.");
            }
        } else {
            // CSV analysis functionality
            String csvFilePath = "consumption.csv";
            String urlToday = "https://www.elprisetjustnu.se/api/v1/prices/" + year + "/" + monthDay + "_SE" + area + ".json";

            System.out.println("Letar efter fil: " + csvFilePath);
            analyzeCsvConsumption(client, urlToday, csvFilePath);
        }

        scanner.close();
    }

    private static int getAnalysisMode(Scanner scanner) {
        System.out.println("\nVälj analysläge:");
        System.out.println("1 = Optimera laddning av elbil");
        System.out.println("2 = Analysera faktisk förbrukning från CSV-fil (consumption.csv)");

        int choice = 0;
        boolean validInput = false;

        while (!validInput) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (choice >= 1 && choice <= 2) {
                    validInput = true;
                } else {
                    System.out.println("Fel inmatning! Välj alternativ 1 eller 2. Försök igen");
                }
            } else {
                System.out.println("Fel inmatning! Skriv bara siffror. Försök igen");
                scanner.nextLine();
            }
        }
        return choice;
    }

    private static void analyzeCsvConsumption(HttpClient client, String url, String csvFilePath) {
        try {
            // Read consumption data from CSV using ConsumptionAnalyzer
            Map<Integer, Double> consumptionData = ConsumptionAnalyzer.readConsumptionStream(csvFilePath);

            if (consumptionData.isEmpty()) {
                System.out.println("Ingen förbrukningsdata kunde läsas från CSV-filen.");
                return;
            }

            System.out.printf("Läste förbrukningsdata för %d timmar%n", consumptionData.size());

            // Fetch electricity prices
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(java.time.Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                List<ElectricityPrice> prices = ElectricityPriceParser.parseJsonToElectricityPrice(response.body());

                if (!prices.isEmpty()) {
                    System.out.println("\n=== KOSTNADSANALYS PÅ FAKTISK FÖRBRUKNING FRÅN CSV-FIL===");
                    calculateTotalCost(prices, consumptionData);
                } else {
                    System.out.println("Kunde inte hämta prisdata från API:et");
                }
            } else {
                System.out.println("Något gick fel vid hämtning av prisdata. Status: " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("Fel vid analys av CSV-förbrukning: " + e.getMessage());
            System.out.println("Kontrollera att CSV-filen är korrekt formaterad och innehåller giltig data.");
        }
    }

    // Simple cost calculation
    private static void calculateTotalCost(List<ElectricityPrice> prices, Map<Integer, Double> consumptionData) {
        double totalCost = 0.0;
        double totalConsumption = 0.0;
        int matchedHours = 0;

        // Create a map for quick price lookup
        Map<Integer, Double> priceMap = prices.stream()
                .collect(Collectors.toMap(
                        ElectricityPrice::hour,
                        ElectricityPrice::sekPerKwh,
                        (existing, replacement) -> existing
                ));

        System.out.println("\n=== DETALJERAD KOSTNADSANALYS ===");
        System.out.printf("%-6s %-12s %-12s %-12s%n", "Timme", "Förbrukning", "Pris", "Kostnad");
        System.out.printf("%-6s %-12s %-12s %-12s%n", "", "(kWh)", "(SEK/kWh)", "(SEK)");
        System.out.println("----------------------------------------------------");

        // Calculate costs for each hour
        for (int hour = 0; hour < 24; hour++) {
            if (consumptionData.containsKey(hour) && priceMap.containsKey(hour)) {
                double consumption = consumptionData.get(hour);
                double price = priceMap.get(hour);
                double hourCost = consumption * price;

                totalCost += hourCost;
                totalConsumption += consumption;
                matchedHours++;

                System.out.printf("%-6d %-12.2f %-12.4f %-12.2f%n",
                        hour, consumption, price, hourCost);
            }
        }

        System.out.println("----------------------------------------------------");
        System.out.printf("TOTALT: %-8.2f kWh = %.2f SEK%n", totalConsumption, totalCost);

        if (totalConsumption > 0) {
            double averagePrice = totalCost / totalConsumption;
            System.out.printf("Genomsnittligt pris: %.4f SEK/kWh%n", averagePrice);
        }

        System.out.printf("Analyserade %d av 24 timmar%n", matchedHours);
    }

    private static void fetchAndAnalyze(HttpClient client, String url, String title, int chargingHours) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(java.time.Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                List<ElectricityPrice> prices = ElectricityPriceParser.parseJsonToElectricityPrice(response.body());

                if (!prices.isEmpty()) {
                    System.out.println("\n=== " + title + " ===");
                    ElectricityPriceParser.analyzeElectricityPrices(prices);
                    ElectricityPriceParser.findBestChargingTime(prices, chargingHours);
                } else {
                    System.out.println("Kunde inte parsa prisdata för " + title);
                }

            } else {
                System.out.println("Något gick fel vid hämtning av " + title + ". Status: " + response.statusCode());
            }

        } catch (IOException e) {
            System.out.println("IO fel vid hämtning av " + title + ": " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Request avbruten vid hämtning av " + title + ": " + e.getMessage());
        }
    }

    private static int getUserAreaChoice(Scanner scanner) {
        int area = 0;
        boolean validInput = false;

        while (!validInput) {
            if (scanner.hasNextInt()) {
                area = scanner.nextInt();
                if (area >= 1 && area <= 4) {
                    validInput = true;
                } else {
                    System.out.println("Fel inmatning! Talet måste vara mellan 1 och 4. Försök igen");
                }
            } else {
                System.out.println("Fel inmatning! Skriv bara siffror. Försök igen");
                scanner.next();
            }
        }
        return area;
    }

    private static int getChargingHoursChoice(Scanner scanner) {
        System.out.println("\nHur många timmar vill du ladda din elbil? Välj alternativ 1, 2 eller 3");
        System.out.println("1 = 2 timmar");
        System.out.println("2 = 4 timmar");
        System.out.println("3 = 8 timmar");

        int choice = 0;
        boolean validInput = false;

        while (!validInput) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 3) {
                    validInput = true;
                } else {
                    System.out.println("Fel inmatning! Välj alternativ 1, 2 eller 3. Försök igen");
                }
            } else {
                System.out.println("Fel inmatning! Skriv bara siffror. Försök igen");
                scanner.next();
            }
        }

        int[] hours = {0, 2, 4, 8};
        int chosenHours = hours[choice];
        System.out.printf("Du valde att ladda i %d timmar\n", chosenHours);

        return chosenHours;
    }

    private static void displayAreaChoice(int area) {
        String[] areas = {
                "",
                "Norra Sverige",
                "Norra mellansverige",
                "Södra mellansverige",
                "Södra Sverige"
        };
        System.out.println("Du valde Elprisområde: " + areas[area]);
    }
}