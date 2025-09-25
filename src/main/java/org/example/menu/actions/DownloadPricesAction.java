package org.example.menu.actions;

import org.example.data.PriceDataManager;
import org.example.menu.InputHandler;
import org.example.menu.MenuAction;
import org.example.model.HourlyPrice;

public class DownloadPricesAction implements MenuAction {
    private final PriceDataManager dataManager;
    private final InputHandler inputHandler;

    public DownloadPricesAction(PriceDataManager dataManager, InputHandler inputHandler) {
        this.dataManager = dataManager;
        this.inputHandler = inputHandler;
    }

    @Override
    public void execute() {
        String includeNextDay = inputHandler.getStringInput("Include next day prices? (y/n)");
        dataManager.downloadPrices("y".equalsIgnoreCase(includeNextDay));
        System.out.println("Prices for zone " + dataManager.getZone() + ":");
        for (HourlyPrice price : dataManager.getPrices()) {
            System.out.println(price);
        }
    }

    @Override
    public String getDescription() {
        return "Download prices for the current day";
    }
}
