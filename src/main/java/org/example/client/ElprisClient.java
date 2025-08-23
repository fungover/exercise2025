package org.example.client;

import org.example.model.PricePoint;
import org.example.model.PriceZone;

import java.time.LocalDateTime;
import java.util.List;

// HTTP calls -> DayPrices. Will talk to the API.
public class ElprisClient {
    public List<PricePoint> fetchTodayPrices(PriceZone zone) {
        return List.of (
                new PricePoint(LocalDateTime.now(), 1.23),
                new PricePoint(LocalDateTime.now().plusHours(1), 2.34)
        );
    }
}
