package org.SpinalGlitter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CLI {
    // Allowed zones
    private static final Set<String> ALLOWED_ZONES = Set.of("SE1", "SE2", "SE3", "SE4");
    // Time zone for Stockholm (Europe/Stockholm)
    private static final java.time.ZoneId Z_STHLM = java.time.ZoneId.of("Europe/Stockholm");
    // DateTimeFormatter for displaying date and time in Stockholm time zone
    private static final java.time.format.DateTimeFormatter FMT =
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    .withZone(Z_STHLM);

    public static void main(String[] args) {
        //Scanner object for user input
        Scanner input = new Scanner(System.in);
        // Variable to store the selected zone
        String zone;
        // Loop until a valid zone is selected
        while (true) {
            System.out.println("Hello! Welcome to a electricity price CLI!");
            System.out.println("--------------------------------");
            System.out.print("""
                    SE1 (Norra Sverige)
                    SE2 (Norra Mellansverige)
                    SE3 (Sädra Mellansverige)
                    SE4 (Södra Sverige)
                    Please pick the area you want see the electricity price for (default SE3):\s""");
            // Read user input, trim whitespace, and convert to uppercase
            String answer = input.nextLine().trim().toUpperCase();
            // If input is blank, default to SE3
            if (answer.isBlank()) {
                zone = "SE3";
                break;
            }
            // Check if the input is a valid zone
            if (ALLOWED_ZONES.contains(answer)) {
                zone = answer;
                break;
            }
            System.out.println("Invalid zone. Please try again.");
        }
        System.out.println("You picked zone " + zone);
        System.out.println("--------------------------------");
        //API object to fetch electricity prices
        API api = new API();
        // Main CLI loop
        boolean CliRunning = true;
        while (CliRunning) {

            System.out.println("Please choose an option:");
            System.out.println("1 Show the electricity price for today");
            System.out.println("2 Show the average electricity price for today");
            System.out.println("3 Show the electricity price for tomorrow");
            System.out.println("4 Show the average electricity price for tomorrow");
            System.out.println("5 Show the cheapest and most expensive hour for today");
            System.out.println("6 Show the the best ours to charge your car for today");
            System.out.println("7 Exit");
            System.out.print("Choose: ");
            // Read user input and trim whitespace
            String userInput = input.nextLine().trim();
            // Handle user input
            switch (userInput) {
                case "1": {
                    try {
                        var today = LocalDate.now(Z_STHLM);
                        var entries = api.fetchDay(today, zone);

                        if (entries.isEmpty()) {
                            System.out.println("No data available for today.");
                            System.out.println("--------------------------------");
                        } else {
                            System.out.println("Electricity prices for today:");
                            System.out.println("--------------------------------");
                            // Sort entries by start time and print them
                            entries.stream()
                                    .sorted(Comparator.comparing(e -> e.timeStart))
                                    .forEach(e -> System.out.printf("%s - %s : %.4f SEK/kWh%n",
                                            FMT.format(e.timeStart),
                                            FMT.format(e.timeEnd),
                                            e.sekPerKWh));
                        }
                        System.out.println("--------------------------------");
                    } catch (Exception ex) {
                        System.out.println("Error fetching data: " + ex.getMessage());
                    }
                    break;
                }

                case "2": {
                    try {
                        var today = LocalDate.now(Z_STHLM);
                        var entries = api.fetchDay(today, zone);

                        if (entries.isEmpty()) {
                            System.out.println("No data available for today.");
                        } else {
                            // Calculate and print the average price
                            double avgPrice = entries.stream()
                                    .mapToDouble(e -> e.sekPerKWh)
                                    .average()
                                    .orElse(0.0);
                            System.out.println("--------------------------------");
                            System.out.printf("Average electricity price for today: %.4f SEK/kWh%n", avgPrice);
                        }
                        System.out.println("--------------------------------");
                    } catch (Exception ex) {
                        System.out.println("Error fetching data: " + ex.getMessage());
                    }
                    break;
                }

                case "3": {
                    try {
                        var tomorrow = LocalDate.now(Z_STHLM).plusDays(1);
                        var entries = api.fetchDay(tomorrow, zone);

                        if (entries.isEmpty()) {
                            System.out.println("No data available for tomorrow.");
                            System.out.println("--------------------------------");
                        } else {
                            System.out.println("Electricity prices for tomorrow:");
                            System.out.println("--------------------------------");

                            entries.stream()
                                    .sorted(Comparator.comparing(e -> e.timeStart))
                                    .forEach(e -> System.out.printf("%s - %s : %.4f SEK/kWh%n",
                                            FMT.format(e.timeStart),
                                            FMT.format(e.timeEnd),
                                            e.sekPerKWh));
                        }
                        System.out.println("--------------------------------");
                    } catch (Exception ex) {
                        System.out.println("Error fetching data: " + ex.getMessage());
                    }
                    break;
                }

                case "4": {
                    try {
                        var tomorrow = LocalDate.now(Z_STHLM).plusDays(1);
                        var entries = api.fetchDay(tomorrow, zone);

                        if (entries.isEmpty()) {
                            System.out.println("No data available for tomorrow.");
                        } else {
                            double avgPrice = entries.stream()
                                    .mapToDouble(e -> e.sekPerKWh)
                                    .average()
                                    .orElse(0.0);
                            System.out.println("--------------------------------");
                            System.out.printf("Average electricity price for tomorrow: %.4f SEK/kWh%n", avgPrice);
                        }
                        System.out.println("--------------------------------");
                    } catch (Exception ex) {
                        System.out.println("Error fetching data: " + ex.getMessage());
                    }
                    break;
                }

                case "5": {
                    try {
                        var today = LocalDate.now(Z_STHLM);
                        var entries = api.fetchDay(today, zone);

                        if (entries.isEmpty()) {
                            System.out.println("No data available for today.");
                            System.out.println("--------------------------------");
                            break;
                        }

                        var comp = Comparator
                                .comparingDouble((PriceEntry e) -> e.sekPerKWh)
                                .thenComparing(e -> e.timeStart); // Tie-breaker: earlier time

                        PriceEntry cheapest = entries.stream()
                                .min(comp)
                                .orElseThrow();

                        PriceEntry mostExpensive = entries.stream()
                                .max(comp)
                                .orElseThrow();

                        System.out.println("--------------------------------");
                        System.out.printf("Cheapest hour: %s – %s : %.4f SEK/kWh%nMost expensive: %s – %s : %.4f SEK/kWh%n",
                                FMT.format(cheapest.timeStart),
                                FMT.format(cheapest.timeEnd),
                                cheapest.sekPerKWh,
                                FMT.format(mostExpensive.timeStart),
                                FMT.format(mostExpensive.timeEnd),
                                mostExpensive.sekPerKWh
                        );
                        System.out.println("--------------------------------");
                    } catch (Exception ex) {
                        System.out.println("Error fetching data: " + ex.getMessage());
                    }
                    break;
                }

                case "6": {
                    try {
                        var today = LocalDate.now(Z_STHLM);
                        var entries = api.fetchDay(today, zone);

                        if (entries.isEmpty()) {
                            System.out.println("No data available for today.");
                            break;
                        }
                        entries.sort(java.util.Comparator.comparing(e -> e.timeStart));

                        System.out.println("""
                                Best charging hours today:
                                --------------------------------""");
                        // Check for 2, 4, and 8 hour windows
                        for (int hours : new int[]{2, 4, 8}) {
                            // Find the best window
                            int[] range = bestWindow(entries, hours);
                            // If not enough periods, notify the user
                            if (range[0] < 0) {
                                System.out.printf("%2dh: not enough periods%n", hours);
                                System.out.println("--------------------------------");
                                continue;
                            }
                            // Get the start and end entries of the best window
                            var start = entries.get(range[0]);
                            var end = entries.get(range[1]);
                            // Calculate the average price in the best window
                            double avg = entries.subList(range[0], range[1] + 1)
                                    .stream()
                                    .mapToDouble(e -> e.sekPerKWh)
                                    .average()
                                    .orElse(Double.NaN);

                            System.out.printf("%2dh: %s – %s  (avg %.4f SEK/kWh)%n",
                                    hours, FMT.format(start.timeStart), FMT.format(end.timeEnd), avg);
                        }
                        System.out.println("--------------------------------");

                    } catch (Exception ex) {
                        System.out.println("Error fetching data: " + ex.getMessage());
                    }
                    break;
                }

                case "7": {
                    System.out.println("Exiting the CLI. Goodbye!");
                    CliRunning = false;
                    break;
                }

                default: {
                    System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    // Find the best (cheapest) contiguous window of given size
    private static int[] bestWindow(List<PriceEntry> entries, int windowSize) {
        // If not enough entries, return invalid range
        int n = entries.size();
        if (n < windowSize) return new int[]{-1, -1};
        // Calculate the sum of the first window
        double sum = 0.0;
        // Initial sum for the first 'windowSize' entries
        for (int i = 0; i < windowSize; i++) sum += entries.get(i).sekPerKWh;
        // Initialize best sum and starting index
        double bestSum = sum;
        int bestStart = 0;
        // Slide the window across the entries
        for (int i = windowSize; i < n; i++) {
            sum += entries.get(i).sekPerKWh;
            sum -= entries.get(i - windowSize).sekPerKWh;
            int start = i - windowSize + 1;
            // Update best sum and starting index if a new best is found
            if (sum < bestSum - 1e-12) {
                bestSum = sum;
                bestStart = start;
            }
        }
        // Return the starting and ending indices of the best window
        return new int[]{bestStart, bestStart + windowSize - 1};
    }
}