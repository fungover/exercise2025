package org.example.menu.actions;

import org.example.data.PriceDataManager;
import org.example.menu.InputHandler;
import org.example.menu.MenuAction;
import org.example.model.HourlyPrice;
import org.example.util.PriceUtil;

import java.util.List;

public class BestChargingTimeAction implements MenuAction {
    private final PriceDataManager dataManager;
    private final InputHandler inputHandler;

    public BestChargingTimeAction(PriceDataManager dataManager, InputHandler inputHandler) {
        this.dataManager = dataManager;
        this.inputHandler = inputHandler;
    }

    @Override
    public void execute() {
        List<HourlyPrice> prices = dataManager.getPrices();
        if (prices.isEmpty()) {
            System.out.println("No prices available.");
            return;
        }

        String raw = inputHandler.getStringInput("Enter charging duration (2, 4 or 8 hours):");
        int duration;
        try {
            duration = Integer.parseInt(raw.trim());
            } catch (NumberFormatException e) {
            System.out.println("Invalid number. Choose 2, 4 or 8 hours.");
            return;
            }
        if (duration != 2 && duration != 4 && duration != 8) {
            System.out.println("Invalid duration. Choose 2, 4 or 8 hours.");
            return;
            }
        if (prices.size() < duration) {
            System.out.println("Not enough price data for the selected duration.");
            return;
            }

        double minSekCost = Double.MAX_VALUE;
        double minEurCost = Double.MAX_VALUE;
        int bestStartHour = 0;
        for (int i =0; i <= prices.size() - duration; i++) {
            double windowSekCost = 0;
            double windowEurCost = 0;
            for (int j = i; j < i + duration; j++) {
                windowSekCost += prices.get(j).getSekPerKwh();
                windowEurCost += prices.get(j).getEurPerKwh();
            }
            if (windowSekCost < minSekCost) {
                minSekCost = windowSekCost;
                minEurCost = windowEurCost;
                bestStartHour = prices.get(i).getHour();
            }
        }

        System.out.println(
                "Best time to charge for " + duration + " hours: Hour " +
                bestStartHour + " (Total cost: SEK " +
                PriceUtil.formatPrice(minSekCost) + ", EUR " +
                PriceUtil.formatPrice(minEurCost) + ")");
    }

    @Override
    public String getDescription() {
        return "Calculate the best time to charge for a given duration";
    }
}
