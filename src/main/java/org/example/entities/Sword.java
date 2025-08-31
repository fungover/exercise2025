package org.example.entities;

public class Sword extends Item {
    public Sword() {
        super("Iron Sword", "A sharp iron sword (+5 dmg)", ItemType.Weapon);
    }

    @Override public void use(Player player) {
        //auto equipping everything seems best right now
        System.out.println("You equipped the " + name + "! (Weapon was equipped!)");
    }
}
