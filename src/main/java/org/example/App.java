package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;
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
                    System.out.println("==========================================================");
                    System.out.println(" Show electrical prices for a day ");
                    System.out.println("==========================================================");

                    LocalDate day = askForDate(scanner);
                    String zone = askForZone(scanner);

                    String json = fetchJsonForDay(day, zone);
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

                    String json = fetchJsonForDay(day, zone);
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

                    String json = fetchJsonForDay(day, zone);
                    if (json != null) {
                        try {
                            PriceEntry[] prices = parseEntries(json);
                            Printers.printBestChargingWindows(prices);
                        } catch (Exception e) {
                            System.out.println("Failed to parse prices: " + e.getMessage());
                        }
                    }
                }
                case "4" -> {
                    System.out.println("==========================================================");
                    System.out.println(" Calculate cost from consumption CSV ");
                    System.out.println("==========================================================");

                    LocalDate day = askForDate(scanner);
                    String zone = askForZone(scanner);

                    String json = fetchJsonForDay(day, zone);
                    if (json != null) {
                        try {
                            PriceEntry[] prices = parseEntries(json);

                            System.out.print("Enter path to consumption CSV (e.g., consumption.csv): ");
                            String path = scanner.nextLine().trim();

                            Map<String, Double> consumption = ConsumptionCsvReader.loadConsumption(path);
                            ConsumptionCalculator.calculateTotalCost(prices, consumption);

                        } catch (Exception e) {
                            System.out.println("Failed: " + e.getMessage());
                        }
                    }
                }
                case "5" -> {
                    System.out.println("You have exited the CLI!");
                    cliActive = false;
                }

                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static LocalDate askForDate(Scanner scanner) {
        while (true) {
            System.out.println("Enter day: 'today', 'tomorrow', 'yesterday' or YYYY-MM-DD (default = today =" + "ENTER)");
            System.out.print("> ");
            String s = scanner.nextLine().trim().toLowerCase();

            if (s.isEmpty()) {
                return LocalDate.now();
            }

            switch (s) {
                case "today" -> { return LocalDate.now(); }
                case "tomorrow" -> { return LocalDate.now().plusDays(1); }
                case "yesterday" -> { return LocalDate.now().minusDays(1); }
                default -> {
                    try {
                        return LocalDate.parse(s); // ISO-format: YYYY-MM-DD
                    } catch (DateTimeParseException e) {
                        System.out.println("Wrong date.");

                    }
                }
            }
        }
    }

    private static String askForZone(Scanner scanner) {
        while (true) {
            Printers.printZoneAreas();
            String zone = scanner.nextLine().trim().toUpperCase();
            if (zone.isEmpty()) return "SE3";
            if (zone.equals("SE1") || zone.equals("SE2") || zone.equals("SE3") || zone.equals("SE4")) {
                return zone;
            }
            System.out.println("Invalid zone. Please enter SE1, SE2, SE3 or SE4.");
        }
    }

    private static String fetchJsonForDay(LocalDate day, String zone) {
        try {
        String json = fetchers.fetchPricesForDay(day, zone);
        if (json == null) {
            System.out.println("No data available for " + day + " in zone " + zone + ".");
            }
        return json;
        } catch (Exception e) {
            System.err.println("Failed to fetch prices for " + day + " (" + zone + "): " + e.getMessage());
            return null;
        }
    }

    private static PriceEntry[] parseEntries(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, PriceEntry[].class);
    }
}