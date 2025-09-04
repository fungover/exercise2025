package org.example.entities;

public class Sword extends Weapon {
    public Sword() {
        super("Iron Sword", "A sharp iron sword (+5 dmg)", 5);
    }

    //    @Override public void use(Player player) {
    //        //auto equipping everything seems best right now
    //        System.out.println("You equipped the " + name + "! (Weapon was equipped!)");
    //    }
}
