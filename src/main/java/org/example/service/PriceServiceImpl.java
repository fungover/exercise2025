package org.example.service;

import org.example.client.ElprisClient;
import org.example.model.PricePoint;
import org.example.model.PriceZone;

import java.util.List;

public class PriceServiceImpl implements PriceService {

    private final ElprisClient client;

    public PriceServiceImpl(ElprisClient client) {
        this.client = client;
    }

    @Override
    public List<PricePoint> getTodayPrices(PriceZone zone) {
        return client.fetchTodayPrices(zone);
    }
}