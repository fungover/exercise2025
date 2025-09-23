package org.example;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PriceAnalyzer {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm");

    /**
     * Computes and prints basic statistics (mean, max, min) for a list of Price entries.
     *
     * <p>Parses each Price.time_start/time_end as OffsetDateTime, accumulates SEK_per_kWh to compute
     * the mean SEK price, tracks the maximum SEK_per_kWh and its start time, and tracks the minimum
     * EUR_per_kWh and its start time. Results are printed to standard output using the class'
     * predefined formatter.
     *
     * <p>Note: The method has side effects (prints to stdout) and expects each Price to expose
     * time_start and time_end as ISO-8601 strings and numeric fields SEK_per_kWh and EUR_per_kWh.
     * If the provided list is empty, the computed mean will be NaN and the max/min values will remain
     * their initial sentinel values.
     *
     * @param prices list of Price objects to analyze; each entry must contain parsable time_start/time_end
     *               and numeric SEK_per_kWh / EUR_per_kWh fields
     */
    public static void analyze(List<Price> prices) {
        double totalPrice = 0;
        double priceMax = Double.MIN_VALUE;
        double priceMin = Double.MAX_VALUE;
        String priceHourMax = null;
        String priceHourMin = null;
        boolean debug = false;

        int index = 0;
        for (Price price : prices) {
            OffsetDateTime timeStart = OffsetDateTime.parse(price.time_start);
            OffsetDateTime timeEnd = OffsetDateTime.parse(price.time_end);

            if (debug) {
                System.out.println("Object nr: " + index);
                System.out.println("Start" + timeStart.format(formatter));
                System.out.println("End " + timeEnd.format(formatter));
                System.out.println("Price (SEK): " + price.SEK_per_kWh);
                System.out.println("Price (EUR): " + price.EUR_per_kWh);
                System.out.println("---------------");
            }

            totalPrice += price.SEK_per_kWh;

            //Todo Max price
            if (price.SEK_per_kWh > priceMax) {
                priceMax = price.SEK_per_kWh;
                priceHourMax = timeStart.format(formatter);
            }
            //Todo Min price
            if (price.EUR_per_kWh < priceMin) {
                priceMin = price.EUR_per_kWh;
                priceHourMin = timeStart.format(formatter);
            }
            index++;
        }

        double meanPrice = totalPrice / prices.size();

        System.out.println("Mean Price : " + meanPrice + " SEK");
        System.out.println("Max Price : " + priceMax + " at " + priceHourMax);
        System.out.println("Min Price : " + priceMin + " at " + priceHourMin);
    }
        /**
     * Finds and prints the contiguous charging window of the given duration with the lowest total SEK cost.
     *
     * <p>Scans the provided list of Price entries for all contiguous windows of length {@code durationHours},
     * sums each window's {@code SEK_per_kWh}, and prints the window with the smallest total using the class'
     * date formatter. If the list contains fewer entries than {@code durationHours}, a warning message is printed
     * and no window is selected.</p>
     *
     * @param prices        the price entries to analyze; each entry must provide {@code time_start}, {@code time_end}
     *                      and {@code SEK_per_kWh}
     * @param durationHours length of the contiguous window in hours to evaluate
     */
    public static void findBestChargingWindow(List<Price> prices,int durationHours) {
        if (prices.size() < durationHours) {
            System.out.println("Not enough time for " + durationHours + " hours");
        }
        double bestSum = Double.POSITIVE_INFINITY;
        OffsetDateTime bestStart = null;
        OffsetDateTime bestEnd = null;
            //Todo Outer loop finds the best window of time
        for (int i = 0; i <= prices.size() - durationHours; i++) {
            double sum = 0;
            //Todo Inner loop calculate total price
            for (int j = 0; j < durationHours; j++) {
                sum += prices.get(i + j).SEK_per_kWh;
            }
            if (sum < bestSum) {
                bestSum = sum;
                bestStart = OffsetDateTime.parse(prices.get(i).time_start);
                bestEnd = OffsetDateTime.parse(prices.get(i+ durationHours - 1).time_end);
            }
        }

        if (bestStart != null) {
            System.out.printf("Best %d hours charging window: %s --> %s (total %.0f SEK)%n",durationHours,bestStart.format(formatter),bestEnd.format(formatter),bestSum);
        }
    }
}
