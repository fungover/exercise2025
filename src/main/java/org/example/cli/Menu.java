package org.example.cli;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.List;
import java.time.LocalDate;

import org.example.model.BestChargingTimes;
import org.example.model.PriceHour;
import org.example.service.ElectricityPriceService;
import org.example.utils.FormatUtil;
import org.example.utils.StatsUtil;

public class Menu {

    private final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter InputDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public void start() {
        boolean start = true;

        while (start) {
            System.out.println("\n*************************************************");
            System.out.println("*** Welcome! We check prices for electricity! ***");
            System.out.println("*************************************************");
            System.out.println("\nSelect a zone to continue!");
            System.out.println("(Press [1]-[5] and then press Enter):");
            System.out.println("\n[1] SE1 = Luleå / Northern Sweden");
            System.out.println("[2] SE2 = Sundsvall / Northern Central Sweden");
            System.out.println("[3] SE3 = Stockholm / Southern Central Sweden");
            System.out.println("[4] SE4 = Malmö / Southern Sweden");
            System.out.println("[5] Exit");

            int mainMenuChoice = readIntInRange(scanner, 1, 5);

            switch (mainMenuChoice) {
                case 1, 2, 3, 4 -> zoneMenu(zoneCodeFromChoice(mainMenuChoice));
                case 5 -> start = false;

            }
        }
            // Closing program dot-"animation" (...)
        System.out.print("Closing program");
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.print(".");
        }
        System.out.println();

    }

    private int readIntInRange(Scanner sc, int min, int max) {
        while (true) {
            if (sc.hasNextInt()) {
                int v = sc.nextInt();
                sc.nextLine();
                if (v >= min && v <= max) return v;
            } else  {
                sc.nextLine();
            }
            System.out.println("Please select from the menu [" + min + "]-[" + max + "] and press Enter.");
        }
    }

    private String zoneCodeFromChoice(int choice) {
        return switch (choice) {
            case 1 -> "SE1";
            case 2 -> "SE2";
            case 3 -> "SE3";
            case 4 -> "SE4";
            default -> null; // 5 = Exit
        };
    }

    private String zoneName(String code) {
        return switch (code) {
            case "SE1" -> "Luleå / Northern Sweden";
            case "SE2" -> "Sundsvall / Northern Central Sweden";
            case "SE3" -> "Stockholm / Southern Central Sweden";
            case "SE4" -> "Malmö / Southern Sweden";
            default -> "";
        };
    }

    private void zoneMenu(String zoneCode) {
        boolean back = false;
        while (!back) {
            System.out.println("\n*** Zone: " + zoneCode +  " (" + zoneName(zoneCode) + ") " + " ***");
            System.out.println("[1] Today (24-hours + Summary)");
            System.out.println("[2] Tomorrow (if available)");
            System.out.println("[3] Search (date)");
            System.out.println("[4] Back");

            int choice = readIntInRange(scanner, 1, 4);
            switch (choice) {
                case 1 -> showDay(zoneCode, LocalDate.now());
                case 2 -> showDay(zoneCode, LocalDate.now().plusDays(1));
                case 3 -> {
                    boolean searching = true;
                    while (searching) {
                        System.out.println("Enter date (YYYY-MM-DD), or 'b' to go back: ");
                        String searchDateInput = scanner.nextLine().trim();

                        if (searchDateInput.equalsIgnoreCase("b")) {
                            searching = false;
                        } else {
                            try {
                                LocalDate date = LocalDate.parse(searchDateInput, InputDateFormatter);
                                showDay(zoneCode, date);
                                searching = false;
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid date format. Please use YYYY-MM-DD (example: 2024-02-10)");
                            }
                        }
                    }

                }
                case 4 -> back = true;
            }
        }
    }
    private void showDay(String zoneCode, LocalDate date) {
        ElectricityPriceService service = new ElectricityPriceService();
        List<PriceHour> hours = service.fetchByDate(zoneCode, date);

        if (hours.isEmpty()) {
            System.out.println("*** No prices available for " + zoneCode +  " (" + zoneName(zoneCode) + ") " +
                    " on " + date + " (maybe not published yet). ***");
            return;
        }
        for (PriceHour hour : hours) {
            System.out.println("____________________________________________________" + "\n Zone: " +
                    zoneCode + " (" + zoneName(zoneCode) + ") " + "\n Date: " +
                    FormatUtil.formatDate(hour.time_start()) + "\n Time: " +
                    FormatUtil.formatTime(hour.time_start()) + " - " +
                    FormatUtil.formatTime(hour.time_end()) + "\n SEK: " +
                    FormatUtil.formatPriceSEK(hour.SEK_per_kWh()) + "\n EUR: " +
                    FormatUtil.formatPriceEUR(hour.EUR_per_kWh()) + "\n Exchange rate: " +
                    FormatUtil.formatEXR(hour.EXR())
            );
        }
        double avgSEK = StatsUtil.calcAverageSek(hours);
        double avgEUR = StatsUtil.calcMeanPricesEUR(hours);
        PriceHour cheapest = StatsUtil.findCheapestSek(hours);
        PriceHour mostExpensive = StatsUtil.findMostExpensiveSek(hours);

        System.out.println("____________________________________________________" + "\n * SUMMARY: *" +
                "\n Zone: " + zoneCode + " (" + zoneName(zoneCode) + ") " + "\n Date: " +
                FormatUtil.formatDate(hours.get(0).time_start()) + "\n Average: " +
                FormatUtil.formatPriceSEK(avgSEK) + " | " +
                FormatUtil.formatPriceEUR(avgEUR) + "\n Cheapest hour: " +
                FormatUtil.formatTime(cheapest.time_start()) + "-" +
                FormatUtil.formatTime(cheapest.time_end()) +
                " (" + FormatUtil.formatPriceSEK(cheapest.SEK_per_kWh()) + " | " +
                FormatUtil.formatPriceEUR(cheapest.EUR_per_kWh()) + ")" +
                "\n Most expensive hour: " + FormatUtil.formatTime(mostExpensive.time_start()) +
                "-" + FormatUtil.formatTime(mostExpensive.time_end()) +
                " (" + FormatUtil.formatPriceSEK(mostExpensive.SEK_per_kWh()) + " | " +
                FormatUtil.formatPriceEUR(mostExpensive.EUR_per_kWh()) +")"
        );

        System.out.println("\n Cheapest charging hours:");
        printBestHours(hours, StatsUtil.findBestWindow(hours, 2));
        printBestHours(hours, StatsUtil.findBestWindow(hours, 4));
        printBestHours(hours, StatsUtil.findBestWindow(hours, 8));
        System.out.println("____________________________________________________");
    }

    private void printBestHours(List<PriceHour> hours, BestChargingTimes.BestWindow w) {
        if (w == null) return;
        var start = hours.get(w.startIndex());
        var end = hours.get(w.startIndex() + w.length() - 1);

        double totalSek = w.totalSek();
        double totalEur = calcBestHoursToEuro(hours, w);

        System.out.println(" " + w.length() + " hours: " +
                FormatUtil.formatTime(start.time_start()) + "-" +
                FormatUtil.formatTime(end.time_end()) + " Total: ~" +
                FormatUtil.formatAmountSEK(totalSek) + " | ~" + FormatUtil.formatAmountEUR(totalEur)
        );

    }

    private double calcBestHoursToEuro(List<PriceHour> hours, BestChargingTimes.BestWindow w) {
        double sum = 0.0;
        for (int i = w.startIndex(); i < w.startIndex() + w.length(); i++) {
            sum += hours.get(i).EUR_per_kWh();
        }
        return sum;
    }

}
