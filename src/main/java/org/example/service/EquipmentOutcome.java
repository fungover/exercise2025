package org.example.service;

import java.util.Objects;

public class EquipmentOutcome {
    private final boolean success;
    private final String message;

    public EquipmentOutcome(boolean success, String message) {
        this.success = success;
        this.message = Objects.requireNonNullElse(message, "");
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public static EquipmentOutcome success(String message) {
        return new EquipmentOutcome(true, message);
    }

    public static EquipmentOutcome failure(String message) {
        return new EquipmentOutcome(false, message);
    }
}
