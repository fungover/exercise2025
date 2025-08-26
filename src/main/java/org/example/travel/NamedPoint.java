package org.example.travel;

public class NamedPoint {
    private final Point point;
    private final String name;

    public NamedPoint(Point point, String name) {
        this.point = point;
        this.name = name;
    }

    public String display() {
        return name + ": " + point.display();
    }
}
