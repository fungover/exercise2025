package org.example;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        String zone = zoneSelection();

        DateTimeFormatter dayFmt = DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter monthFmt = DateTimeFormatter.ofPattern("MM");

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        API api = new API();

        try {
            List<ElectricityPrice> pricesToday = api.fetchPrices(zone, dayFmt.format(today), monthFmt.format(today), String.valueOf(today.getYear()));
            List<ElectricityPrice> pricesTomorrow = api.fetchPrices(zone, dayFmt.format(tomorrow), monthFmt.format(tomorrow), String.valueOf(tomorrow.getYear()));

            if (pricesToday == null || pricesToday.isEmpty()) {
                System.out.println("No price data available for today.");
                return;
            }

            double meanPrice = CalculateMeanPrice(pricesToday);

            System.out.println("Mean price for today: " + String.format("%.4f", meanPrice) + " (SEK/kWh)");

            if (pricesTomorrow != null && !pricesTomorrow.isEmpty()) {
                System.out.println("Mean price for tomorrow: " + String.format("%.4f", CalculateMeanPrice(pricesTomorrow)) + " (SEK/kWh)");
            } else {
                System.out.println("The mean price for tomorrow could not be calculated at this time.");
            }

            ElectricityPrice cheapest = getPrice(pricesToday, false);
            ElectricityPrice mostExpensive = getPrice(pricesToday, true);

            System.out.println("Cheapest price is " + OffsetDateTime.parse(cheapest.time_start).format(DateTimeFormatter.ofPattern("HH:mm")) + " for " + String.format("%.4f", cheapest.SEK_per_kWh) + "(SEK/kWh)");
            System.out.println("The most expensive price is " + OffsetDateTime.parse(mostExpensive.time_start).format(DateTimeFormatter.ofPattern("HH:mm")) + " for " + String.format("%.4f", mostExpensive.SEK_per_kWh) + "(SEK/kWh)");

            double[] priceArray = makePricesArray(pricesToday);

            int[] hourArr = {2, 4, 8};

            System.out.println("If you would like to charge your vehicle, I recommend starting at these times depending on how long you plan to charge:");

            for (int i : hourArr) {
                GetResultFromHours(priceArray, i, pricesToday);
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Error fetching prices: " + e.getMessage());
        }
    }

    public static String zoneSelection() {
        System.out.println("Welcome! Please select what zone you would like to see the electrical prices in.");
        System.out.println("(1) - Luleå / Norra Sverige");
        System.out.println("(2) - Sundsvall / Norra Mellansverige");
        System.out.println("(3) - Stockholm / Södra Mellansverige");
        System.out.println("(4) - Malmö / Södra Sverige");
        System.out.println("Please select what zone you would like to see with the corresponding number");

        Scanner scanner = new Scanner(System.in);
        String selection = scanner.nextLine().trim();
        return switch (selection) {
            case "1" -> "SE1";
            case "2" -> "SE2";
            case "3" -> "SE3";
            case "4" -> "SE4";
            default -> {
                System.out.println("Invalid selection. Defaulting to SE3.");
                yield "SE3";
            }
        };
    }

    public static double CalculateMeanPrice(List<ElectricityPrice> prices) {
        if (prices == null || prices.isEmpty()) return 0.0;

        double meanP = 0.0;

        for (ElectricityPrice price : prices) {
            meanP += price.SEK_per_kWh;
        }

        meanP /= prices.size();

        return meanP;
    }

    public static ElectricityPrice getPrice(List<ElectricityPrice> prices, boolean expensive) {
        if (prices == null || prices.isEmpty()) {
            throw new IllegalArgumentException("Prices list must not be null or empty.");
        }

        ElectricityPrice best = prices.getFirst();

        for (int i = 1; i < prices.size(); i++) {
            ElectricityPrice p = prices.get(i);

            boolean moreExpensive = p.SEK_per_kWh > best.SEK_per_kWh;

            boolean cheaper = p.SEK_per_kWh < best.SEK_per_kWh;

            boolean samePriceEarlier =
                    p.SEK_per_kWh == best.SEK_per_kWh &&
                            p.time_start.compareTo(best.time_start) < 0;

            if (expensive) {
                if (moreExpensive || samePriceEarlier) {
                    best = p;
                }
            } else {
                if (cheaper || samePriceEarlier) {
                    best = p;
                }
            }
        }

        return best;
    }

    public static double[] makePricesArray(List<ElectricityPrice> prices) {
        if (prices == null || prices.isEmpty()) {
            throw new IllegalArgumentException("Prices list must not be null or empty");
        }

        double[] result = new double[prices.size()];
        for (int i = 0; i < prices.size(); i++) {
            result[i] = prices.get(i).SEK_per_kWh;
        }

        return result;
    }

    public static BestHoursRecord getCheapestHours(double[] prices, int hours) {
        int n = prices.length;
        if (hours <= 0 || hours > n) {
            throw new IllegalArgumentException("hours must be between 1 and " + n);
        }

        double sum = 0;
        for (int i = 0; i < hours; i++) {
            sum += prices[i];
        }

        double bestSum = sum;
        int bestStart = 0;

        for (int start = 1; start <= n - hours; start++) {
            sum = sum - prices[start - 1] + prices[start + hours - 1];
            if (sum < bestSum) {
                bestSum = sum;
                bestStart = start;
            }
        }

        return new BestHoursRecord(bestStart, bestSum);
    }

    public static void GetResultFromHours(double[] priceArray, int hours, List<ElectricityPrice> prices) {
        if (priceArray == null || prices == null) {
            throw new IllegalArgumentException("Arrays must not be null");
        }
        BestHoursRecord returningResult = getCheapestHours(priceArray, hours);

        int bestStart = returningResult.startIndex();
        if (bestStart >= prices.size()) {
            throw new  IllegalArgumentException("Best start index exceeds prices list size");
        }
        double sum = returningResult.totalSum();

        String startTime = OffsetDateTime
                .parse(prices.get(bestStart).time_start)
                .format(DateTimeFormatter.ofPattern("HH:mm"));

        System.out.println("(" + hours + " hours) start charging at " + startTime + ". It will cost a total of " + String.format("%.2f", sum) + "kr");
    }
}
