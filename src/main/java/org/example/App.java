package org.example;

class App {
    static void main() {
        MeanPrice priceList = new MeanPrice();
        TopBottomPrice topBottomPrice = new TopBottomPrice();

        System.out.println();
        priceList.printMeanPrice();
        System.out.println();
        topBottomPrice.printTopBottomPrice();
    }
}