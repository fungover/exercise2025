package org.example.utils;

import org.example.model.ElectricityPrice;

import java.time.OffsetDateTime;
import java.util.List;

public class PriceDataUtils {

    public static int findCurrentOrNextHourIndex(List<ElectricityPrice> prices) { // Find the index of the current hour or the next hour in the list of prices.
        if (prices.isEmpty()) { // If the list is empty, we return 0.
            return 0;
        }

        OffsetDateTime now = OffsetDateTime.now(); // Get the current date and time with offset from UTC.
        OffsetDateTime currentHour = now.withMinute(0).withSecond(0).withNano(0); // Set the minutes, seconds, and nanoseconds to zero to get the start of the current hour.

        for (int i = 0; i < prices.size(); i++) { // Loop through the list of prices.
            OffsetDateTime priceTime = prices.get(i).timeStart().withMinute(0).withSecond(0).withNano(0); // Get the start time of the price and set the minutes, seconds, and nanoseconds to zero.

            if (!priceTime.isBefore(currentHour)) { // If the price time is equal to or after the current hour, we return the index.
                return i; // Return the index of the current hour or the next hour.
            }
        }
        return 0; // If no such hour is found, return 0 (this case should not happen with valid data).
    }
}
