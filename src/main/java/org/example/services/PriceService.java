package org.example.services;

public class PriceService {
    public String handleChoice(int mainMenuChoice, String areaCode) {
        return switch (mainMenuChoice) {
            case 1 -> downloadPrices(areaCode);
            case 2 -> calculateMean(areaCode);
            case 3 -> findExtremes(areaCode);
            case 4 -> bestChargingWindow(areaCode);
            default -> "Invalid option!";
        };

    }

    private String downloadPrices(String areaCode) {
        return "Downloading prices for area " + areaCode + ": ... (not implemented yet)";
    }

    private String calculateMean(String areaCode) {
        return "Mean price for area " + areaCode + ": ... (not implemented yet)";
    }

    private String findExtremes(String areaCode) {
        return "Cheapest and most expensive hours for area " + areaCode + ": ... (not implemented yet)";
    }

    private String bestChargingWindow(String areaCode) {
        return "Best charging window for area " + areaCode + ": ... (not implemented yet)";
    }
}
