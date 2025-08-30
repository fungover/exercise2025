package org.example.menu.actions;

import org.example.data.PriceDataManager;
import org.example.menu.InputHandler;
import org.example.menu.MenuAction;

public class SelectZoneAction implements MenuAction {
    private final PriceDataManager dataManager;
    private final InputHandler inputHandler;

    public SelectZoneAction(PriceDataManager dataManager, InputHandler inputHandler) {
        this.dataManager = dataManager;
        this.inputHandler = inputHandler;
    }

    @Override
    public void execute() {
        String zone = inputHandler.getStringInput("Enter price zone (SE1, SE2, SE3 or SE4:");
        dataManager.setZone(zone);
        System.out.println("Price zone set to " + zone);
    }

    @Override
    public String getDescription() {
        return "Select the price zone";
    }
}
