package org.example;

import org.example.detective.SherlockHolmes;
import org.example.repository.CrimeArchive;

public class App {
    public static void main(String[] args) {
        var crimeRepo = new CrimeArchive();
        var detective = new SherlockHolmes(crimeRepo);
        var engine = new Investigation(detective);

        engine.startInvestigation("CASE-221B");
    }
}
