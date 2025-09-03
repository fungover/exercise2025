import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ElectricityPriceClient {

    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();

        // Lista för att lagra alla dagens priser
        List<ElectricityPrice> dagensPriser;

        LocalDate today = LocalDate.now();
        String year = today.format(DateTimeFormatter.ofPattern("yyyy"));
        String monthDay = today.format(DateTimeFormatter.ofPattern("MM-dd"));

        System.out.println("I vilken Elprisområde bor du? Ange en siffra mellan 1 och 4:");
        System.out.println("1 = Luleå / Norra Sverige");
        System.out.println("2 = Sundsvall / Norra mellansverige");
        System.out.println("3 = Stockholm / Södra mellansverige");
        System.out.println("4 = Malmö / Södra Sverige");

        int area = getUserAreaChoice(scanner);
        displayAreaChoice(area);

        // Fråga om laddningstid för elbil
        int laddningstimmar = getChargingHoursChoice(scanner);

        String url = "https://www.elprisetjustnu.se/api/v1/prices/" + year + "/" + monthDay + "_SE" + area + ".json";

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {

                dagensPriser = ElectricityPriceParser.parseJsonToElectricityPrice(response.body());

                if (!dagensPriser.isEmpty()) {
                    System.out.println("\n=== PRISANALYS ===");
                    ElectricityPriceParser.analyzeElectricityPrices(dagensPriser);

                    // Visa bästa laddningstid för elbil
                    ElectricityPriceParser.findBestChargingTime(dagensPriser, laddningstimmar);
                } else {
                    System.out.println("Kunde inte parsa prisdata!");
                }

            } else {
                System.out.println("Något gick fel. Status: " + response.statusCode());
            }

        } catch (IOException e) {
            System.out.println("IO fel: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Request avbruten: " + e.getMessage());
        }

        scanner.close();
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

    /**
     * Hanterar användarens val av laddningstid för elbil
     */
    private static int getChargingHoursChoice(Scanner scanner) {
        System.out.println("\nHur många timmar vill du ladda din elbil?");
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
                    System.out.println("Fel inmatning! Välj 1, 2 eller 3. Försök igen");
                }
            } else {
                System.out.println("Fel inmatning! Skriv bara siffror. Försök igen");
                scanner.next();
            }
        }

        // Konvertera val till timmar
        int[] hours = {0, 2, 4, 8}; // Index 0 används inte
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