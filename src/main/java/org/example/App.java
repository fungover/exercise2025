package org.example;

import java.time.LocalDate;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean cliActive = true;

        while (cliActive) {
            Printers.printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.println("==========================================================");
                    System.out.println(" Show electrical prices for a day ");
                    System.out.println("==========================================================");

                    LocalDate day = askForDate(scanner);
                    String zone = askForZone(scanner);

                    String json = HttpFetch.fetchJsonForDay(day, zone);
                    if (json != null) {
                        Printers.printPricesForDay(day, zone, json);
                    }
                }

                case "2" -> {
                    System.out.println("==========================================================");
                    System.out.println(" Show average price for a day ");
                    System.out.println("==========================================================");

                    LocalDate day = askForDate(scanner);
                    String zone = askForZone(scanner);

                    String json = HttpFetch.fetchJsonForDay(day, zone);
                    if (json != null) {
                        MeanPriceCalculator.printMeanPrice(json);
                    }
                }

                case "3" -> {
                    System.out.println("==========================================================");
                    System.out.println(" Show best charging hours for a day ");
                    System.out.println("==========================================================");

                    LocalDate day = askForDate(scanner);
                    String zone = askForZone(scanner);

                    String json = HttpFetch.fetchJsonForDay(day, zone);
                    if (json != null) {
                        try {
                            PriceEntry[] prices = HttpFetch.parseEntries(json);
                            Printers.printBestChargingWindows(prices);
                        } catch (Exception e) {
                            System.out.println("Failed to parse prices: " + e.getMessage());
                        }
                    }
                }

                case "4" -> {
                    System.out.println("You have exited the Program!");
                    cliActive = false;
                }

                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static LocalDate askForDate(Scanner scanner) {
        System.out.print("Enter date (yyyy-mm-dd): ");
        return LocalDate.parse(scanner.nextLine().trim());
    }

    private static String askForZone(Scanner scanner) {
        System.out.print("Enter zone (SE1, SE2, SE3, SE4): ");
        return scanner.nextLine().trim().toUpperCase();
    }
}
