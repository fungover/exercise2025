package org.example;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ElectricPriceCli {

    public static void main(String[] args) throws IOException {
        // Declare List variables
        List<ElectricityPrice> pricesToday = null;
        List<ElectricityPrice> pricesTomorrow = null;

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

        System.out.println(welcomeText + "\n\n\n");

        // Fetch data for a specific zone
        boolean stmt = false;
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
                if (jsonDataToday != null) {
                    System.out.println("Prices fetched successfully for zone " + input);
                }
            } else {
                System.out.println("Invalid Zone Input");
            }
        } while (!stmt);

        // Main menu loop
        boolean running = true;
        do {
            System.out.println("Please choose an option:");
            System.out.println("\t1. Calculate and display the mean price for the current 24-hour period");
            System.out.println("\t2. Identify the cheapest and most expensive hours");
            System.out.println("\t3. Suggest optimal time to charge an electric car (2, 4, or 8 hours)");
            System.out.println("\t4. Import hourly consumption data from a CSV file");
            System.out.println("\t0. Exit");

            String choice = System.console().readLine("Enter your choice: ");

            switch (choice) {
                case "1":
                    System.out.println("Displaying mean price...\n");
                    BigDecimal meanPrice = ElectricPriceUtils.meanPrice(pricesToday, pricesTomorrow);
                    System.out.println("The mean price for today is: " + meanPrice + " SEK/kWh");
                    System.out.println("\n\n");
                    break;
                case "2":
                    System.out.println("Displaying cheapest and most expensive hours...\n");
                    ElectricPriceUtils.printCheapestAndMostExpensiveHours(pricesToday, pricesTomorrow);
                    System.out.println("\n\n");
                    break;
                case "3":
                    System.out.println("Startig optimal charging time calculator...\n");
                    ElectricPriceUtils.suggestOptimalChargingTime(pricesToday, pricesTomorrow);
                    System.out.println("\n\n");
                    break;
                case "4":
                    System.out.println("5");
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
}
