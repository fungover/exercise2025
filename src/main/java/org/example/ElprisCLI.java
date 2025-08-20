package org.example;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;

public class ElprisCLI {
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Välj priszon (SE1, SE2, SE3, SE4): ");
        String zon = scanner.nextLine().trim().toUpperCase();

        ElprisFetch fetcher = new ElprisFetch();
        List<Elpris> priser = fetcher.hämtaPriser(zon, LocalDate.now());

        if (priser.isEmpty()) {
            System.out.println("Inga priser hittades.");
            return;
        }


        System.out.println("Priser för idag;");
        for (Elpris elpris :priser) {
            System.out.println(String.format("%s - %s: %.2f kr",
                    elpris.getTimeStart().getHour(),
                    elpris.getTimeEnd().getHour(),
                    elpris.getSEK()
            ));
        // API fetch
        }
    }
}
