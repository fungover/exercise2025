/**
 * Calculates the total electricity cost from consumption and price data.
 * For each consumption entry, find the matching hourly price by date and hour,
 * multiplies SEK price by kWh usage and sums the result.
 */

package org.example.service;
import org.example.model.Consumption;
import org.example.model.Elpris;

import java.util.List;

public class CostCalc {
    public static double calculateTotalCost(List<Consumption> usage, List<Elpris> prices) {
        double total = 0;
        for (Consumption consumption : usage) {
            for (Elpris elpris : prices) {
                if (elpris.getTimeStart().getHour() == consumption.timestamp().getHour() &&
                elpris.getTimeStart().toLocalDate().equals(consumption.timestamp().toLocalDate())) {
                    total += elpris.getSEK() * consumption.kWh();
                    break;

            }
        }
    }
        return total;
    }
}
