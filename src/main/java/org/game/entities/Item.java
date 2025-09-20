package org.game.entities;

import java.util.List;

public class Item {
    private String name;
    private String type;
    private int price;
    private int quantity;
    private List<String> slots;


    private int healthBonus;
    private int strengthBonus;
    private int defenseBonus;

    private boolean consumable;
    private int healAmount;


    //Todo constructor for equipable items
    public Item(String name, String type, int price, int quantity, List<String> slots, int strengthBonus, int defenseBonus, int healthBonus) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.slots = slots;
        this.healthBonus = healthBonus;
        this.strengthBonus = strengthBonus;
        this.defenseBonus = defenseBonus;

    }
    //Todo constructor for non equipable items
    public Item(String name, String type, int price, int quantity) {
        this.name = name;
        this.type = type;
        this.price  = price;
        this.quantity  = quantity;
        this.slots =null;
    }

    // constructor for consumables
    public Item(String name, String type, int price, int quantity,boolean consumable, int healAmount) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.slots = null;
        this.consumable = true;
        this.healAmount = healAmount;
    }


    public boolean isEquippable(){
        return slots !=null && !slots.isEmpty();
    }
    public boolean isConsumable(){return  consumable;}

    public String getName() {return name;}
    public String getType() {return type;}
    public int getPrice() {return price;}
    public int getQuantity() {return quantity;}
    public List<String> getSlots() {return slots;}
    public int getStrengthBonus() {return strengthBonus;}
    public int getDefenseBonus() {return defenseBonus;}
    public int getHealthBonus() {return healthBonus;}
    public int getHealAmount() {return healAmount;}


    public void setName(String name) {this.name = name;}


    public void addQuantity(int quantity) {this.quantity += quantity;}
    public void removeQuantity(int quantity) {this.quantity -= quantity;}
}
