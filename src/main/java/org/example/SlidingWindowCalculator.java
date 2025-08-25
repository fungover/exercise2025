package org.example;

public class SlidingWindowCalculator {

    public static void findCheapestWindow(PriceEntry[] prices, int hours) {
        if (prices == null || prices.length < hours) {
            System.out.println("Inte tillräckligt många timmar i datan.");
            return;
        }

        // Counting som of the first window
        double currentSum = 0;
        for (int i = 0; i < hours; i++) {
            currentSum += prices[i].SEK_per_kWh;
        }

        double bestSum = currentSum;
        int bestStart = 0;

        // Push the window
        for (int start = 1; start <= prices.length - hours; start++) {
            currentSum = currentSum - prices[start - 1].SEK_per_kWh
                    + prices[start + hours - 1].SEK_per_kWh;

            if (currentSum < bestSum) {
                bestSum = currentSum;
                bestStart = start;
            }
        }

        // Print result
        int bestEnd = bestStart + hours - 1;
        System.out.printf("%dh billigaste: %s → %s | Medelpris: %.3f SEK/kWh%n",
                hours,
                prices[bestStart].time_start.substring(11, 16),
                prices[bestEnd].time_end.substring(11, 16),
                bestSum / hours);
    }
}

