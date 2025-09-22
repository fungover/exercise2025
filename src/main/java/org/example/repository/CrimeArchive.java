package org.example.repository;

public class CrimeArchive implements Crime {
    @Override
    public String getCrimeDetails(String caseNumber){
        return "Crime scene: Baker street. Witness: Mrs Hudson.";
    }
}
