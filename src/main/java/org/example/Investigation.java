package org.example;

import org.example.detective.Detective;

public class Investigation {
    private final Detective detective;

    public Investigation(Detective detective) {
        this.detective = detective;
    }

    public void startInvestigation(String caseNumber) {
        System.out.println(detective.investigate(caseNumber));
    }
}
