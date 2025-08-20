package org.example;

import java.time.LocalDate;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean cliActive = true;

        while(cliActive) {

            // Shows the CLI options
            System.out.println("Welcome to Christians Java CLI!");
            System.out.println("1. Show prices prices for today");
            System.out.println("2. Show prices for tomorrow");
            System.out.println("3. Show prices for yesterday");
            System.out.println("4. Show average price for a day");
            System.out.println("5. Show cheapest and most expensive hour for a day");
            System.out.println("6. Exit");

            // Reading the user option
            String choice = scanner.nextLine().trim();


            // Taking action upon user input
            switch (choice) {
                case "1" -> {
                    System.out.println("You've chosen option 1");
                    System.out.print("Enter Zone (SE1–SE4, default SE3): ");
                    String zone = scanner.nextLine().trim().toUpperCase();
                    if (zone.isEmpty()) {
                        zone = "SE3";
                    } else if (!zone.trim().equalsIgnoreCase("SE1")
                    && !zone.trim().toUpperCase().equals("SE2")
                    && !zone.trim().toUpperCase().equals("SE3")
                    && !zone.trim().toUpperCase().equals("SE4")) {
                        zone = "SE3";
                    }

                    showPricesForToday(LocalDate.now(), zone);
                }

                case "2" -> System.out.println("You've chosen option 2");
                case "3" -> System.out.println("You've chosen option 3");
                case "4" -> System.out.println("You've chosen option 4");
                case "5" -> System.out.println("You've chosen option 5");
                case "6" -> cliActive = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }
    private static void showPricesForToday(LocalDate now, String zone) {
        try {
            var http = java.net.http.HttpClient.newHttpClient(); // Creates a HTTP Client
            var today = java.time.LocalDate.now(); // Gets current date 2025-08-20

            String year = String.valueOf(today.getYear());
            String month = String.format("%02d",today.getMonthValue()); // Format to add a 0 if it's not a double nummber
            String day = String.format("%02d",today.getDayOfMonth()); // 8 becomes 08

            String url = "https://www.elprisetjustnu.se/api/v1/prices/" + year + "/" + month + "-" + day + "_" + zone + ".json";

            var req = java.net.http.HttpRequest.newBuilder(java.net.URI.create(url)).GET().build();
            var res = http.send(req, java.net.http.HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() != 200) {
                System.out.println("HTTP " + res.statusCode() + " for " + url);
                return;
            }

/*          System.out.println(url); Debuggers
            System.out.println(today);*/

            String body = res.body();
            System.out.println(body);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

