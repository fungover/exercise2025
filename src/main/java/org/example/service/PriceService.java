package org.example.service;

import org.example.model.PricePoint;
import org.example.model.PriceZone;

import java.util.List;

public interface PriceService {

    List<PricePoint> getTodayPrices(PriceZone zone);

    List<PricePoint> getAvailablePrices(PriceZone zone);
}
