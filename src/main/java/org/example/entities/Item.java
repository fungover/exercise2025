package org.example.entities;

public abstract class Item {
    protected String name;
    protected int x;
    protected int y;

    public Item(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() { return name; }
    public int getX() { return x; }
    public int getY() { return y; }

    public abstract void use(Player player);
}


