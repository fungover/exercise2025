package org.example.service;

import org.example.model.PricePoint;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

public class ChargingPlanner {

    public static class ChargingWindow {
        private final OffsetDateTime start;
        private final OffsetDateTime end;
        private final BigDecimal totalPrice;

        public ChargingWindow(OffsetDateTime start, OffsetDateTime end, BigDecimal totalPrice) {
            this.start = start;
            this.end = end;
            this.totalPrice = totalPrice;
        }

        public OffsetDateTime getStart() { return start; }
        public OffsetDateTime getEnd() { return end; }
        public BigDecimal getTotalPrice() { return totalPrice; }
    }

    public static ChargingWindow findBestWindow(List<PricePoint> prices, int hours) {
        if (hours <= 0) throw new IllegalArgumentException("hours must be > 0");

        Instant now = Instant.now();
        List<PricePoint> futurePrices = prices.stream()
                .filter(p -> !p.start().toInstant().isBefore(now))
                .sorted(java.util.Comparator.comparing(PricePoint::start))
                .toList();

        if (futurePrices.size() < hours) return null;

        int maxWindow = Math.min(hours, futurePrices.size());
        int minIndex = 0;
        BigDecimal minSum = futurePrices.subList(0, maxWindow).stream()
                .map(PricePoint::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        for (int i = 1; i <= futurePrices.size() - maxWindow; i++) {
            BigDecimal sum = futurePrices.subList(i, i + maxWindow).stream()
                    .map(PricePoint::price)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            if (sum.compareTo(minSum) < 0) {
                minSum = sum;
                minIndex = i;
            }
        }

        OffsetDateTime start = futurePrices.get(minIndex).start();
        OffsetDateTime end = futurePrices.get(minIndex + maxWindow - 1).start().plusHours(1);
        return new ChargingWindow(start, end, minSum);
    }
}
