package org.example.entities.items;

public abstract class Item {
    private String type;
    private int quantity;

    public Item(String type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
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
