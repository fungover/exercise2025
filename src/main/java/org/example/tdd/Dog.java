package org.example.tdd;

public class Dog {

    private final String name;
    private int age;

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String name() {
        return name;
    }

    public int age() {
        return age;
    }

    public void increaseAgeByOne() {
        this.age++;
    }

    public String describe() {
        return name + " är " + age + " år gammal.";
    }

    public String bark() {
        return "Voff!";
    }
}
