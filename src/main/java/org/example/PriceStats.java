package org.example;

import java.util.List;

public class PriceStats {
    public static double mean(List<Price> prices) {
        double sum = 0;
        for (Price p : prices) {
            sum += p.getSekPerKwh();
        }
        return sum / prices.size();
    }

    public static Price cheapest(List<Price> prices) {
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
        Price expensive = prices.get(0);
        for (Price p : prices) {
            if (p.getSekPerKwh() > expensive.getSekPerKwh() ||
                    (p.getSekPerKwh() == expensive.getSekPerKwh() && p.getStart().isBefore(expensive.getStart()))) {
                expensive = p;
            }
        }
        return expensive;
    }

    public static void bestWindow(List<Price> prices, int hours) {
        double bestAvg = Double.MAX_VALUE;
        int bestIndex = 0;

        for (int i = 0; i <= prices.size() - hours; i++) {
            double sum = 0;
            for (int j = 0; j < hours; j++) {
                sum += prices.get(i + j).getSekPerKwh();
            }
            double avg = sum / hours;
            if (avg < bestAvg) {
                bestAvg = avg;
                bestIndex = i;
            }
        }

        Price start = prices.get(bestIndex);
        Price end = prices.get(bestIndex + hours - 1);
        System.out.println("Bästa " + hours + "h: " + start.getStart().toLocalTime() + "–" +
                end.getEnd().toLocalTime() + " (" + bestAvg + " SEK/kWh)");
    }
}
