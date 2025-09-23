package org.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.detective.Detective;

@ApplicationScoped
public class Investigation {
    private final Detective detective;

    @Inject
    public Investigation(Detective detective) {
        this.detective = detective;
    }

    public void startInvestigation(String caseNumber) {
        System.out.println(detective.investigate(caseNumber));
    }
}
