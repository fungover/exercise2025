package org.example;

import org.example.service.AveragePrice;
import org.example.service.MinMaxPrice;
import org.example.service.EVChargingOptimizer;
import org.example.service.AllHourlyPrices;
import org.example.service.CSVImport;
import org.example.ui.Menu;
import java.util.Scanner;
import java.util.InputMismatchException;

public class App {
    private static final String[] ZONE_IDS = { "", "SE3", "SE1", "SE2", "SE4" };
    private static final String[] ZONE_NAMES = { "", "Stockholm", "Luleå", "Sundsvall", "Malmö" };

    private static void printGoodbyeMessage(String userName) {
        System.out.println("Goodbye " + userName + "! Thanks for using electricity scout.");
    }

    // --user input handler--
    private static int getValidIntegerInput(Scanner scanner, int min, int max, String errorMessage) {
        int input = -1;
        boolean validInput = false;

        while (!validInput) {
            try {
                input = scanner.nextInt();
                if (input >= min && input <= max) {
                    validInput = true;
                } else {
                    System.out.println(errorMessage);
                    System.out.print("Please try again: ");
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input! Please enter a number.");
                System.out.print("Please try again: ");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return input;
    }

    private static String getValidStringInput(Scanner scanner) {
        String input = "";
        while (input.trim().isEmpty()) {
            input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                System.out.println("Name cannot be empty!");
                System.out.print("Please enter your name: ");
            }
        }
        return input.trim();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // ------------props------------
            boolean appIsRunning = true;
            String userName;
            int selectedZone = 0;
            int averageMenuOption = 0;
            int selectedMainMenu = 0;

            // --user name menu--
            Menu.spacer(20);
            Menu.userNameMenu();
            userName = getValidStringInput(scanner);

            while (appIsRunning) {
                // --zone menu---
                Menu.spacer(20);
                Menu.zoneMenu(userName);
                selectedZone = getValidIntegerInput(scanner, 1, 6,
                        "Wrong zone selection! Please select 1-4 or 6 to exit.");

                if (selectedZone == 6) {
                    printGoodbyeMessage(userName);
                    appIsRunning = false;
                    break;
                }

                // Validate zone selection (1-4 are valid zones)
                if (selectedZone < 1 || selectedZone > 4) {
                    System.out.println("Wrong zone selection! Please select a number between 1-4.");
                    continue;
                }

                // --Main menu--
                Menu.spacer(20);
                Menu.mainMenu(userName, ZONE_NAMES[selectedZone]);
                selectedMainMenu = getValidIntegerInput(scanner, 1, 6,
                        "Wrong option! Please select a number between 1-6.");

                // ------Handle user selection----
                switch (selectedMainMenu) {
                    case 1:
                        // --Show today's average electricity cost for 24H period--
                        AveragePrice.showAveragePrice(userName, ZONE_NAMES[selectedZone], ZONE_IDS[selectedZone]);
                        averageMenuOption = getValidIntegerInput(scanner, 1, 2, "Wrong option! Please select 1 or 2.");
                        if (averageMenuOption == 2) {
                            printGoodbyeMessage(userName);
                            appIsRunning = false;
                            break;
                        }
                        break;
                    case 2:
                        // --Find cheapest and most expensive hours--
                        MinMaxPrice.showMinMaxPrices(userName, ZONE_NAMES[selectedZone], ZONE_IDS[selectedZone]);
                        averageMenuOption = getValidIntegerInput(scanner, 1, 2, "Wrong option! Please select 1 or 2.");
                        if (averageMenuOption == 2) {
                            printGoodbyeMessage(userName);
                            appIsRunning = false;
                            break;
                        }
                        break;
                    case 3:
                        // --Find best EV charging times--
                        EVChargingOptimizer.showBestChargingTimes(userName, ZONE_NAMES[selectedZone],
                                ZONE_IDS[selectedZone]);
                        averageMenuOption = getValidIntegerInput(scanner, 1, 2, "Wrong option! Please select 1 or 2.");
                        if (averageMenuOption == 2) {
                            printGoodbyeMessage(userName);
                            appIsRunning = false;
                            break;
                        }
                        break;
                    case 4:
                        // --Show all hourly prices--
                        AllHourlyPrices.showAllHourlyPrices(userName, ZONE_NAMES[selectedZone], ZONE_IDS[selectedZone]);
                        averageMenuOption = getValidIntegerInput(scanner, 1, 2, "Wrong option! Please select 1 or 2.");
                        if (averageMenuOption == 2) {
                            printGoodbyeMessage(userName);
                            appIsRunning = false;
                            break;
                        }
                        break;
                    case 5:
                        // --CSV import and cost calculation--
                        try {
                            CSVImport.showCSVImport(userName, ZONE_NAMES[selectedZone], ZONE_IDS[selectedZone],
                                    scanner);
                            averageMenuOption = getValidIntegerInput(scanner, 1, 2,
                                    "Wrong option! Please select 1 or 2.");
                            if (averageMenuOption == 2) {
                                printGoodbyeMessage(userName);
                                appIsRunning = false;
                                break;
                            }
                        } catch (Exception e) {
                            System.out.println("Error with CSV import: " + e.getMessage());
                            System.out.println("Returning to main menu...");
                        }
                        break;
                    case 6:
                        printGoodbyeMessage(userName);
                        appIsRunning = false;
                        break;
                    default:
                        System.out.println("Invalid option! Please select a number between 1-6.");
                }
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            System.out.println("The application will now exit.");
        } finally {
            scanner.close();
        }
    }

}
