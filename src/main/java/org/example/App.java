package org.example;

class App {
    static void main() {
        PriceMapper mapper = new PriceMapper();
        MeanPrice meanPrice = new MeanPrice(mapper);
        TopBottomPrice topBottomPrice = new TopBottomPrice(mapper);
        ChargingPrice chargingPrice = new ChargingPrice(mapper);

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