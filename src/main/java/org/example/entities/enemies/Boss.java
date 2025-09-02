package org.example.entities.enemies;

import org.example.entities.Player;

public interface Boss {
    void performSpecialAttack(Player player);

    boolean hasSpecialAttack();

    String getDefeatMessage();
}
