package org.example;

import java.time.LocalDate;
import java.util.Scanner;

public class App {

    private static final HttpFetchers fetchers = new HttpFetchers();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean cliActive = true;

        while (cliActive) {
            Printers.printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.println("You've chosen option 1");
                    Printers.printZoneAreas();
                    String zone = scanner.nextLine().trim().toUpperCase();
                    if (zone.isEmpty()) zone = "SE3";
                    showPricesForToday(zone);
                }

                case "2" -> {
                    System.out.println("You've chosen option 2");
                    Printers.printZoneAreas();
                    String zone = scanner.nextLine().trim().toUpperCase();
                    if (zone.isEmpty()) zone = "SE3";
                    showPricesForTomorrow(zone);
                }

                case "3" -> {
                    System.out.println("You've chosen option 3");
                    Printers.printZoneAreas();
                    String zone = scanner.nextLine().trim().toUpperCase();
                    if (zone.isEmpty()) zone = "SE3";
                    String json = fetchers.fetchPricesForDay(LocalDate.now().minusDays(1), zone);
                    Printers.printPricesForDay(LocalDate.now().minusDays(1), zone, json);
                }

                case "4" -> System.out.println("You've chosen option 4");
                case "5" -> System.out.println("You've chosen option 5");
                case "6" -> System.out.println("You've chosen option 6");
                case "7" -> {
                    System.out.println("You have exited the CLI!");
                    cliActive = false;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    public static void showPricesForTomorrow(String zone) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String json = fetchers.fetchPricesForDay(tomorrow, zone);
        Printers.printPricesForDay(tomorrow, zone, json);
    }

    public static void showPricesForToday(String zone) {
        LocalDate today = LocalDate.now();
        String json = fetchers.fetchPricesForDay(today, zone);
        Printers.printPricesForDay(today, zone, json);
    }
}



