package org.example.service;

public class CombatResult {
    private final String status; // EnemyDefeated, PlayerDefeated, Ongoing, Retreated
    private final String log;    // Textmeddelande

    public CombatResult(String status, String log) {
        this.status = status;
        this.log = log;
    }

    public String getStatus() {
        return status;
    }

    public String getLog() {
        return log;
    }
}