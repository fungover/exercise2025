package org.example;

import org.example.printers.HelloPrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        HelloPrinter.sayHello("Test");
        HelloPrinter.sayHello();

//        int x = 1;
//        test(x);
//        System.out.println(x);

        int[] array = { 1, 2, 3 };
        //To protect the content of our array, we need to create a copy.
        var copy = Arrays.copyOf(array, array.length);

        test(copy);
        System.out.println(array[0]);

        ArrayList<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");

        var copyOfList = List.of(list);


    }

    static void test(int[] values){
        values[0] = 4;
        System.out.println(values[0]);
    }

    static void test(int value) {
        value++;
        System.out.println(value);
    }



}