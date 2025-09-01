package org.example.service;

public class ItemUseOutcome {
    private final boolean success;
    private final String message;

    public ItemUseOutcome(boolean success, String message) {
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
