package org.example;

class App {
    static void main() {
        MeanPrice priceList = new MeanPrice();
        TopOrBottomPrice topOrBottomPrice = new TopOrBottomPrice();

        System.out.println();
        priceList.getMeanPrice();
        System.out.println();
        topOrBottomPrice.getTopBottomPrice();
    }
}