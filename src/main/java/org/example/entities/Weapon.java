package org.example.entities;

public abstract class Weapon extends Item implements Equippable {
    private final int damageBonus;

    public Weapon(String name, String description, Position position, int damageBonus) {
        super(name, description, position);
        this.damageBonus = damageBonus;
    }

    @Override
    public void equip(Player player) {
        player.setDamage(player.getDamage() + damageBonus);
        System.out.println("Equipped " + name + "! Damage increased by " + damageBonus + ".");
    }

    @Override
    public void unequip(Player player) {
        player.setDamage(player.getDamage() - damageBonus);
        System.out.println("Unequipped " + name + ". Damage decreased by " + damageBonus + ".");
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.WEAPON;
    }

    @Override
    public int getStatBonus() {
        return damageBonus;
    }
}
