package org.example.util;
import org.example.model.PricePoint;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class PriceOps {
    private PriceOps() {}

    /** Merge two lists and sort chronologically by start time. */
    public static List<PricePoint> mergeSorted(List<PricePoint> a, List<PricePoint> b) {
        List<PricePoint> out = new ArrayList<>(a.size() + b.size());
        out.addAll(a);
        out.addAll(b);
        out.sort(Comparator.comparing(PricePoint::start));
        return out;
    }

    /** Keep only hours that haven't finished yet (includes the current hour). */
    public static List<PricePoint> futureOrCurrent(List<PricePoint> in, OffsetDateTime now) {
        return in.stream()
                .filter(p -> p.end().isAfter(now))
                .toList();
    }
}
