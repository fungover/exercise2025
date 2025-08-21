package org.example.printers;

public class HelloPrinter {
    public static void sayHello() {
        System.out.println("Hello");
    }

    public static void sayHello(String name) {
        sayHello(name, 1);
    }

    public static void sayHello(String name, int number) {
        System.out.println("Hello " + name);
    }

    public static void sayHello(int number) {
        System.out.println("Hello " + number);
    }
}
