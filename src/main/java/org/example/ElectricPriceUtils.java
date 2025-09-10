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
    /*public static BigDecimal meanPrice(List<ElectricityPrice> pricesToday, List<ElectricityPrice> pricesTomorrow) {
        BigDecimal meanPrice;
        if (pricesTomorrow != null) {
            ZonedDateTime start = ZonedDateTime.now();
            ZonedDateTime end = start.plusHours(24);

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
    }*/
    public static BigDecimal meanPrice(List<ElectricityPrice> pricesToday, List<ElectricityPrice> pricesTomorrow) {
        // If tomorrow's data is missing, use the entire 'pricesToday' list without filtering
        if (pricesTomorrow == null) {
            return meanPriceCalculator(pricesToday);
        }

        ZonedDateTime start = ZonedDateTime.now();
        ZonedDateTime end = start.plusHours(24);

        // Merge lists (today + tomorrow)
        List<ElectricityPrice> all = Stream.concat(
                pricesToday == null ? Stream.<ElectricityPrice>empty() : pricesToday.stream(),
                pricesTomorrow.stream()
        ).toList();

        // Include only price blocks that overlap the interval [start, end)
        List<ElectricityPrice> filtered = all.stream()
                .filter(p -> {
                    ZonedDateTime s = ZonedDateTime.parse(p.time_start());
                    ZonedDateTime e = ZonedDateTime.parse(p.time_end());
                    return !e.isBefore(start) && s.isBefore(end);
                })
                .toList();

        return meanPriceCalculator(filtered);
    }


    /**
     * Calculate the mean price from a list of ElectricityPrice objects
     *
     * @param prices List of ElectricityPrice objects
     * @return Returns the mean price as a BigDecimal
     */
    public static BigDecimal meanPriceCalculator(List<ElectricityPrice> prices) {
        if (prices == null || prices.isEmpty()) {
            // Nothing to calculate
            return BigDecimal.ZERO.setScale(5, RoundingMode.HALF_UP);
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (ElectricityPrice price : prices) {
            sum = sum.add(BigDecimal.valueOf(price.SEK_per_kWh()));
        }

        return sum.divide(BigDecimal.valueOf(prices.size()), 5, RoundingMode.HALF_UP);
    }

    /**
     * Identify and print the least expensive and most expensive hours from the combined list of today's and tomorrow's prices.
     * If tomorrow's prices are not available, only today's prices will be considered.
     *
     * @param pricesToday    The list of prices for today
     * @param pricesTomorrow The list of prices for tomorrow (can be null)
     */
    public static void printCheapestAndMostExpensiveHours(List<ElectricityPrice> pricesToday, List<ElectricityPrice> pricesTomorrow) {
        // Merge lists safely; treat null as empty
        List<ElectricityPrice> allPrices = Stream.concat(
                pricesToday == null ? Stream.<ElectricityPrice>empty() : pricesToday.stream(),
                pricesTomorrow == null ? Stream.<ElectricityPrice>empty() : pricesTomorrow.stream()
        ).toList();

        if (allPrices.isEmpty()) {
            System.out.println("No price data available.");
            return;
        }

        // Single pass to find cheapest and most expensive (with tie-breaker by earliest start time)
        ElectricityPrice cheapest = null;
        ElectricityPrice mostExpensive = null;

        for (ElectricityPrice p : allPrices) {
            if (cheapest == null
                    || p.SEK_per_kWh() < cheapest.SEK_per_kWh()
                    || (p.SEK_per_kWh() == cheapest.SEK_per_kWh()
                    && ZonedDateTime.parse(p.time_start()).isBefore(ZonedDateTime.parse(cheapest.time_start())))) {
                cheapest = p;
            }
            if (mostExpensive == null
                    || p.SEK_per_kWh() > mostExpensive.SEK_per_kWh()
                    || (p.SEK_per_kWh() == mostExpensive.SEK_per_kWh()
                    && ZonedDateTime.parse(p.time_start()).isBefore(ZonedDateTime.parse(mostExpensive.time_start())))) {
                mostExpensive = p;
            }
        }

        // Format time for output
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
        if (input.matches("[248]")) {
            int hours = Integer.parseInt(input);
            System.out.println(slidingWindowAlgorithm(allPrices, hours));
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
    public static String slidingWindowAlgorithm(List<ElectricityPrice> allPrices, int hours) {
        if (allPrices == null || allPrices.isEmpty()) {
            return "No price data available.";
        }

        //Filter the list to only include prices from now
        ZonedDateTime start = ZonedDateTime.now();
        List<ElectricityPrice> filteredList = allPrices.stream()
                .filter(p -> {
                    ZonedDateTime priceTime = ZonedDateTime.parse(p.time_start());
                    return !priceTime.isBefore(start);
                }).toList();

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
        // Format time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String startTime = ZonedDateTime.parse(filteredList.get(optimalStartIndex).time_start()).format(formatter);
        String endTime = ZonedDateTime.parse(filteredList.get(optimalStartIndex + hours - 1).time_end()).format(formatter);

        return "The optimal time is: " + startTime + " and the total price will be: " + optimalSum + " SEK/kWh" + "\n" +
                "The charging will end at: " + endTime;
    }
}
