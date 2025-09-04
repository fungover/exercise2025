package org.example;

public class Calculate {

    public static double calculateMean(PricePerHour[] prices) {

        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Inga prisdata tillgängliga för medelvärde.");
        }

        double sum = 0;
        double mean;

        for (PricePerHour p : prices) {
            sum += p.SEK_per_kWh();
        }
        mean = sum / prices.length;
        return mean;
    }

    public static PricePerHour findMostExpensiveHour(PricePerHour[] prices) {

        PricePerHour best = prices[0];
        for (PricePerHour p : prices)
            if (p.SEK_per_kWh() > best.SEK_per_kWh() ||
                    p.SEK_per_kWh() == best.SEK_per_kWh() &&
                            best.time_start().compareTo(p.time_start()) > 0)
                best = p;
        return best;
    }

    public static PricePerHour findCheapestHour(PricePerHour[] prices) {
        PricePerHour best = prices[0];
        for (PricePerHour p : prices)
            if (p.SEK_per_kWh() < best.SEK_per_kWh() || (p.SEK_per_kWh() == best.SEK_per_kWh()
                    && best.time_start().compareTo(p.time_start()) > 0))
                best = p;
        return best;
    }

    public static PricePerHour[] findBestChargingPeriod(PricePerHour[] prices, int hours) {
        if (prices.length < hours) {
            throw new IllegalArgumentException("Inte tillräckligt många timmar i datan.");
        }

        double windowSum = 0;
        int startIndex = 0;

        // Calculate the sum of the first window
        for (int i = 0; i < hours; i++) {
            windowSum += prices[i].SEK_per_kWh();
        }

        double minSum = windowSum;

        // Move the window one step at a time
        for (int i = hours; i < prices.length; i++) {
            windowSum = windowSum - prices[i - hours].SEK_per_kWh() + prices[i].SEK_per_kWh();

            if (windowSum < minSum) {
                minSum = windowSum;
                startIndex = i - hours + 1;
            }
        }

        // Return the cheapest time slot (as an array of PricePerHour)
        PricePerHour[] bestPeriod = new PricePerHour[hours];
        System.arraycopy(prices, startIndex, bestPeriod, 0, hours);

        return bestPeriod;
    }
}
