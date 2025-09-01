package org.example;
import java.io.IOException;
import java.time.LocalDate;

public class Printer {

    public static void printer() {

        Fetch fetch = new Fetch();
        String priceBracket = "SE3";

        // Today's prices
        try{
            PricePerHour[] today = fetch.fetch(LocalDate.now(), priceBracket);
            System.out.println("Dagens priser i " + priceBracket + ": ");
            for (PricePerHour p : today) {
                System.out.println(p.time_start() + " -- " + p.SEK_per_kWh() + " kr/kWh");
            }

            // Tomorrow's prices
            PricePerHour[] tomorrow = fetch.fetch(LocalDate.now().plusDays(1), priceBracket);
            System.out.println("\nMorgondagens priser i " + priceBracket + ": ");
            for (PricePerHour p : tomorrow) {
                System.out.println(p.time_start() + " -- " + p.SEK_per_kWh() + " kr/kWh");
            }
        }
        catch (IOException e) {
            System.err.println("Fel vid nätverks- eller filåtkomst: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Programmet avbröts: " + e.getMessage());
        }

    }

}
