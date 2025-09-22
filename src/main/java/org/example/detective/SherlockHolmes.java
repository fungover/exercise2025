package org.example.detective;

import org.example.repository.Crime;

public class SherlockHolmes implements Detective {
    private final Crime crime;

    public SherlockHolmes(Crime crime) {
        this.crime = crime;
    }

    @Override
    public String investigate(String caseNumber) {
        String clues= crime.getCrimeDetails(caseNumber);
        return "Investigating case number: " + caseNumber + " with clues: " + clues;
    }
}
