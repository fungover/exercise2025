package org.example;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException, InterruptedException {
        ElectricityPriceFetcher electricityPriceFetcher = new ElectricityPriceFetcher();
        Menu.showDefaultMenu(electricityPriceFetcher);
    }
}
