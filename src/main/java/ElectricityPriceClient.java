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
import java.util.Scanner;

public class ElectricityPriceClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(java.time.Duration.ofSeconds(10))
                .build();

        // List to store all today's prices
        List<ElectricityPrice> todaysPrice;

        LocalDate today = LocalDate.now(ZoneId.of("Europe/Stockholm"));
        String year = today.format(DateTimeFormatter.ofPattern("yyyy"));
        String monthDay = today.format(DateTimeFormatter.ofPattern("MM-dd"));

        // Morgondagens datum
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

        // Ask about charging time for electric car
        int chargingHours = getChargingHoursChoice(scanner);

        String urlToday = "https://www.elprisetjustnu.se/api/v1/prices/" + year + "/" + monthDay + "_SE" + area + ".json";
        String urlTomorrow = "https://www.elprisetjustnu.se/api/v1/prices/" + yearTomorrow + "/" + monthDayTomorrow + "_SE" + area + ".json";

        // Hämta dagens priser
        fetchAndAnalyze(client, urlToday, "DAGENS PRISER", chargingHours);

        // Hämta morgondagens priser endast efter kl. 14
        LocalTime now = LocalTime.now(ZoneId.of("Europe/Stockholm"));
        if (now.isAfter(LocalTime.of(14, 0))) {
            fetchAndAnalyze(client, urlTomorrow, "MORGONDAGENS PRISER", chargingHours);
        } else {
            System.out.println("\nMorgondagens priser är inte tillgängliga före kl. 14:00.");
        }

        scanner.close();
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

        // Convert selections to hours
        int[] hours = {0, 2, 4, 8}; // Index 0 not in use
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
