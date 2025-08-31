package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Menu {

    public static void showDefaultMenu() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        do {
            System.out.println("Menu: (1, 2, 3, 4) as choices:");
            System.out.println("1. Download prices for the current day and the next day (if available)");
            System.out.println("2. Print the mean price for the current 24-hour period.");
            System.out.println("3. Identify and print the hours with the cheapest and most expensive prices.");
            System.out.println("4. Determine the best time to charge an electric car for durations of 2, 4, or 8 hours.");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    isRunning = false;
                    showMenuForDownloadingPrices(scanner);
                    break;
                case 2:
                    showMenuForMeanPrice(scanner);
                    break;
                case 3:
                    // Identify and print hours with cheapest and most expensive hours;
                    break;
                case 4:
                    showMenuForBestChargingHoursFromNow(scanner);
                    break;
                default:
                    System.out.println("Invalid choice. You must enter a valid number.");
            }

        } while (isRunning);

    }

    public static void showMenuForBestChargingHoursFromNow(Scanner scanner) throws IOException, InterruptedException {
        boolean isRunning = true;

        do {
            System.out.println("Enter what zone you want to see the mean price for:");
            System.out.println("SE1 = Luleå / Norra Sverige");
            System.out.println("SE2 = Sundsvall / Norra Mellansverige");
            System.out.println("SE3 = Stockholm / Södra Mellansverige");
            System.out.println("SE4 = Malmö / Södra Sverige");

            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "SE1", "SE2", "SE3", "SE4" -> {
                    System.out.println("Enter the duration you want to check for: ");
                    int windowSize = Integer.parseInt(scanner.nextLine());

                   if (windowSize == 2 || windowSize == 4 || windowSize == 8) {
                        ElectricityPriceAnalyzer.printBestChargingHoursFromNow(choice, windowSize);
                        isRunning = false;
                    } else {
                        System.out.println("Invalid choice. Duration must be 2, 4, or 8.");
                    }
                }
                default -> System.out.println("Invalid choice. You must enter a valid zone.");
            }

        } while (isRunning);
    }

    public static void showMenuForMeanPrice(Scanner scanner) throws IOException, InterruptedException {
        boolean isRunning = true;

        do {
            System.out.println("Enter what zone you want to see the mean price for:");
            System.out.println("SE1 = Luleå / Norra Sverige");
            System.out.println("SE2 = Sundsvall / Norra Mellansverige");
            System.out.println("SE3 = Stockholm / Södra Mellansverige");
            System.out.println("SE4 = Malmö / Södra Sverige");

            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "SE1", "SE2", "SE3", "SE4" -> {
                    isRunning = false;
                    ElectricityPriceAnalyzer.printMeanPriceForToday(choice);
                }
                default -> System.out.println("Invalid choice. You must enter a valid zone.");
            }

        } while (isRunning);
    }


    public static void showMenuForDownloadingPrices(Scanner scanner) throws IOException, InterruptedException {
        boolean isRunning = true;

        do {
            System.out.println("Enter what zone you want to download prices for:");
            System.out.println("SE1 = Luleå / Norra Sverige");
            System.out.println("SE2 = Sundsvall / Norra Mellansverige");
            System.out.println("SE3 = Stockholm / Södra Mellansverige");
            System.out.println("SE4 = Malmö / Södra Sverige");

            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "SE1", "SE2", "SE3", "SE4" -> {
                    isRunning = false;
                    ElectricityPriceFetcher.downloadTodayAndTomorrow(choice);
                }
                default -> System.out.println("Invalid choice. You must enter a valid zone.");
            }

        } while (isRunning);
    }
}

