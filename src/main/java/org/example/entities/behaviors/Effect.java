package org.example.entities.behaviors;

import org.example.entities.Player;

public interface Effect {
    void apply(Player player);
    int getValue();
    String toString();
}
