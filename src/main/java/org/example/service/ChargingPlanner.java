package org.example.service;

import org.example.model.PricePoint;

import java.time.LocalDateTime;
import java.util.List;

public class ChargingPlanner {
    public static class ChargingWindow {
        private final LocalDateTime start;
        private final LocalDateTime end;
        private final double totalPrice;

        public ChargingWindow(LocalDateTime start, LocalDateTime end, double totalPrice) {
            this.start = start;
            this.end = end;
            this.totalPrice = totalPrice;
        }

        public LocalDateTime getStart() { return start; }
        public LocalDateTime getEnd() { return end; }
        public double getTotalPrice() { return totalPrice; }
    }

    public static ChargingWindow findBestWindow(List<PricePoint> prices, int hours) {

        LocalDateTime now = LocalDateTime.now();
        List<PricePoint> futurePrices = prices.stream()
                .filter(p -> !p.start().isBefore(now))
                .sorted(java.util.Comparator.comparing(PricePoint::start))
                .toList();


        if (hours <= 0) {
            throw new IllegalArgumentException("hours must be > 0");
            }
        if (futurePrices.size() < hours) return null;

        int maxWindow = Math.min(hours, futurePrices.size());
        double minSum = Double.MAX_VALUE;
        int minIndex = 0;

        for (int i = 0; i < futurePrices.size() - maxWindow; i++) {
            double sum = 0;
            for (int j = 0; j < maxWindow; j++) {
                sum += futurePrices.get(i + j).price();
            }

            if (sum < minSum) {
                minSum = sum;
                minIndex = i;
            }
        }

        LocalDateTime start = futurePrices.get(minIndex).start();
        LocalDateTime end = futurePrices.get(minIndex + maxWindow - 1).start().plusHours(1);
        return new ChargingWindow(start, end, minSum);
    }
}
