package org.example.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Picks one option from a list according to integer weights.
 * Example: add(SWORD, 60), add(AXE, 40) -> SWORD ~60%, AXE ~40%.
 */
public final class WeightedPicker<ValueType> {

        public record WeightedOption<ValueType>(ValueType value, int weight) {
        public WeightedOption {
            if (value == null) throw new IllegalArgumentException("value cannot be null");
            if (weight <= 0) throw new IllegalArgumentException("weight must be > 0");
        }
        }

    /** Builder to collect options with weights, then build the picker. */
    public static final class Builder<ValueType> {
        private final List<WeightedOption<ValueType>> options = new ArrayList<>();

        public Builder<ValueType> add(ValueType value, int weight) {
            options.add(new WeightedOption<>(value, weight));
            return this;
        }

        public WeightedPicker<ValueType> build() {
            return new WeightedPicker<>(options);
        }
    }

    private final List<WeightedOption<ValueType>> options;
    private final int totalWeight;

    public WeightedPicker(List<WeightedOption<ValueType>> options) {
        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException("WeightedPicker needs at least one option");
        }
        int sum = 0;
        for (WeightedOption<ValueType> option : options) {
            sum += option.weight;
        }
        this.options = List.copyOf(options);
        this.totalWeight = sum;
    }

    /** Randomly pick one option according to its weight. */
    public ValueType pick(Random random) {
        int randomNumber = random.nextInt(totalWeight);
        int runningTotal = 0;

        for (WeightedOption<ValueType> option : options) {
            runningTotal += option.weight;
            if (randomNumber < runningTotal) {
                return option.value;
            }
        }
        // Should never happen if weights are valid; fallback to last.
        return options.getLast().value;
    }
}
