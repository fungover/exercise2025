package org.example;
import java.util.List;
public class PriceAnalyzer {
    public static void main(String[] args) {

    }
    public static void meanValueCalculator(List<ElectricityPrice> prices) {
        double sum = 0.0;
        for (ElectricityPrice price : prices) {
            System.out.println(price);
        }
    }
}
