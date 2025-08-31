package org.example.entities;

public class Weapon extends Item {
    private int damageBonus;

    public Weapon(String name, int damageBonus) {
        super(name);
        this.damageBonus = damageBonus;
    }
    public int getDamageBonus() {
        return damageBonus;
    }

    @Override
    public void use(Character character) {
        character.setBaseDamage(character.getBaseDamage() + damageBonus);
        System.out.println(character.getName() + " equips " + getName() + " (+" + damageBonus + " damage)!");
    }
}
