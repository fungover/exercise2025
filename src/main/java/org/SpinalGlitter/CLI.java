package org.SpinalGlitter;

import java.util.Scanner;
import java.util.Set;

public class CLI {
    private static final Set<String> ALLOWED_ZONES = Set.of("SE1", "SE2", "SE3", "SE4");//Will be used later

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        //String zone = askZone(input);


        boolean CliRunning = true;
        while (CliRunning) {

            // Display the options for the user.
            System.out.println("Hello! Welcome to SpinalGlitter CLI!");
            //System.out.println("Zone: " + zone);
            System.out.println("Please choose an option:");
            System.out.println("1 Show the electricity price for today");
            System.out.println("2 Show the average electricity price for today");
            System.out.println("3 Show the electricity price for tomorrow");
            System.out.println("4 Show the average electricity price for tomorrow");
            System.out.println("5 Show the cheapest and most expensive hour for today");
            System.out.println("6 Show the the best ours to charge your car for today");
            System.out.println("7 Exit");
            System.out.print("Choose: ");

            // Read the user input
            String userInput = input.nextLine().trim();

            // Process the user input
            switch (userInput) {
                case "1": {
                    try {
                        API api = new API();
                        var today = java.time.LocalDate.now(java.time.ZoneId.of("Europe/Stockholm"));
                        var entries = api.fetchDay(today, "SE3");// TODO: Change to users chosen zone

                        if (entries.isEmpty()) {
                            System.out.println("No data available for today.");
                        } else {
                            java.time.format.DateTimeFormatter fmt =
                                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                                            .withZone(java.time.ZoneId.of("Europe/Stockholm"));

                            entries.stream()
                                    .sorted(java.util.Comparator.comparing(e -> e.timeStart))
                                    .forEach(e -> System.out.printf("%s - %s : %.4f SEK/kWh%n",
                                            fmt.format(e.timeStart),
                                            fmt.format(e.timeEnd),
                                            e.sekPerKWh));
                        }
                    } catch (Exception ex) {
                        System.out.println("Error fetching data: " + ex.getMessage());
                    }
                    return;
                }

                case "2": {
                    try {
                        API api = new API();
                        var today = java.time.LocalDate.now(java.time.ZoneId.of("Europe/Stockholm"));
                        var entries = api.fetchDay(today, "SE3");// TODO: Change to users chosen zone

                        if (entries.isEmpty()) {
                            System.out.println("No data available for today.");
                        } else {
                            double avgPrice = entries.stream()
                                    .mapToDouble(e -> e.sekPerKWh)
                                    .average()
                                    .orElse(0.0);

                            System.out.printf("Average electricity price for today: %.6f SEK/kWh%n", avgPrice);
                        }
                    } catch (Exception ex) {
                        System.out.println("Error fetching data: " + ex.getMessage());
                    }
                    return;
                }

                case "3": {
                    try {
                        API api = new API();
                        var tomorrow = java.time.LocalDate.now(java.time.ZoneId.of("Europe/Stockholm")).plusDays(1);
                        var entries = api.fetchDay(tomorrow, "SE3");// TODO: Change to users chosen zone

                        if (entries.isEmpty()) {
                            System.out.println("No data available for tomorrow.");
                        } else {
                            java.time.format.DateTimeFormatter fmt =
                                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                                            .withZone(java.time.ZoneId.of("Europe/Stockholm"));

                            entries.stream()
                                    .sorted(java.util.Comparator.comparing(e -> e.timeStart))
                                    .forEach(e -> System.out.printf("%s - %s : %.4f SEK/kWh%n",
                                            fmt.format(e.timeStart),
                                            fmt.format(e.timeEnd),
                                            e.sekPerKWh));
                        }
                    } catch (Exception ex) {
                        System.out.println("Error fetching data: " + ex.getMessage());
                    }
                    return;
                }

                case "4": {
                    try {
                        API api = new API();
                        var tomorrow = java.time.LocalDate.now(java.time.ZoneId.of("Europe/Stockholm")).plusDays(1);
                        var entries = api.fetchDay(tomorrow, "SE3");// TODO: Change to users chosen zone

                        if (entries.isEmpty()) {
                            System.out.println("No data available for today.");
                        } else {
                            double avgPrice = entries.stream()
                                    .mapToDouble(e -> e.sekPerKWh)
                                    .average()
                                    .orElse(0.0);

                            System.out.printf("Average electricity price for tomorrow: %.6f SEK/kWh%n", avgPrice);
                        }
                    } catch (Exception ex) {
                        System.out.println("Error fetching data: " + ex.getMessage());
                    }
                    return;
                }

                case "5": {
                    try {
                        API api = new API();
                        var today = java.time.LocalDate.now(java.time.ZoneId.of("Europe/Stockholm"));
                        var entries = api.fetchDay(today, "SE3");// TODO: Change to users chosen zone

                        if (entries.isEmpty()) {
                            System.out.println("No data available for today.");
                            break;
                        }

                        var comp = java.util.Comparator
                                .comparingDouble((PriceEntry e) -> e.sekPerKWh)
                                .thenComparing(e -> e.timeStart);

                        PriceEntry cheapest = entries.stream()
                                .min(comp)
                                .orElseThrow();

                        PriceEntry mostExpensive = entries.stream()
                                .max(comp)
                                .orElseThrow();

                        var fmt = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                                .withZone(java.time.ZoneId.of("Europe/Stockholm"));

                        System.out.printf("Cheapest hour: %s – %s : %.4f SEK/kWh%nMost exp.: %s – %s : %.4f SEK/kWh%n",
                                fmt.format(cheapest.timeStart),
                                fmt.format(cheapest.timeEnd),
                                cheapest.sekPerKWh,
                                fmt.format(mostExpensive.timeStart),
                                fmt.format(mostExpensive.timeEnd),
                                mostExpensive.sekPerKWh
                                );

                    } catch (Exception ex) {
                        System.out.println("Error fetching data: " + ex.getMessage());
                    }
                }

                case "6": {
                    System.out.println("You chose option 6: Show the the best ours to charge your car for today.");
                    return;
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
}