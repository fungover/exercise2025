package org.example.store;

import org.example.store.animals.*;

import java.util.ArrayList;
import java.util.List;

public class PetStore {
    private String name;
    private List<Animal> animals = new ArrayList<>();

    public PetStore(String name) {
        this.name = name;
    }

    public void printAllAnimals(List<Animal> animals){

    }

    public void run(){
        System.out.println("Welcome to " + name);
        //Initialize the store with some animals
        Animal animal1 = new Kiwi();
        animals.add(animal1);
        Animal buttefly = new ButterFly();
        animals.add(buttefly);
        animals.add(new SeaGull());

        printAllAnimals(animals);

        for (Animal animal : animals) {
            System.out.println(animal.name() + " wants to eat: " +  animal.preferredFood());
            if( animal instanceof Airborne airborne)
                System.out.println(animal.name() + " flies " + airborne.fly());
            if( animal instanceof SeaCreature seaCreature)
                System.out.println(animal.name() + " swims " + seaCreature.swim());
        }

    }



    static void main() {
        PetStore petStore = new PetStore("Endangered pets");
        petStore.run();
    }
}
