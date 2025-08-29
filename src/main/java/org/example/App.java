package org.example;

class App {
    static void main() {
        MeanPrice priceList = new MeanPrice();
        TopBottomPrice topBottomPrice = new TopBottomPrice();

        System.out.println();
        priceList.getMeanPrice();
        System.out.println();
        topBottomPrice.getTopBottomPrice();
    }
}