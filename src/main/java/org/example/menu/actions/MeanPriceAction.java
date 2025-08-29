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
        MeanPrice meanPrice = PriceUtil.calculateMeanPrice(dataManager.getPrices());
        System.out.println("Mean price for zone " + dataManager.getZone() + ":");
        System.out.println(meanPrice);
    }

    @Override
    public String getDescription() {
        return "Calculate the mean price for the current day";
    }
}
