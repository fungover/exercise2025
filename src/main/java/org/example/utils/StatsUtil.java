package org.example.utils;

import org.example.model.PriceHour;

import java.util.List;

public class StatsUtil {

    public static double calcAverageSek(List<PriceHour> hours) {
        if (hours == null || hours.isEmpty()) return 0.0 ;
        double sum = 0.0;
        for (PriceHour h : hours) sum += h.SEK_per_kWh();
        return sum / hours.size();
    }

    public static PriceHour findCheapestSek(List<PriceHour> hours) {
        if (hours == null || hours.isEmpty()) return null;
        PriceHour best = hours.get(0);
        for (PriceHour h : hours) {
            double p = h.SEK_per_kWh(), bp = best.SEK_per_kWh();
            if (p < bp) best = h;
            else if (p == bp) {
                var t = java.time.OffsetDateTime.parse(h.time_start());
                var bt = java.time.OffsetDateTime.parse(best.time_start());
                if (t.isBefore(bt)) best = h;
            }
        }
        return best;
    }

    public static PriceHour findMostExpensiveSek(List<PriceHour> hours) {
        if (hours == null || hours.isEmpty()) return null;
        PriceHour worst = hours.get(0);
        for (PriceHour h : hours) {
            double p = h.SEK_per_kWh(), wp = worst.SEK_per_kWh();
            if (p > wp) worst = h;
            else if (p == wp) {
                var t = java.time.OffsetDateTime.parse(h.time_start());
                var bt = java.time.OffsetDateTime.parse(worst.time_start());
                if (t.isBefore(bt)) worst = h;
            }
        }
        return worst;
    }

    public static double calcMeanPricesEUR(List<PriceHour> hours) {
        if (hours == null || hours.isEmpty()) return 0.0 ;
        double sum = 0.0;
        for (PriceHour h : hours) sum += h.EUR_per_kWh();
        return sum / hours.size();
    }

}
