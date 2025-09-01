package org.example.service;

public class EquipmentOutcome {
    private final boolean success;
    private final String message;

    public EquipmentOutcome(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
