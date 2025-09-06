package org.example.entities.items;

public abstract class Item {
    private String name;
    private int quantity;

    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getType() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String displayInfo() {
        return "Type: " + getType() + ", Quantity: " + getQuantity();
    }

    public void addQuantity() {
        this.quantity += 1;
    }

    public void removeQuantity() {
        this.quantity -= 1;
    }
}
