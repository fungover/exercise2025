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
        if (prices.size() <  hours) return null;

        double minSum = Double.MAX_VALUE;
        int minIndex = 0;

        for (int i = 0; i < prices.size() - hours; i++) {
            double sum = 0;
            for (int j = 0; j < hours; j++) {
                sum += prices.get(i + j).price();
            }

            if (sum < minSum) {
                minSum = sum;
                minIndex = i;
            }
        }

        LocalDateTime start = prices.get(minIndex).start();
        LocalDateTime end = prices.get(minIndex + hours - 1).start().plusHours(1);
        return new ChargingWindow(start, end, minSum);
    }
}
