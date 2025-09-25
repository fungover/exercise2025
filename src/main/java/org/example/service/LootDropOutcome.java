package org.example.service;

public class LootDropOutcome {
    private final boolean itemDropped;
    private final String message;

    public LootDropOutcome(boolean itemDropped, String message) {
        this.itemDropped = itemDropped;
        this.message = message;
    }

    public boolean isItemDropped() {
        return itemDropped;
    }

    public String getMessage() {
        return message;
    }
}
