package org.example.travel;

public class NamedPoint extends Point {

    private final String name;

    public NamedPoint(String name, int x, int y) {
        super(x, y);
        this.name = name;
    }

    @Override
    public String display() {
        return name + ": " + super.display();
    }
}
