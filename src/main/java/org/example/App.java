package org.example;

public class App {

    public static void main(String[] args) {
        int i = 0;
        while (i < 3) {
            System.out.println("Hello World!");
            i++;
        }
        for(int j = 0; j < 3; j++) {
            System.out.println("Hello World!");
        }
        //Loop using recursion, be carful to not fill the stack == stack overflow
        counter(0);
    }

    public static void counter(int i) {
        if( i == 3)
            return;
        System.out.println(i);
        counter(i + 1);
    }

    public static void demo() {
        System.out.println("Demo");
    }

    public static int sum(int a, int b) {
        return a + b;
    }

}
