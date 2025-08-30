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
        String zone = inputHandler.getStringInput("Enter price zone (SE1, SE2, SE3 or SE4):");
        if (zone == null || zone.trim().isEmpty()) {
            System.out.println("Invalid option, price zone unchanged.");
        } else if (zone.equalsIgnoreCase("SE1") || zone.equalsIgnoreCase("SE2") ||
                zone.equalsIgnoreCase("SE3") || zone.equalsIgnoreCase("SE4")) {
            String zoneUpper = zone.toUpperCase();
            dataManager.setZone(zoneUpper);
            System.out.println("Price zone set to " + zoneUpper);
        }
    }

    @Override
    public String getDescription() {
        return "Select the price zone";
    }
}
