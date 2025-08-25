package org.example;

import java.awt.*;

public class Rectangle {
    private int width;
    private int height;
    private Color color = Color.BLACK;

    //Default constructor
    public Rectangle(){
        this(1,1);  //Constructor chaining
    }

    public Rectangle(int width, int height){
        this(width,height,Color.BLACK);
    }

    //Parameterized constructor
    public Rectangle(int width, int height, Color color){
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setWidth(int width) {
        if (width < 0) {
            throw new IllegalArgumentException("Width cannot be negative");
        }
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public int getArea() {
        return width * height;
    }
}