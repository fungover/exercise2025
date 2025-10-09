package org.example.domain;

import java.util.Objects;

public record Pet(Long id, String name, String species, int hungerLevel, int happiness) {
    public Pet(Long id, String name, String species, int hungerLevel, int happiness) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name");
        this.species = Objects.requireNonNull(species, "species");
        this.hungerLevel = clamp(hungerLevel, 0, 100);
        this.happiness = clamp(happiness, 0, 100);
    }

    private static int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }

    public Pet withId(Long newId) {
        return new Pet(newId, name, species, hungerLevel, happiness);
    }

    /**
     * Domain behaviors return new instances
     */
    public Pet feed(int amount) {
        int a = Math.max(1, amount);
        return new Pet(id, name, species, clamp(hungerLevel - a, 0, 100), happiness);
    }

    public Pet play(int amount) {
        int a = Math.max(1, amount);
        return new Pet(id, name, species, hungerLevel, clamp(happiness + a, 0, 100));
    }
}
