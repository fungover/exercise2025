package org.example.tdd;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DogTest {

    @Test
    public void canCreateDogWithNameAndAge() {
        Dog dog = new Dog("Pluto", 14);
        assertEquals("Pluto", dog.name());
        assertEquals(14, dog.age());
    }

    @Test
    public void canIncreaseAgeByOne() {
        Dog dog = new Dog("Buster", 1);
        dog.increaseAgeByOne();
        assertEquals(2, dog.age());
    }

    @ParameterizedTest
    @CsvSource({"Fido, 3, Fido är 3 år gammal.", "Hugo, 7, Hugo är 7 år gammal."})
    public void canGetDescriptionOfDog(String name, int age, String expected) {
        Dog dog = new Dog(name, age);
        String description = dog.describe();
        assertEquals(expected, description);
    }


}
