package org.example;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class ElectricPriceCli {
    private static List<ElectricityPrice> pricesToday;
    private static List<ElectricityPrice> pricesTomorrow;

    public static void main(String[] args) throws IOException {
        String welcomeText = """
                ====================================================
                          Welcome to ElPrice CLI!
                ====================================================
                Hello! This tool helps you make smart decisions
                about electricity usage. Find out the cheapest hours
                to charge your electric car or simply save on energy costs.
                Let's get started!
                ====================================================
                """;

        System.out.println(welcomeText);

        boolean running = true;

        do {
            System.out.println("Please choose an option:");
            System.out.println("1. Download prices for today and tomorrow (if tommorow is not available only today will be downloaded)");
            System.out.println("2. Calculate and display the mean price for the current 24-hour period");
            System.out.println("3. Identify the cheapest and most expensive hours");
            System.out.println("4. Suggest optimal time to charge an electric car (2, 4, or 8 hours)");
            System.out.println("5. Select a price zone (SE1/SE2/SE3/SE4)");
            System.out.println("6. Import hourly consumption data from a CSV file");
            System.out.println("0. Exit");
            
            String choice = System.console().readLine("Enter your choice: ");

            switch (choice) {
                case "1":
                    System.out.println("1");
                    boolean stmt = false;
                    /*String[] parts;*/
                    do {
                        String input = System.console().readLine("Which zone do you want to fetch prices for? (SE1, SE2, SE3, SE4) ");
                        if (input.matches("SE1|SE2|SE3|SE4")) {

                            String jsonDataToday = GetDataFromAPI.fetchDataPrices(LocalDate.now(), input);
                            String jsonDataTomorrow = GetDataFromAPI.fetchDataPrices(LocalDate.now().plusDays(1), input);

                            if (jsonDataToday != null) {
                                stmt = true;
                                pricesToday = GetDataFromAPI.ConvertDataToObjects(jsonDataToday);
                            }
                            if (jsonDataTomorrow != null) {
                                pricesTomorrow = GetDataFromAPI.ConvertDataToObjects(jsonDataTomorrow);
                            }
                            System.out.println("Prices fetched successfully for zone " + input);
                        } else {
                            System.out.println("Invalid Zone Input");
                        }
                    } while (!stmt);
                    /*if (input.equals("s")) {
                        do {
                            String userInput = System.console().readLine("Enter Date and Zone: (yyyy-mm--dd-SEn): ");
                            parts = userInput.split("-");

                            // isValidInput checks if input is valid (returns true or false)
                            stmt = isValidInput(parts);
                            if (stmt) {
                                String jsonData = GetDataFromAPI.fetchDataPrices(LocalDate.of(
                                        Integer.parseInt(parts[0]),
                                        Integer.parseInt(parts[1]),
                                        Integer.parseInt(parts[2])
                                ), parts[3]);
                                List<ElectricityPrice> pricesInputDay = GetDataFromAPI.ConvertDataToObjects(jsonData);
                                for (ElectricityPrice price : pricesInputDay) {
                                    System.out.println(price.SEK_per_kWh());
                                }
                            }

                        } while (!stmt);
                        for (String part : parts) {
                            System.out.println(part);
                        }
                    } else if (input.equals("t")) {
                        String zoneInput = System.console().readLine("Enter Zone (SE1, SE2, SE3, SE4): ");
                        if (zoneInput.matches("SE[1-4]")) {
                            System.out.println("Invalid Zone Input");
                            String jsonData = GetDataFromAPI.fetchDataPrices(LocalDate.now(), zoneInput);
                            List<ElectricityPrice> pricesToday = GetDataFromAPI.ConvertDataToObjects(jsonData);
                            for (ElectricityPrice price : pricesToday) {
                                System.out.println(price);
                            }
                        }
                    } else {
                        System.out.println("You have chosen an incorrect alternative, please try again.\n\n");
                    }*/

                    System.out.println("\n\n");
                    break;
                case "2":
                    System.out.println("2");
                    if (pricesToday != null) {
                        BigDecimal meanPrice = meanPriceCalculator(pricesToday);
                        System.out.println("The mean price for today is: " + meanPrice + " SEK/kWh");
                    } else {
                        System.out.println("You need to download the prices first (option 1)");
                    }
                    System.out.println("\n\n");
                    break;
                case "3":
                    System.out.println("3");
                    System.out.println("\n\n");
                    break;
                case "4":
                    System.out.println("4");
                    System.out.println("\n\n");
                    break;
                case "5":
                    System.out.println("5");
                    System.out.println("\n\n");
                    break;
                case "6":
                    System.out.println("6");
                    System.out.println("\n\n");
                    break;
                case "0":
                    running = false;
                    System.out.println("You have exited successfully");
                    System.out.println("\n\n");
                    break;
                default:
                    System.out.println("You have chosen an incorrect alternative, please try again.\n\n");

            }
        }
        while (running);
    }

    /*static boolean isValidInput(String[] input) {
        boolean stmt = false;
        if (input.length == 4) {
            int currentYear = LocalDate.now().getYear();
            int inputYear = Integer.parseInt(input[0]);

            if (input[0].matches("\\d{4}") && (inputYear <= currentYear) && (inputYear >= (currentYear - 2))) {
                if (input[1].matches("\\d{2}") && input[1].matches("0[1-9]|1[0-2]")) {
                    if (input[2].matches("\\d{2}") && input[2].matches("0[1-9]|[12][0-9]|3[01]")) {
                        if (input[3].matches("SE[1-4]")) {
                            stmt = true;
                        }
                    }
                }
            }
        }
        if (!stmt) {
            System.out.println("You have entered an incorrect date or zone, please try again.");
        }
        return stmt;
    }*/

    /**
     * Calculate the mean price from a list of ElectricityPrice objects
     *
     * @param prices List of ElectricityPrice objects
     * @return Returns the mean price as a BigDecimal
     */
    public static BigDecimal meanPriceCalculator(List<ElectricityPrice> prices) {
        BigDecimal sum = BigDecimal.ZERO;
        System.out.println(sum);
        for (ElectricityPrice price : prices) {
            sum = sum.add(BigDecimal.valueOf(price.SEK_per_kWh()));
        }

        return sum.divide(BigDecimal.valueOf(prices.size()), 5, RoundingMode.HALF_UP);
    }
}
