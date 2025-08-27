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

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        List<Elpris> prices = new ArrayList<>();
                prices.addAll(fetcher.getPrice(zon, today));
                prices.addAll(fetcher.getPrice(zon, tomorrow));

        if (prices.isEmpty()) {
            System.out.println("Inga priser hittades.");
            return;
        }

        // 1. Analyze
        double mean = ElprisAnalyzer.mean(prices);
        Elpris cheapest = ElprisAnalyzer.cheapest(prices);
        Elpris expensive = ElprisAnalyzer.mostExpensive(prices);

        System.out.printf("Medelpris:  %.2f kr%n", mean);
        System.out.printf("Billigaste timme: %s %02d:00 (%.2f kr)%n",
        cheapest.getTimeStart().toLocalDate(),
        cheapest.getTimeStart().getHour(), cheapest.getSEK());
        System.out.printf("Dyraste timme: %s %02d:00 (%.2f kr)%n",
        expensive.getTimeStart().toLocalDate(),
        expensive.getTimeStart().getHour(), expensive.getSEK());

        for (int h: new int[]{2, 4, 8}) {
            Elpris start = ElprisAnalyzer.bestPeriod(prices, h);
            double avg = ElprisAnalyzer.periodAverage(prices, start, h);
            System.out.printf("Bästa tid för %dh laddning: %s %02d:00, medelpris %.2f kr%n",
                    h,
                    start.getTimeStart().toLocalDate(),
                    start.getTimeStart().getHour(),
                    avg);
        }




        System.out.println("\nAlla timpriser:");
        for (Elpris elpris :prices) {
            System.out.printf("%s %02d:00 - %02d:00: %.2f kr (%.4f EUR, EXR: %.4f)%n",
                    elpris.getTimeStart().toLocalDate(),
                    elpris.getTimeStart().getHour(),
                    elpris.getTimeEnd().getHour(),
                    elpris.getSEK(),
                    elpris.getEUR(),
                    elpris.getEXR()
            );
        }
        // CSV-handler: Reads file and calculate cost with ConsumptionCSVReader + CostCalc.
        System.out.print("\nVill du importera en CSV med förbrukning och räkna kostnad? (j/n): ");
        String answer = scanner.nextLine().trim().toLowerCase();
        if (answer.equals("j")) {
            System.out.print("Ange sökväg till CSV: ");
            String path = scanner.nextLine().trim().toLowerCase();

            try {
                List<Consumption> usage = ConsumptionCSVReader.readCSV(path);
                double cost = CostCalc.calculateTotalCost(usage, prices);
                System.out.printf("Total kostnad: %2f kr%n", cost);
            } catch (Exception e) {
                System.out.println("Fel vid läsning eller beräkning: " + e.getMessage());
            }

        }
    }
}
