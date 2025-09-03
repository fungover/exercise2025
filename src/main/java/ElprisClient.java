import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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

        // ArrayList för att lagra alla dagens priser
        ArrayList<ElPris> dagensPriser;

        LocalDate today = LocalDate.now();
        String year = today.format(DateTimeFormatter.ofPattern("yyyy"));
        String monthDay = today.format(DateTimeFormatter.ofPattern("MM-dd"));

        System.out.println("I vilken elprisområde bor du? Ange en siffra mellan 1 och 4:");
        System.out.println("1 = Luleå / Norra Sverige");
        System.out.println("2 = Sundsvall / Norra mellansverige");
        System.out.println("3 = Stockholm / Södra mellansverige");
        System.out.println("4 = Malmö / Södra Sverige");

        int area = 0;
        boolean validInput = false;
        while (!validInput) {
            if (scanner.hasNextInt()) {
                area = scanner.nextInt();
                if (area >= 1 && area <= 4) {
                    validInput = true;
                    switch (area) {
                        case 1:
                            System.out.println("Du valde elprisområde: " + "Norra Sverige");
                            break;
                        case 2:
                            System.out.println("Du valde elprisområde: " + "Norra mellansverige");
                            break;
                        case 3:
                            System.out.println("Du valde elprisområde: " + "Södra mellansverige");
                            break;
                        case 4:
                            System.out.println("Du valde elprisområde: " + "Södra Sverige");
                    }
                } else {
                    System.out.println("Fel inmatning! Talet måste vara mellan 1 och 4. Försök igen");
                }
            } else {
                System.out.println("Fel inmatning! Skriv bara siffror. Försök igen");
                scanner.next();
            }
        }

        String url = "https://www.elprisetjustnu.se/api/v1/prices/" + year + "/" + monthDay + "_SE" + area + ".json";

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                //System.out.println("Data hämtad!");

                dagensPriser = parseJsonToElPris(response.body());

                if (!dagensPriser.isEmpty()) {
                    System.out.println("\n=== PRISANALYS ===");
                    analyzeElectricityPrices(dagensPriser);
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

    // Metod för att parsa JSON till ArrayList<ElPris>
    public static ArrayList<ElPris> parseJsonToElPris(String jsonString) {

        ArrayList<ElPris> priser = new ArrayList<>();

        try {
            // JSON ser ut så här: [{"SEK_per_kWh":0.5234,"time_start":"2025-08-25T00:00:00+02:00",...}, ...]

            // Ta bort hakparenteser i början och slut
            jsonString = jsonString.trim();
            if (jsonString.startsWith("[")) jsonString = jsonString.substring(1);
            if (jsonString.endsWith("]")) jsonString = jsonString.substring(0, jsonString.length() - 1);

            String[] objekts = jsonString.split("\\},\\{");

            for (int i = 0; i < objekts.length; i++) {
                String objekt = objekts[i].trim();

                // Rensa bort kvarvarande {eller}
                objekt = objekt.replace("{", "").replace("}", "");

                // Parsa SEK_per_kWh
                double pris = parsePrisFromJson(objekt);

                // Parsa time_start för att få timme
                String timeStart = parseTimeStartFromJson(objekt);
                int timme = parseHourFromTimeString(timeStart);

                // Skapa tid-sträng
                String tid = String.format("%02d:00-%02d:00", timme, (timme + 1) % 24);

                // Skapa ElPris och lägg till i ArrayList
                priser.add(new ElPris(pris, timme, tid));
            }

        } catch (Exception e) {
            System.out.println("Fel vid JSON-parsing: " + e.getMessage());
        }

        return priser;
    }

    // Hitta SEK_per_kWh värdet
    private static double parsePrisFromJson(String json) {
        String search = "\"SEK_per_kWh\":";
        int start = json.indexOf(search) + search.length();
        int end = json.indexOf(",", start);
        if (end == -1) end = json.length();

        String valueStr = json.substring(start, end).trim();
        return Double.parseDouble(valueStr);
    }

    // Hitta time_start värdet
    private static String parseTimeStartFromJson(String json) {
        String search = "\"time_start\":\"";
        int start = json.indexOf(search) + search.length();
        int end = json.indexOf("\"", start);

        return json.substring(start, end);
    }

    // Hjälpmetod för att extrahera timme från tid-sträng
    private static int parseHourFromTimeString(String timeString) {
        // timeString ser ut som: "2025-08-25T14:00:00+02:00"
        int tIndex = timeString.indexOf('T');
        String timePart = timeString.substring(tIndex + 1, tIndex + 3);
        return Integer.parseInt(timePart);
    }

    // Metod för att analysera priserna
    public static void analyzeElectricityPrices(ArrayList<ElPris> priser) {
        System.out.printf("Totalt %d timpriser hämtade%n", priser.size());

        // Hitta lägsta och högsta pris
        ElPris lagstaPris = Collections.min(priser,
                (p1, p2) -> Double.compare(p1.sekPerKwh(), p2.sekPerKwh()));

        ElPris hogstaPris = Collections.max(priser,
                (p1, p2) -> Double.compare(p1.sekPerKwh(), p2.sekPerKwh()));

        // Beräkna genomsnitt
        double genomsnitt = priser.stream()
                .mapToDouble(ElPris::sekPerKwh)
                .average()
                .orElse(0.0);

        System.out.printf("Lägsta pris:      %s%n", lagstaPris);
        System.out.printf("Högsta pris:      %s%n", hogstaPris);
        System.out.printf("Genomsnittspris:  %.4f SEK/kWh%n", genomsnitt);
        System.out.printf("Prisskillnad:     %.4f SEK/kWh (%.1f%%)%n",
                hogstaPris.sekPerKwh() - lagstaPris.sekPerKwh(),
                ((hogstaPris.sekPerKwh() - lagstaPris.sekPerKwh()) / lagstaPris.sekPerKwh()) * 100);

        // Visa alla priser (valfritt - kommentera ut om du inte vill se alla)
        System.out.println("\n=== ALLA DAGENS PRISER ===");
        for (ElPris pris : priser) {
            System.out.println(pris);
        }
    }
}