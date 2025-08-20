package org.example;

import java.time.LocalDate;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean cliActive = true;

        while (cliActive) {

            // Shows the CLI options
            System.out.println("Welcome to Christians Java CLI!");
            System.out.println("1. Show prices for today");
            System.out.println("2. Show prices for tomorrow");
            System.out.println("3. Show prices for yesterday");
            System.out.println("4. Show average price for a day");
            System.out.println("5. Show cheapest and most expensive hour for a day");
            System.out.println("6. Find best charging hours time");
            System.out.println("7. Exit");

            // Reading the user option
            String choice = scanner.nextLine().trim();

            // Taking action upon user input
            switch (choice) {
                case "1" -> {
                    System.out.println("You've chosen option 1");
                    printZoneAreas();
                    String zone = scanner.nextLine().trim().toUpperCase();
                    if (zone.isEmpty()) zone = "SE3";
                    showPricesForToday(zone);
                }

                case "2" -> {
                    System.out.println("You've chosen option 2");
                    printZoneAreas();
                    String zone = scanner.nextLine().trim().toUpperCase();
                    if (zone.isEmpty()) zone = "SE3";
                    showPricesForTomorrow(zone);
                }

                case "3" -> {
                    System.out.println("You've chosen option 3");
                    printZoneAreas();
                    String zone = scanner.nextLine().trim().toUpperCase();
                    if (zone.isEmpty()) zone = "SE3";
                    printPricesForDay(LocalDate.now().minusDays(1), zone);
                }

                case "4" -> System.out.println("You've chosen option 4");
                case "5" -> System.out.println("You've chosen option 5");
                case "6" -> cliActive = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void printZoneAreas() {
        System.out.println("Enter Zone (SE1–SE4, default SE3): ");
        System.out.println("SE1: Luleå / Norra Sverige");
        System.out.println("SE2: Sundsvall / Norra ");
        System.out.println("SE3: Stockholm / Södra Mellansverige");
        System.out.println("SE4: Malmö / Södra Sverige");
    }

    private static String fetchPricesForDay(LocalDate date, String zone) {
        try {
            // Build the URL using the record
            PriceRequests request = new PriceRequests(
                    date.getYear(),
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    zone
            );

            String url = request.buildUrl();

            // HTTP Client and Get
            var http = java.net.http.HttpClient.newHttpClient();
            var req = java.net.http.HttpRequest
                    .newBuilder(java.net.URI.create(url))
                    .GET()
                    .build();

            var res = http.send(req, java.net.http.HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() == 200) return res.body();
            if (res.statusCode() == 404) return null;

            throw new IllegalStateException("HTTP " + res.statusCode() + " for " + url);

        } catch (Exception e) {
            throw new RuntimeException("API request failed: " + e.getMessage(), e);
        }
    }

    private static void printPricesForDay(LocalDate date, String zone) {
        String json = fetchPricesForDay(date, zone);
        if (json == null) {
            System.out.println("No prices published for " + date + " in " + zone + ".");
            return;
        }
        System.out.println("Prices for " + date + " in " + zone + ":");
        System.out.println(json);
    }

    private static void showPricesForTomorrow(String zone) {
        printPricesForDay(LocalDate.now().plusDays(1), zone);
    }

    private static void showPricesForToday(String zone) {
        printPricesForDay(LocalDate.now(), zone);
    }
}


