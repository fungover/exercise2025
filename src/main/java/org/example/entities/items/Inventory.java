package org.example.entities.items;

public class Inventory {
    private int coins;
    private Weapon weapon;
    private HealthPotion healthPotion;

    public Inventory(int coinAmount, String weaponName, int potionAmount) {
        this.coins = coinAmount;
        this.weapon = weaponConstructor(weaponName);
        this.healthPotion = potionConstructor(potionAmount);
    }

    private Weapon weaponConstructor(String weaponName) {
        if (weaponName.equals("Sword")) {
            return new Sword();
        } else if (weaponName.equals("Dagger")) {
            return new Dagger();
        } else {
            System.out.println("Error: No weapon of that name, returning null!");
            return null;
        }
    }

    private HealthPotion potionConstructor(int potionAmount) {
        return new HealthPotion(potionAmount);
    }

    public void setCoins(int amount) {
        this.coins = amount;
    }

    public void setWeapon(String name) {
        this.weapon = weaponConstructor(name);
    }

    public void setHealthPotion(int amount) {
        this.healthPotion.setAmount(amount);
    }

    public int getCoins() {
        return coins;
    }

    public String getWeaponName() {
        return weapon.getName();
    }

    public int getWeaponDamage() {
        return weapon.getDamage();
    }

    public int getHealthPotions() {
        return healthPotion.getAmount();
    }

    public int useHealthPotion() {
        return healthPotion.getHealthRestored();
    }

}
