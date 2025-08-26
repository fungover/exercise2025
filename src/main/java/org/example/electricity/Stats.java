/* Class to work with my Printer. This class will handle the maths and statistic logics */
package org.example.electricity;

import java.util.Comparator;
import java.util.List;

public class Stats {

    /* Average Price */
    public static double avg(List<PriceData> prices) {
        if (prices == null || prices.isEmpty()) return 0.0;
        return prices.stream().mapToDouble(PriceData::sekPerkWh).average().orElse(0.0);
    }

    /* Lowest kWh Price */
    public static PriceData min(List<PriceData> prices) {
        if (prices == null || prices.isEmpty()) return null;
        return prices.stream().min(Comparator.comparingDouble(PriceData::sekPerkWh)).orElse(null);
    }

    /* Highest kWh Price */
    public static PriceData max(List<PriceData> prices) {
        if (prices == null || prices.isEmpty()) return null;
        return prices.stream().max(Comparator.comparingDouble(PriceData::sekPerkWh)).orElse(null);
    }

}
