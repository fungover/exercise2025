package org.example;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class ChargingPrice {
    private final PriceMapper priceMapper = new PriceMapper();
    private final Price[] allPrices = priceMapper.getAllPrices();
    private final Price[] prices = currentPrices();
    private final int hours = LocalDateTime.now().getHour();
    private final int minutes = LocalDateTime.now().getMinute();

    private Price[] currentPrices() {
        int newIndex = 0;
        String now = setHour();
        for (Price price : allPrices) {
            if (now.equals(price.getStartHour())) {
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
        String formattedTime = String.format("%02d", dateTime);
        return formattedTime + ":00";
    }

    private ChargingPriceDates chargingPrices(Price[] prices, int n, int k) {
        // n = prices.length and must be greater than k = hours
        if (n <= k) {
            return new ChargingPriceDates(BigDecimal.valueOf(-1), -1, -1);
        }

        // First window
        int startIndex = 0;
        int endIndex = k - 1;
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

            if (windowSum.compareTo(minSum) < 0) {
                minSum = windowSum;
                startIndex = i - k + 1;
                endIndex = i;
            }
        }
        return new ChargingPriceDates(minSum, startIndex, endIndex);
    }

    private void printChargingPrice(int k) {
        int n = prices.length;

        ChargingPriceDates chargingPriceDates = chargingPrices(prices, n, k);
        Price startPrice = prices[chargingPriceDates.startIndex];
        Price endPrice = prices[chargingPriceDates.endIndex];

        System.out.println("For " + k + " hours: " +
                chargingPriceDates.minSum + " SEK");
        System.out.println("Starts: " + startPrice.getStartDate() + " / " +
                startPrice.getStartHour());
        System.out.println("Ends: " + endPrice.getEndDate() + " / " +
                endPrice.getEndHour());
    }

    public void printCharge2() {
        printChargingPrice(2);
    }

    public void printCharge4() {
        printChargingPrice(4);
    }

    public void printCharge8() {
        printChargingPrice(8);
    }

    record ChargingPriceDates(BigDecimal minSum, int startIndex, int endIndex) {}
}
