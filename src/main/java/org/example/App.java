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

    /**
     * Prints a personalized farewell message to the console.
     *
     * @param userName the user's name to include in the goodbye message
     */
    private static void printGoodbyeMessage(String userName) {
        System.out.println("Goodbye " + userName + "! Thanks for using electricity scout.");
    }

    /**
     * Reads and returns an integer from the given Scanner that falls within the inclusive range [min, max].
     *
     * Repeatedly prompts the user until a valid integer in range is entered. Non-integer input is
     * detected and handled by prompting again; out-of-range integers cause the provided errorMessage
     * to be printed and a re-prompt. Consumes the remainder of the input line after a successful read.
     *
     * @param min the minimum acceptable integer (inclusive)
     * @param max the maximum acceptable integer (inclusive)
     * @param errorMessage message printed when an entered integer is outside the allowed range
     * @return a validated integer within [min, max]
     */
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

    /**
     * Reads and returns a non-empty, trimmed line from the provided Scanner.
     *
     * Repeatedly prompts the user (via the existing console prompts) until a
     * non-blank string is entered. Leading and trailing whitespace are removed
     * from the returned value.
     *
     * @return a non-empty, trimmed String entered by the user
     */
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

    /**
     * Application entry point — runs the interactive command-line interface for selecting a zone
     * and performing electricity-related actions.
     *
     * <p>The method prompts for a user name, then enters a persistent menu loop where the user
     * selects a zone and one of several actions (average price, min/max hours, EV charging optimizer,
     * all hourly prices, or CSV import). Each action delegates to corresponding service classes and
     * returns to the main menu unless the user chooses to exit. Inputs are validated and the CSV
     * import action handles its own exceptions; any unexpected exception is reported and the
     * application exits. The method ensures the input Scanner is closed on termination.
     */
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
            selectedZone = getValidIntegerInput(scanner, 1, 6, "Wrong zone selection! Please select a number between 1-6.");

            if (selectedZone == 6) {
                printGoodbyeMessage(userName);
                appIsRunning = false;
                break;
            }

            // Validate zone selection (1-5 are valid zones)
            if (selectedZone < 1 || selectedZone > 5) {
                System.out.println("Wrong zone selection! Please select a number between 1-5.");
                continue;
            }

            // --Main menu--
            Menu.spacer(20);
            Menu.mainMenu(userName, ZONE_NAMES[selectedZone]);
            selectedMainMenu = getValidIntegerInput(scanner, 1, 6, "Wrong option! Please select a number between 1-6.");

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
                    EVChargingOptimizer.showBestChargingTimes(userName, ZONE_NAMES[selectedZone], ZONE_IDS[selectedZone]);
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
                    //--CSV import and cost calculation--
                    try {
                        CSVImport.showCSVImport(userName, ZONE_NAMES[selectedZone], ZONE_IDS[selectedZone], scanner);
                        averageMenuOption = getValidIntegerInput(scanner, 1, 2, "Wrong option! Please select 1 or 2.");
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
