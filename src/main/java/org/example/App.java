package org.example;

class App {
    static void main() {
        MeanPrice meanPrice = new MeanPrice();
        TopBottomPrice topBottomPrice = new TopBottomPrice();
        ChargingPrice chargingPrice = new ChargingPrice();

        System.out.println();
        meanPrice.printMeanPrice();
        System.out.println();
        topBottomPrice.printTopBottomPrice();
        System.out.println();
        chargingPrice.printChargingPrice();
    }
}