package org.example;

public record PriceRequests(int year, int month, int day, String zone) {

    public String buildUrl() {
        return "https://www.elprisetjustnu.se/api/v1/prices/"
                + year + "/"
                + String.format("%02d", month) + "-"
                + String.format("%02d", day) + "_"
                + zone + ".json";


    }
}
