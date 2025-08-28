package org.example.util;

import org.example.model.ChargeWindow;
import org.example.model.HourExtremes;
import org.example.model.PricePoint;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class PriceOps {
    private PriceOps() {
    }

    /**
     * Merge two lists and sort chronologically by start time.
     */
    public static List<PricePoint> mergeSorted(List<PricePoint> a, List<PricePoint> b) {
        List<PricePoint> out = new ArrayList<>(a.size() + b.size());
        out.addAll(a);
        out.addAll(b);
        out.sort(Comparator.comparing(PricePoint::start));
        return out;
    }

    /**
     * Keep only hours that haven't finished yet (includes the current hour).
     */
    public static List<PricePoint> futureOrCurrent(List<PricePoint> in, OffsetDateTime now) {
        return in.stream()
                .filter(p -> p.end().isAfter(now))
                .toList();
    }

    public static PricePoint minByPriceEarliest(List<PricePoint> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("minByPriceEarliest: list must not be null or empty");
        }
        return list.stream()
                .min(Comparator.comparing(PricePoint::sekPerKWh)
                        .thenComparing(PricePoint::start))
                .get();
    }

    public static PricePoint maxByPriceEarliest(List<PricePoint> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("maxByPriceEarliest: list must not be null or empty");
        }
        Comparator<PricePoint> cmp =
                Comparator.comparing(PricePoint::sekPerKWh)
                        .thenComparing(PricePoint::start, Comparator.reverseOrder());
        return list.stream().max(cmp).get();
    }

    public static HourExtremes extremesByPriceEarliest(List<PricePoint> list) {
        return new HourExtremes(
                minByPriceEarliest(list),
                maxByPriceEarliest(list)
        );
    }

    /**
     * Best contiguous k-hour window by total price; earliest on ties.
     */
    public static ChargeWindow bestWindow(List<PricePoint> points, int k) {
        int n = points.size();
        if (n < k) return null;

        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < k; i++) {
            sum = sum.add(points.get(i).sekPerKWh());
        }
        BigDecimal bestSum = sum;
        int bestStart = 0;

        for (int i = k; i < n; i++) {
            sum = sum
                    .subtract(points.get(i - k).sekPerKWh())
                    .add(points.get(i).sekPerKWh());

            if (sum.compareTo(bestSum) < 0) {
                bestSum = sum;
                bestStart = i - k + 1;
            }
        }

        PricePoint startPt = points.get(bestStart);
        PricePoint endPt = points.get(bestStart + k - 1);
        BigDecimal avg = bestSum.divide(BigDecimal.valueOf(k), 5, RoundingMode.HALF_UP);

        return new ChargeWindow(startPt.start(), endPt.end(), k, avg, bestSum);
    }
}
