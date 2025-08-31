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
        System.out.println("Cheapest charging prices:");
        chargingPrice.printCharge2();
        System.out.println();
        chargingPrice.printCharge4();
        System.out.println();
        chargingPrice.printCharge8();
    }
}