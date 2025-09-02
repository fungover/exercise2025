package org.example.entities.items.weapons;

import org.example.entities.Position;

public class IronSword extends Weapon {
    public IronSword(Position position) {
        super("Iron Sword", "A sharp blade. +20 damage.", position, 20);
    }
}
