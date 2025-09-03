package org.example;
import java.io.IOException;
import java.time.LocalDate;

public class App {

    static void main(String[] args) throws IOException, InterruptedException {


        String priceZone = Menu.chooseArea();

        try {
            PricePerHour[] today = Fetch.fetch(LocalDate.now(), priceZone);
            PricePerHour[] tomorrow = Fetch.fetch(LocalDate.now().plusDays(1), priceZone);
            System.out.print("\n---- Dagens priser ---- ");
            Printer.printCalculatedPrices(today);
            Printer.printBestChargingPeriods(today);
            System.out.println();
            System.out.print("---- Morgondagens priser ----");
            Printer.printCalculatedPrices(tomorrow);
            Printer.printBestChargingPeriods(tomorrow);

        } catch (IOException | InterruptedException e) {
            System.err.println("Fel: " + e.getMessage());
        }


    }
}

