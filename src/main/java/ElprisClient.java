import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

// Record för att lagra elpris-data
record ElPris(double sekPerKwh, int timme, String tid) {
    @Override
    public String toString() {
        return String.format("Timme %02d: %.4f SEK/kWh (%s)", timme, sekPerKwh, tid);
    }
}

public class ElprisClient {

    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();

        // Lista för att lagra alla dagens priser (nu med List istället för ArrayList)
        List<ElPris> dagensPriser;

        LocalDate today = LocalDate.now();
        String year = today.format(DateTimeFormatter.ofPattern("yyyy"));
        String monthDay = today.format(DateTimeFormatter.ofPattern("MM-dd"));

        System.out.println("I vilken elprisområde bor du? Ange en siffra mellan 1 och 4:");
        System.out.println("1 = Luleå / Norra Sverige");
        System.out.println("2 = Sundsvall / Norra mellansverige");
        System.out.println("3 = Stockholm / Södra mellansverige");
        System.out.println("4 = Malmö / Södra Sverige");

        int area = getUserAreaChoice(scanner);
        displayAreaChoice(area);

        String url = "https://www.elprisetjustnu.se/api/v1/prices/" + year + "/" + monthDay + "_SE" + area + ".json";

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Använd den nya ElPrisParser-klassen
                dagensPriser = ElPrisParser.parseJsonToElPris(response.body());

                if (!dagensPriser.isEmpty()) {
                    System.out.println("\n=== PRISANALYS ===");
                    ElPrisParser.analyzeElectricityPrices(dagensPriser);
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

    /**
     * Hanterar användarens val av elprisområde med förbättrad validering
     */
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
     * Visar användarens valda elprisområde
     */
    private static void displayAreaChoice(int area) {
        String[] areas = {
                "", // Index 0 används inte
                "Norra Sverige",
                "Norra mellansverige",
                "Södra mellansverige",
                "Södra Sverige"
        };
        System.out.println("Du valde elprisområde: " + areas[area]);
    }
}