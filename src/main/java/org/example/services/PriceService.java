package org.example.services;
import org.example.api.PriceApiClient;
import java.io.IOException;
import java.time.LocalDate;

public record PriceService(PriceApiClient apiClient) {

    public String handleChoice(int mainMenuChoice, LocalDate date, String areaCode) throws IOException, InterruptedException {
        return switch (mainMenuChoice) {
            case 1 -> downloadPrices(date, areaCode);
            case 2 -> calculateMean(areaCode);
            case 3 -> findExtremes(areaCode);
            case 4 -> bestChargingWindow(areaCode);
            default -> "Invalid option!";
        };

    }


    private String downloadPrices(LocalDate date, String areaCode) throws IOException, InterruptedException {
        return apiClient.fetchPrices(date, areaCode);
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
