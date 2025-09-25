package org.example.entities;

/**
 Equippable weapon. Implementations are typically also Items.
 Contract: getDamage() >= 0 and getName() is non-blank.
 */
public interface Weapon {
    int getDamage();
    String getName();
}