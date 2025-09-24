/* Class to work with my Printer. This class will handle the maths and statistic logics */
package org.example.electricity;

import java.util.Comparator;
import java.util.List;

public class Stats {

    public record BestWindow(int startHour, int length, double sum, double average) { }

    private static final Comparator<PriceData> BY_PRICE_THEN_HOUR =
            Comparator.comparingDouble(PriceData::sekPerkWh)
                    .thenComparingInt(PriceData::hour);

    /* Average Price */
    public static double avg(List<PriceData> prices) {
        if (prices == null || prices.isEmpty()) return 0.0;
        return prices.stream().mapToDouble(PriceData::sekPerkWh).average().orElse(0.0);
    }

    /* Lowest kWh Price */
    public static PriceData min(List<PriceData> prices) {
        if (prices == null || prices.isEmpty()) return null;
        return prices.stream().min(BY_PRICE_THEN_HOUR).orElse(null);
    }

    /* Highest kWh Price */
    public static PriceData max(List<PriceData> prices) {
        if (prices == null || prices.isEmpty()) return null;
        return prices.stream().max(Comparator.comparingDouble(PriceData::sekPerkWh).reversed()
                        .thenComparingInt(PriceData::hour))
                .orElse(null);
    }

    public static BestWindow bestWindow(List<PriceData> prices, int k) {
        if (prices == null || prices.size() < k) {
            throw new IllegalArgumentException("Too few prices to display" + k);
        }

        double sum = 0;
        for (int i = 0; i < k; i++) sum += prices.get(i).sekPerkWh();
        double bestSum = sum;
        int bestStart = 0;

        for (int start = 1; start + k <= prices.size(); start++) {
            sum += prices.get(start + k - 1).sekPerkWh();
            sum -= prices.get(start - 1).sekPerkWh();

            if (sum < bestSum) {
                bestSum = sum;
                bestStart = start;
            }
        }
        return new BestWindow(bestStart, k, bestSum, bestSum / k);
    }
}



