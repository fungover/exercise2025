package org.game.entities;

public class Item {
    private String name;
    private String type;
    private int price;
    private int quantity;

    public Item(String name, String type, int price, int quantity) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {return name;}
    public String getType() {return type;}
    public int getPrice() {return price;}
    public int getQuantity() {return quantity;}
    public void setName(String name) {this.name = name;}

    public void addQuantity(int quantity) {this.quantity += quantity;}
    public void removeQuantity(int quantity) {this.quantity -= quantity;}
}
