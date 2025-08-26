package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class ElectricPriceUtils {
    /**
     * Calculate the mean price for the current 24-hour period (if tomorrow's data is available, it will be used to calculate the mean price for the next 24 hours).
     *
     * @param pricesToday    The list of prices for today
     * @param pricesTomorrow The list of prices for tomorrow (can be null)
     * @return Returns the mean price as a BigDecimal
     */
    public static BigDecimal meanPrice(List<ElectricityPrice> pricesToday, List<ElectricityPrice> pricesTomorrow) {
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
            List<ElectricityPrice> filteredList = Stream.concat(remainingEleToday.stream(), remainingEleUnderPeriod.stream())
                    .toList();

            meanPrice = meanPriceCalculator(filteredList);

        } else {
            meanPrice = meanPriceCalculator(pricesToday);
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
        for (ElectricityPrice price : prices) {
            sum = sum.add(BigDecimal.valueOf(price.SEK_per_kWh()));
        }

        return sum.divide(BigDecimal.valueOf(prices.size()), 5, RoundingMode.HALF_UP);
    }

    /**
     * Identify and print the cheapest and most expensive hours from the combined list of today's and tomorrow's prices.
     * If tomorrow's prices are not available, only today's prices will be considered.
     *
     * @param pricesToday    The list of prices for today
     * @param pricesTomorrow The list of prices for tomorrow (can be null)
     */
    public static void printCheapestAndMostExpensiveHours(List<ElectricityPrice> pricesToday, List<ElectricityPrice> pricesTomorrow) {
        List<ElectricityPrice> allPrices = concatLists(pricesToday, pricesTomorrow);

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

    /**
     *
     * @param pricesToday    The list of prices for today
     * @param pricesTomorrow The list of prices for tomorrow (can be null)
     * @return The combined list of prices
     */
    public static List<ElectricityPrice> concatLists(List<ElectricityPrice> pricesToday, List<ElectricityPrice> pricesTomorrow) {
        List<ElectricityPrice> allPrices = pricesToday;
        if (pricesTomorrow != null) {
            allPrices = Stream.concat(pricesToday.stream(), pricesTomorrow.stream()).toList();
        }

        return allPrices;
    }

    /**
     * Suggest optimal time to charge an electric car for a given duration (2, 4, or 8 hours) based on the lowest average price.
     * The function gives the optimal time to charge starting from the next full hour.
     */
    public static void suggestOptimalChargingTime(List<ElectricityPrice> pricesToday, List<ElectricityPrice> pricesTomorrow) {
        // Implementation goes here
        List<ElectricityPrice> allPrices = concatLists(pricesToday, pricesTomorrow);
        String input = System.console().readLine("How many hours do you want to charge? (2, 4, or 8) ");
        if (input.matches("[2|4|8]")) {
            int hours = Integer.parseInt(input);
            slidingWindowAlgorithm(allPrices, hours);
        } else {
            System.out.println("Invalid input. Please enter 2, 4, or 8.");
        }

    }

    /**
     * Sliding window algorithm to find the optimal time to charge an electric car for a given duration.
     *
     * @param allPrices List of all ElectricityPrice objects
     * @param hours     Duration in hours to charge (2, 4, or 8)
     */
    public static void slidingWindowAlgorithm(List<ElectricityPrice> allPrices, int hours) {
        //Filter list to only include prices from now
        ZonedDateTime start = ZonedDateTime.now();
        List<ElectricityPrice> filteredList = allPrices.stream()
                .filter(p -> {
                    ZonedDateTime priceTime = ZonedDateTime.parse(p.time_start());
                    return !priceTime.isBefore(start);
                }).toList();

        System.out.println(filteredList);

        BigDecimal optimalSum = BigDecimal.ZERO;
        int optimalStartIndex = 0;

        for (int i = 0; i <= filteredList.size() - hours; i++) {
            BigDecimal sum = BigDecimal.ZERO;

            // summarize the next 'hours' elements
            for (int j = i; j < i + hours; j++) {
                sum = sum.add(BigDecimal.valueOf(filteredList.get(j).SEK_per_kWh()));
            }

            // check if this is the lowest sum found so far
            if (optimalSum.compareTo(BigDecimal.ZERO) == 0 || sum.compareTo(optimalSum) < 0) {
                optimalSum = sum;
                optimalStartIndex = i;
            }

        }
        System.out.println("The sum is " + optimalSum + "and the index is " + optimalStartIndex);
        System.out.println("The optimal sum is " + filteredList.get(optimalStartIndex) + "/// " + filteredList.get(optimalStartIndex + hours));
        // Save the start time for the first ele in the window as the optimal start time and the end time the last ele in the window.
        //Step 2
        // Move the window one element to the right, the average price for those elements
        // Compare the first window with the new window, if the new window has a lower average price, update the optimal start time and optimal end time.
        // Step 3:
        // Compare the average price of the new window with the previous window
        // If the new window has a lower average price, update the optimal start time and optimal

        //
        //TODO: Loop through the list depending on the hours
    }
}
