package org.example.entities.items;

import org.example.entities.Position;

public abstract class Item implements Position {
    private final String type;
    private int quantity;
    private int x;
    private int y;

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

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
