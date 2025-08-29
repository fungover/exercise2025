package org.example.menu.actions;

import org.example.data.PriceDataManager;
import org.example.menu.MenuAction;
import org.example.model.HourlyPrice;
import org.example.util.PriceUtil;

import java.util.Comparator;
import java.util.List;

public class ExtremeHoursAction implements MenuAction {
    private final PriceDataManager dataManager;

    public ExtremeHoursAction(PriceDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        List<HourlyPrice> prices = dataManager.getPrices();
        if (prices.isEmpty()) {
            System.out.println("No prices available.");
            return;
        }

        HourlyPrice cheapestSek = prices.stream().min(Comparator.comparingDouble(HourlyPrice::getSekPerKwh).thenComparingInt(HourlyPrice::getHour)).orElse(null);
        HourlyPrice mostExpensiveSek = prices.stream().max(Comparator.comparingDouble(HourlyPrice::getSekPerKwh).thenComparingInt(HourlyPrice::getHour)).orElse(null);

        HourlyPrice cheapestEur = prices.stream().min(Comparator.comparingDouble(HourlyPrice::getEurPerKwh).thenComparingInt(HourlyPrice::getHour)).orElse(null);
        HourlyPrice mostExpensiveEur = prices.stream().max(Comparator.comparingDouble(HourlyPrice::getEurPerKwh).thenComparingInt(HourlyPrice::getHour)).orElse(null);

        System.out.println("Cheapest price for zone " + dataManager.getZone() + ":");
        System.out.println(cheapestSek);
        System.out.println(cheapestEur);
        System.out.println("Most expensive price for zone " + dataManager.getZone() + ":");
        System.out.println(mostExpensiveSek);
        System.out.println(mostExpensiveEur);
    }

    @Override
    public String getDescription() {
        return "Display the cheapest and most expensive hourly prices for the current day";
    }
}
