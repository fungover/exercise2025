package org.example;

import java.util.List;

public class PriceStats {
    public static double mean(List<Price> prices) {
        if (prices == null || prices.isEmpty()) {
            throw new IllegalArgumentException("prices must not be null/empty");
        }
        double sum = 0d;
        for (Price p : prices) {
            sum += p.getSekPerKwh();
        }
        return sum / prices.size();
    }

    public static Price cheapest(List<Price> prices) {
        if (prices == null || prices.isEmpty()) {
            throw new IllegalArgumentException("prices must not be null/empty");
        }

        Price cheapest = prices.get(0);
        for (Price p : prices) {
            if (p.getSekPerKwh() < cheapest.getSekPerKwh() ||
                    (p.getSekPerKwh() == cheapest.getSekPerKwh() && p.getStart().isBefore(cheapest.getStart()))) {
                cheapest = p;
            }
        }
        return cheapest;
    }


    public static Price mostExpensive(List<Price> prices) {
        if (prices == null || prices.isEmpty()) {
            throw new IllegalArgumentException("prices must not be null/empty");
        }
        return prices.stream()
                .max((a, b) -> {
                    int cmp = Double.compare(a.getSekPerKwh(), b.getSekPerKwh());
                    if (cmp != 0) return cmp;
                    return b.getStart().compareTo(a.getStart());
                })
                .orElseThrow();
    }

    public static void bestWindow(List<Price> prices, int hours) {
        if (prices == null || prices.isEmpty()) {
            throw new IllegalArgumentException("prices must not be null/empty");
        }
        if (hours <= 0 || hours > prices.size()) {
            throw new IllegalArgumentException("invalid hours: " + hours);
        }

        double sum = 0d;
        for (int i = 0; i < hours; i++) {
            sum += prices.get(i).getSekPerKwh();
        }
        double bestAvg = sum / hours;
        int bestIndex = 0;

        for (int i = hours; i < prices.size(); i++) {
            sum += prices.get(i).getSekPerKwh();
            sum -= prices.get(i - hours).getSekPerKwh();
            double avg = sum / hours;
            if (avg < bestAvg) {
                bestAvg = avg;
                bestIndex = i - hours + 1;
            }
        }

        Price start = prices.get(bestIndex);
        Price end = prices.get(bestIndex + hours - 1);
        System.out.println("Bästa " + hours + "h: " +
                start.getStart().toLocalTime() + "–" +
                end.getEnd().toLocalTime() + " (" +
                String.format("%.4f", bestAvg) + " SEK/kWh)");
    }
}
