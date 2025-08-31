package org.example;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class ChargingPrice {
    private final PriceMapper prices = new PriceMapper();
    private final Price[] allPrices = prices.getAllPrices();
    private final int hours = LocalDateTime.now().getHour();
    private final int minutes = LocalDateTime.now().getMinute();

    private Price[] currentPrices() {
        int newIndex = 0;
        String now = setHour();
        for (Price price : allPrices) {
            if (now.equals(price.getHourStart())) {
                break;
            }
            newIndex++;
        }
        return Arrays.copyOfRange(allPrices, newIndex, allPrices.length);
    }

    private String setHour() {
        int dateTime;
        if (minutes >= 30) {
            dateTime = hours + 1;
        } else {
            dateTime = hours;
        }
        return dateTime + ":00";
    }

    private BigDecimal checkChargingPrices(Price[] prices, int n, int k) {
        // n = prices.length and must be greater than k
        if (n <= k) {
            System.out.println("Invalid");
            return BigDecimal.valueOf(-1);
        }

        // First window
        BigDecimal minSum = BigDecimal.ZERO;
        for (int i = 0; i < k; i++) {
            minSum = minSum.add(prices[i].getSekPerKWh());
        }

        // Check remaining windows in an interval of variable k
        BigDecimal windowSum = minSum;
        for (int i = k; i < n; i++) {
            BigDecimal indexPrice = prices[i].getSekPerKWh();
            BigDecimal windowPrice = prices[i - k].getSekPerKWh();

            windowSum = windowSum.add(indexPrice.subtract(windowPrice));
            minSum = minSum.min(windowSum);
        }
        return minSum;
    }

    public void printChargingPrice() {
        Price[] currentPrices = currentPrices();
        int n = currentPrices.length;
        System.out.println("2h: " + checkChargingPrices(currentPrices, n, 2));
        System.out.println("4h: " + checkChargingPrices(currentPrices, n, 4));
        System.out.println("8h: " + checkChargingPrices(currentPrices, n, 8));
    }
}
