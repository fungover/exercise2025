package org.example;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import java.util.stream.Stream;

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
                    System.out.println(LocalDate.now());
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
                    System.out.println("\n\n");
                    break;
                case "2":
                    System.out.println("2");
                    if (pricesToday != null) {
                        BigDecimal meanPrice = meanPrice();
                        System.out.println("The mean price for today is: " + meanPrice + " SEK/kWh");
                    } else {
                        System.out.println("You need to download the prices first (option 1)");
                    }
                    System.out.println("\n\n");
                    break;
                case "3":
                    System.out.println("3");
                    if (pricesToday != null) {
                        printCheapestAndMostExpensiveHours();
                    } else {
                        System.out.println("You need to download the prices first (option 1)");
                    }
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
     * Calculate the mean price for the current 24-hour period (if tomorrow's data is available, it will be used to calculate the mean price for the next 24 hours).
     *
     * @return Returns the mean price as a BigDecimal
     */
    public static BigDecimal meanPrice() {
        BigDecimal meanPrice;
        if (pricesTomorrow != null) {
            ZonedDateTime start = ZonedDateTime.now();
            ZonedDateTime end = start.plusHours(24);

            // create a formatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Filter prices from now to the end of this day.
            List<ElectricityPrice> remainingEleToday = pricesToday.stream()
                    .filter(p -> {
                        ZonedDateTime priceTime = ZonedDateTime.parse(p.time_start());
                        return !priceTime.isBefore(start);
                    }).toList();

            // Filter prices from the next day to the end of your 24h period.
            List<ElectricityPrice> remainingEleUnderPeriod = pricesTomorrow.stream()
                    .filter(p -> {
                        ZonedDateTime priceTime = ZonedDateTime.parse(p.time_end());
                        return !priceTime.isAfter(end);
                    }).toList();
            // Combine the two lists
            List<ElectricityPrice> newList = Stream.concat(remainingEleToday.stream(), remainingEleUnderPeriod.stream())
                    .toList();

            meanPrice = meanPriceCalculator(newList);

        } else {
            meanPrice = meanPriceCalculator(pricesToday);
            /*}*/
        }
        return meanPrice;
    }

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

    /**
     * Identify and print the cheapest and most expensive hours from the combined list of today's and tomorrow's prices.
     * If tomorrow's prices are not available, only today's prices will be considered.
     */
    public static void printCheapestAndMostExpensiveHours() {
        List<ElectricityPrice> allPrices = pricesToday;
        if (pricesTomorrow != null) {
            allPrices = Stream.concat(pricesToday.stream(), pricesTomorrow.stream()).toList();
        }

        if (allPrices.isEmpty()) {
            System.out.println("No price data available.");
            return;
        }

        // Get the cheapest hour and most expensive hour
        ElectricityPrice cheapest = allPrices.stream()
                .min(Comparator.comparing(ElectricityPrice::SEK_per_kWh))
                .get();

        ElectricityPrice mostExpensive = allPrices.stream()
                .max(Comparator.comparing(ElectricityPrice::SEK_per_kWh))
                .get();

        // Format time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String cheapestTime = ZonedDateTime.parse(cheapest.time_start()).format(formatter);
        String mostExpensiveTime = ZonedDateTime.parse(mostExpensive.time_start()).format(formatter);

        System.out.printf("Cheapest hour is: %s with a price of: %.5f SEK/kWh%n", cheapestTime, cheapest.SEK_per_kWh());
        System.out.printf("Most expensive hour is %s with a price of %.5f SEK/kWh%n", mostExpensiveTime, mostExpensive.SEK_per_kWh());
    }
}

