package org.example.entities;

public interface Boss {
    void performSpecialAttack(Player player);

    boolean hasSpecialAttack();

    String getDefeatMessage();
}
