package org.example.factory;

public interface Animal {

    //Factory method
    public static Animal createAnimal(String animalType){
        if( animalType.equals("dog"))
            return new Dog();
        else if( animalType.equals("cat"))
            return new Cat();
        else
            return new UnknownAnimal();
    }

}
