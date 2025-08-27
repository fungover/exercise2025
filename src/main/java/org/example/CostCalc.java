package org.example;
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
