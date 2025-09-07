package org.game.entities;

import java.util.List;

public class Item {
    private String name;
    private String type;
    private int price;
    private int quantity;
    private List<String> slots;

    //Todo constructor for equipable items
    public Item(String name, String type, int price, int quantity, List<String> slots) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.slots = slots;
    }

    public Item(String name, String type, int price, int quantity) {
        this(name, type, price, quantity,null);
    }


    public boolean isEquippable(){
        return slots !=null && !slots.isEmpty();
    }

    public String getName() {return name;}
    public String getType() {return type;}
    public int getPrice() {return price;}
    public int getQuantity() {return quantity;}
    public List<String> getSlots() {return slots;}


    public void setName(String name) {this.name = name;}


    public void addQuantity(int quantity) {this.quantity += quantity;}
    public void removeQuantity(int quantity) {this.quantity -= quantity;}
}
