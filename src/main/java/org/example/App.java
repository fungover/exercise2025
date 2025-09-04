package org.example;

import java.io.IOException;
import java.time.LocalDate;

public class App {

    public static void main(String[] args) throws IOException {

        try {
            String priceZone = Menu.chooseArea();

            PricePerHour[] today = Fetch.fetch(LocalDate.now(), priceZone);
            PricePerHour[] tomorrow = Fetch.fetch(LocalDate.now().plusDays(1), priceZone);

            if(today.length > 0){
                System.out.print("\n---- Dagens priser ---- ");
                Printer.printCalculatedPrices(today);
                Printer.printBestChargingPeriods(today);
            }

            System.out.println();

            if(tomorrow.length > 0){
                System.out.print("---- Morgondagens priser ----");
                Printer.printCalculatedPrices(tomorrow);
                Printer.printBestChargingPeriods(tomorrow);
            }


        } catch (IOException | InterruptedException e) {
            System.err.println("Fel: " + e.getMessage());
        }


    }
}

