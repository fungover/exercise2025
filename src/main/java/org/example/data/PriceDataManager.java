package org.example.data;

import com.fasterxml.jackson.jr.ob.JSON;
import org.example.model.HourlyPrice;
import org.example.util.PriceUtil;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PriceManagerData {
    private List<HourlyPrice> prices = new ArrayList<>();
    private String currentZone = "SE4";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM-dd");

    public void setZone(String zone) {
        if (zone != null && !zone.trim().isEmpty()) {
            this.currentZone = zone;
        }
    }

    public String getZone() {
        return currentZone;
    }

    public List<HourlyPrice> getPrices() {
        return new ArrayList<>(prices);
    }

    public void downloadPrices(boolean includeNextDay) {
        prices.clear();
        LocalDateTime now = LocalDateTime.now();
        String todayPath = now.format(formatter);

        try {
            URL urlToday = new URL("https://www.elprisetjustnu.se/api/v1/prices/" + todayPath + "_" + currentZone + ".json");
            List<Object> todayResults = JSON.std.listFrom(urlToday);
            prices.addAll(PriceUtil.convertToHourlyPrices(todayResults, currentZone));

            // Condition to ensure the next days data is available before processing it
            if (includeNextDay && now.getHour() >= 13) {
                LocalDateTime tomorrow = now.plusDays(1);
                String tomorrowPath = tomorrow.format(formatter);
                URL urlTomorrow = new URL("https://www.elprisetjustnu.se/api/v1/prices/" + tomorrowPath + "_" + currentZone + ".json");
                List<Object> tomorrowResults = JSON.std.listFrom(urlTomorrow);
                prices.addAll(PriceUtil.convertToHourlyPrices(tomorrowResults, currentZone));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
