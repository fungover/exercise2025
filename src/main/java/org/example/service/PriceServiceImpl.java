package org.example.service;

import org.example.client.ElprisClient;
import org.example.model.PricePoint;
import org.example.model.PriceZone;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PriceServiceImpl implements PriceService {

    private final ElprisClient client;

    public PriceServiceImpl(ElprisClient client) {
        this.client = client;
    }

    @Override
    public List<PricePoint> getTodayPrices(PriceZone zone) {
        return client.fetchDayPrices(zone, LocalDate.now());
    }

    public List<PricePoint> getTomorrowPrices(PriceZone zone) {
        return client.fetchDayPrices(zone, LocalDate.now().plusDays(1));
    }

    public List<PricePoint> getAvailablePrices(PriceZone zone) {
        List<PricePoint> today = getTodayPrices(zone);
        List<PricePoint> tomorrow = getTomorrowPrices(zone);

        if (tomorrow == null || tomorrow.isEmpty()) {
            return today;
        }

        List<PricePoint> merged = new ArrayList<>(today);
        merged.addAll(tomorrow);
        return merged;
    }

}