package org.example.utils;

import org.example.model.ElectricityPrice;

import java.time.OffsetDateTime;
import java.util.List;

public class PriceDataUtils {

    public static int findCurrentOrNextHourIndex(List<ElectricityPrice> prices) {
        if (prices.isEmpty()) {
            return 0;
        }

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime currentHour = now.withMinute(0).withSecond(0).withNano(0);

        for (int i = 0; i < prices.size(); i++) {
            OffsetDateTime priceTime = prices.get(i).timeStart().withMinute(0).withSecond(0).withNano(0);

            if (!priceTime.isBefore(currentHour)) {
                return i;
            }
        }
        return 0;
    }
}
