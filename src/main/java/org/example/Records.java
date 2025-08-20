package org.example;

import java.util.Objects;

public class Records {

    static void main() {
        final Box box = new Box();

        box.width = 10;
        box.height = 20;
        box.length = 15;

        BoxRecord boxRecord = new BoxRecord(10, 20, 15);
        BoxRecord largeBox = new BoxRecord(100, 200, 100);
        BoxRecord smallBox = new BoxRecord(10, 20, 15);
        var anotherLargeBox = new BoxRecord(100, 200, 100);

        if( largeBox.equals( anotherLargeBox ))
            System.out.println("Large Box is equal to AnotherLarge Box");
    }
}

class Box {
    int width;
    int height;
    int length;
}

//Immutable - kan inte ändras efter skapandet
record BoxRecord(int width, int height, int length) {}