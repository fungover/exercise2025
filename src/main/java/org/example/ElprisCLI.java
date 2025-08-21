package org.example;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;

public class ElprisCLI {
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Välj priszon (SE1, SE2, SE3, SE4): ");
        String zon = scanner.nextLine().trim().toUpperCase();

        ElprisFetch fetcher = new ElprisFetch();

        LocalDate idag = LocalDate.now();
        LocalDate imorgon = idag.plusDays(1);

        List<Elpris> priser = new ArrayList<>();
                priser.addAll(fetcher.getPrice(zon, idag));
                priser.addAll(fetcher.getPrice(zon, imorgon));

        if (priser.isEmpty()) {
            System.out.println("Inga priser hittades.");
            return;
        }

        // 1. Analyze
        double mean = ElprisAnalyzer.mean(priser);
        Elpris billigast = ElprisAnalyzer.cheapest(priser);
        Elpris dyrast = ElprisAnalyzer.mostExpensive(priser);

        System.out.printf("Medelpris:  %.2f kr%n", mean);
        System.out.printf("Billigaste timme: %s %02d:00 (%.2f kr)%n",
        billigast.getTimeStart().toLocalDate(),
        billigast.getTimeStart().getHour(), billigast.getSEK());
        System.out.printf("Dyraste timme: %s %02d:00 (%.2f kr)%n",
        dyrast.getTimeStart().toLocalDate(),
        dyrast.getTimeStart().getHour(), dyrast.getSEK());

        for (int h: new int[]{2, 4, 8}) {
            Elpris start = ElprisAnalyzer.bestPeriod(priser, h);
            double avg = ElprisAnalyzer.periodAverage(priser, start, h);
            System.out.printf("Bästa tid för %dh laddning: %s %02d:00, medelpris %.2f kr%n",
                    h,
                    start.getTimeStart().toLocalDate(),
                    start.getTimeStart().getHour(),
                    avg); // Right?
        }




        System.out.println("\nAlla timpriser:");
        for (Elpris elpris :priser) {
            System.out.printf("%s %02d:00 - %02d:00: %.2f kr%n",
                    elpris.getTimeStart().toLocalDate(),
                    elpris.getTimeStart().getHour(),
                    elpris.getTimeEnd().getHour(),
                    elpris.getSEK()
            );

        }
    }
}
