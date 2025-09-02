package org.example;
import java.io.IOException;
import java.time.LocalDate;

public class App {

    static void main(String[] args) throws IOException, InterruptedException {

        String priceBracket = "SE3";

        try {
           PricePerHour[] today = Fetch.fetch(LocalDate.now(), priceBracket);
           PricePerHour[] tomorrow = Fetch.fetch(LocalDate.now().plusDays(1), priceBracket);
            System.out.print("\n---- Dagens priser ---- ");
           Printer.printCalculatedPrices(today);
           System.out.println();
            System.out.print("---- Morgondagens priser ----");
           Printer.printCalculatedPrices(tomorrow);

        } catch (IOException | InterruptedException e) {
            System.err.println("Fel: " + e.getMessage());
        }
    }


}

