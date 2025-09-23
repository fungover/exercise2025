package org.example.detective;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.repository.Crime;

@ApplicationScoped
public class SherlockHolmes implements Detective {
    private final Crime crime;

    @Inject
    public SherlockHolmes(Crime crime) {
        this.crime = crime;
    }

    @Override
    public String investigate(String caseNumber) {
        String clues= crime.getCrimeDetails(caseNumber);
        return "Investigating case number: " + caseNumber + " with clues: " + clues;
    }
}
