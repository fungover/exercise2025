package org.example.cli;

import java.util.Scanner;
import java.util.List;

import org.example.model.PriceHour;
import org.example.service.ElectricityPriceService;

public class Menu {

    private final Scanner scanner = new Scanner(System.in);

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

    private void zoneMenu(String zoneCode) {
        boolean back = false;
        while (!back) {
            System.out.println("\n*** Zone: " + zoneCode + " ***");
            System.out.println("[1] Today (summary + all hours)");
            System.out.println("[2] Tomorrow (if available)");
            System.out.println("[3] Search (date)");
            System.out.println("[4] Back");

            int choice = readIntInRange(scanner, 1, 4);
            switch (choice) {
                case 1 -> {
                    ElectricityPriceService service = new ElectricityPriceService();
                    List<PriceHour> hours = service.fetchToday(zoneCode);
                    if (!hours.isEmpty()) {
                        System.out.println("Fetched hours: " + hours.size());
                        System.out.println(hours.get(0));
                    }
                }
                case 2 -> System.out.println("Tomorrow for " + zoneCode);
                case 3 -> System.out.println("Search by date for " + zoneCode);
                case 4 -> back = true;
            }
        }
    }

}
