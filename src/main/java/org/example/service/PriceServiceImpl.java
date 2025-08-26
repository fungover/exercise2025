package org.example.service;

import org.example.client.ElprisClient;
import org.example.model.PricePoint;
import org.example.model.PriceZone;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriceServiceImpl implements PriceService {

    private final ElprisClient client;
    private static final ZoneId MARKET_ZONE = ZoneId.of("Europe/Stockholm");

    public PriceServiceImpl(ElprisClient client) {
        this.client = client;
    }

    @Override
    public List<PricePoint> getTodayPrices(PriceZone zone) {
        return client.fetchDayPrices(zone, LocalDate.now(MARKET_ZONE));
    }

    public List<PricePoint> getTomorrowPrices(PriceZone zone) {
        return client.fetchDayPrices(zone, LocalDate.now().plusDays(1));
    }

    public List<PricePoint> getAvailablePrices(PriceZone zone) {
        List<PricePoint> today = getTodayPrices(zone);
        List<PricePoint> tomorrow = getTomorrowPrices(zone);

        if (today == null || today.isEmpty()) return (tomorrow == null ? List.of() : tomorrow);
        if (tomorrow == null || tomorrow.isEmpty()) return today;

        List<PricePoint> merged = new ArrayList<>(today.size() + tomorrow.size());
        merged.addAll(today);
        merged.addAll(tomorrow);
        merged.sort(Comparator.comparing(PricePoint::start));
        return merged;
    }

}