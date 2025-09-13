package org.example;

import java.util.Scanner;

public class App {
    public static void main() {
        String zone;
        do {
            printAvailableZones();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your zone: ");
            zone = scanner.nextLine();
        } while (!zone.equals("1") && !zone.equals("2") &&
                !zone.equals("3") && !zone.equals("4"));

        PriceMapper mapper = new PriceMapper(zone);
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

    private static void printAvailableZones() {
        System.out.println();
        System.out.println("Here are some details about the current electricity prices!");
        System.out.println("Available zones:");
        System.out.println("SE1 = Luleå / Norra Sverige");
        System.out.println("SE2 = Sundsvall / Norra Mellansverige");
        System.out.println("SE3 = Stockholm / Södra Mellansverige");
        System.out.println("SE4 = Malmö / Södra Sverige ");
        System.out.println("Only type the 'number' of available zones!");
    }
}