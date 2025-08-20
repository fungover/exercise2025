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
                    System.out.println("Enter Zone (SE1–SE4, default SE3): ");
                    printZoneAreas();
                    String zone = scanner.nextLine().trim().toUpperCase();
                    if (zone.isEmpty()) zone = "SE3";

                    showPricesForToday(zone);
                }

                case "2" -> {
                    System.out.println("You've chosen option 2");
                    System.out.println("Enter Zone (SE1–SE4, default SE3): ");
                    printZoneAreas();
                    String zone = scanner.nextLine().trim().toUpperCase();
                    if (zone.isEmpty()) zone = "SE3";

                    showPricesForTomorrow(zone);
                }
                case "3" -> System.out.println("You've chosen option 3");
                case "4" -> System.out.println("You've chosen option 4");
                case "5" -> System.out.println("You've chosen option 5");
                case "6" -> cliActive = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void printZoneAreas() {
        System.out.println("SE1: Luleå / Norra Sverige");
        System.out.println("SE2: Sundsvall / Norra ");
        System.out.println("SE3: Stockholm / Södra Mellansverige");
        System.out.println("SE4: Malmö / Södra Sverige");
    }

    private static void showPricesForTomorrow(String zone) {
        try {
            var http = java.net.http.HttpClient.newHttpClient();
            var tomorrow = java.time.LocalDate.now().plusDays(1);

            PriceRequests request = new PriceRequests(tomorrow.getYear(), tomorrow.getMonthValue(), tomorrow.getDayOfMonth(), zone);

            String url = request.buildUrl();


            var req = java.net.http.HttpRequest.newBuilder(java.net.URI.create(url)).GET().build();
            var res = http.send(req, java.net.http.HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() != 200) {
                System.out.println("The prices for tomorrow hasn't been released yet, try again later");
                return;
            }

            String body = res.body();
            System.out.println(body);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void showPricesForToday(String zone) {
        try {
            var http = java.net.http.HttpClient.newHttpClient(); // Creates a HTTP Client
            var today = java.time.LocalDate.now(); // Gets current date 2025-08-20

            PriceRequests request = new PriceRequests(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), zone);

            String url = request.buildUrl();


            var req = java.net.http.HttpRequest.newBuilder(java.net.URI.create(url)).GET().build();
            var res = http.send(req, java.net.http.HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() != 200) {
                System.out.println("HTTP " + res.statusCode() + " for " + url);
                return;
            }

            String body = res.body();
            System.out.println(body);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

