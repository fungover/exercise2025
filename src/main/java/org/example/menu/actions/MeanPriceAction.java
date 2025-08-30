package org.example.menu.actions;

import org.example.data.PriceDataManager;
import org.example.menu.MenuAction;
import org.example.model.MeanPrice;
import org.example.util.PriceUtil;

public class MeanPriceAction implements MenuAction {
    private final PriceDataManager dataManager;

    public MeanPriceAction(PriceDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        var loaded = dataManager.getPrices();
        if (loaded.isEmpty()) {
            System.out.println("No prices available. Download prices first.");
            return;
            }
        // Temporary: if both days are loaded, take the first 24 hours as "today".
        var todayOnly = loaded.size() >= 24 ? loaded.subList(0, 24) : loaded;
        MeanPrice meanPrice = PriceUtil.calculateMeanPrice(todayOnly);
        System.out.println("Mean price for zone " + dataManager.getZone() + " (today):");
        System.out.println(meanPrice);
    }

    @Override
    public String getDescription() {
        return "Calculate the mean price for the current day";
    }
}
