package org.example.service;

import org.example.model.PricePoint;

import java.util.Comparator;
import java.util.List;

public class StatsCalculator {

    public static double mean(List<PricePoint> prices) {
        return prices.stream()
                .mapToDouble(PricePoint::price)
                .average()
                .orElse(0.0);
    }
    public static PricePoint min(List<PricePoint> prices) {
        return prices.stream()
                .min(Comparator.comparingDouble(PricePoint::price))
                .orElseThrow();
    }

    public static PricePoint max(List<PricePoint> prices) {
        return prices.stream()
                .max(Comparator.comparingDouble(PricePoint::price))
                .orElseThrow();
    }
}
