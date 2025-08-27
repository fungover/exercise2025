package org.SpinalGlitter;

import java.util.Scanner;
import java.util.Set;

public class CLI {
    private static final Set<String> ALLOWED_ZONES = Set.of("SE1", "SE2", "SE3", "SE4");

    static void main() {

        Scanner input = new Scanner(System.in);
        String zone;

        while (true) {
            System.out.println("Hello! Welcome to a electricity price CLI!");
            System.out.println("--------------------------------");
            System.out.print("""
                    SE1 (Norra Sverige)
                    SE2 (Norra Mellansverige)
                    SE3 (Sädra Mellansverige)
                    SE4 (Södra Sverige)
                    Please pick the area you want see the electricity price for (default SE3):\s""");
            String answer = input.nextLine().trim().toUpperCase();

            if (answer.isBlank()) {
                zone = "SE3";
                break;
            }
            if (ALLOWED_ZONES.contains(answer)) {
                zone = answer;
                break;
            }
            System.out.println("Invalid zone. Please try again.");
        }
        System.out.println("You picked zone " + zone);
        System.out.println("--------------------------------");

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

            String userInput = input.nextLine().trim();

            switch (userInput) {
                case "1": {
                    try {
                        API api = new API();
                        var today = java.time.LocalDate.now(java.time.ZoneId.of("Europe/Stockholm"));
                        var entries = api.fetchDay(today, zone);

                        if (entries.isEmpty()) {
                            System.out.println("No data available for today.");
                            System.out.println("--------------------------------");
                        } else {
                            System.out.println("Electricity prices for today:");
                            System.out.println("--------------------------------");
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
                        System.out.println("--------------------------------");
                    } catch (Exception ex) {
                        System.out.println("Error fetching data: " + ex.getMessage());
                    }
                    break;
                }

                case "2": {
                    try {
                        API api = new API();
                        var today = java.time.LocalDate.now(java.time.ZoneId.of("Europe/Stockholm"));
                        var entries = api.fetchDay(today, zone);

                        if (entries.isEmpty()) {
                            System.out.println("No data available for today.");
                        } else {
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
                        API api = new API();
                        var tomorrow = java.time.LocalDate.now(java.time.ZoneId.of("Europe/Stockholm")).plusDays(1);
                        var entries = api.fetchDay(tomorrow, zone);

                        if (entries.isEmpty()) {
                            System.out.println("No data available for tomorrow.");
                            System.out.println("--------------------------------");
                        } else {
                            System.out.println("Electricity prices for tomorrow:");
                            System.out.println("--------------------------------");
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
                        System.out.println("--------------------------------");
                    } catch (Exception ex) {
                        System.out.println("Error fetching data: " + ex.getMessage());
                    }
                    break;
                }

                case "4": {
                    try {
                        API api = new API();
                        var tomorrow = java.time.LocalDate.now(java.time.ZoneId.of("Europe/Stockholm")).plusDays(1);
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
                        API api = new API();
                        var today = java.time.LocalDate.now(java.time.ZoneId.of("Europe/Stockholm"));
                        var entries = api.fetchDay(today, zone);

                        if (entries.isEmpty()) {
                            System.out.println("No data available for today.");
                            System.out.println("--------------------------------");
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
                        System.out.println("--------------------------------");
                        System.out.printf("Cheapest hour: %s – %s : %.4f SEK/kWh%nMost expensive: %s – %s : %.4f SEK/kWh%n",
                                fmt.format(cheapest.timeStart),
                                fmt.format(cheapest.timeEnd),
                                cheapest.sekPerKWh,
                                fmt.format(mostExpensive.timeStart),
                                fmt.format(mostExpensive.timeEnd),
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
                        API api = new API();
                        var today = java.time.LocalDate.now(java.time.ZoneId.of("Europe/Stockholm"));
                        var entries = api.fetchDay(today, zone);

                        if (entries.isEmpty()) {
                            System.out.println("No data available for today.");
                            break;
                        }
                        entries.sort(java.util.Comparator.comparing(e -> e.timeStart));

                        int periodsPerHour = periodsPerHour(entries);

                        var fmt = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                                .withZone(java.time.ZoneId.of("Europe/Stockholm"));

                        System.out.println("""
                                Best charging hours today:
                                --------------------------------""");
                        for (int hours : new int[]{2, 4, 8}) {
                            int window = hours * periodsPerHour;
                            int[] range = bestWindow(entries, window);

                            if (range[0] < 0) {
                                System.out.printf("%2dh: not enough periods%n", hours);
                                System.out.println("--------------------------------");
                                continue;
                            }

                            var start = entries.get(range[0]);
                            var end = entries.get(range[1]);

                            double avg = entries.subList(range[0], range[1] + 1)
                                    .stream()
                                    .mapToDouble(e -> e.sekPerKWh)
                                    .average()
                                    .orElse(Double.NaN);

                            System.out.printf("%2dh: %s – %s  (avg %.4f SEK/kWh)%n",
                                    hours, fmt.format(start.timeStart), fmt.format(end.timeEnd), avg);
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

    private static int periodsPerHour(java.util.List<PriceEntry> entries) {
        return entries.size() >= 48 ? 4 : 1;
    }

    private static int[] bestWindow(java.util.List<PriceEntry> entries, int windowSize) {
        int n = entries.size();
        if (n < windowSize) return new int[]{-1, -1};

        double sum = 0.0;
        for (int i = 0; i < windowSize; i++) sum += entries.get(i).sekPerKWh;

        double bestSum = sum;
        int bestStart = 0;

        for (int i = windowSize; i < n; i++) {
            sum += entries.get(i).sekPerKWh;
            sum -= entries.get(i - windowSize).sekPerKWh;
            int start = i - windowSize + 1;

            if (sum < bestSum - 1e-12) {
                bestSum = sum;
                bestStart = start;
            }
        }
        return new int[]{bestStart, bestStart + windowSize - 1};
    }
}