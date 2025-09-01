package org.example.entities.items;

class HealthPotion {
    private int amount;

    public HealthPotion(int amount) {
        this.amount = amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getHealthRestored() {
        return 20;
    }

    public int getAmount() {
        return amount;
    }
}
