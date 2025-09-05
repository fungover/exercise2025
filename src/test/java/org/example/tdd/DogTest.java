package org.example.tdd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DogTest {

    @Test
    public void canCreateDogObject() {
        Dog dog = new Dog();
    }

    @Test
    public void canCreateDogWithNameAndAge() {
        Dog dog = new Dog("Pluto", 14);
        assertEquals("Pluto", dog.name() );
        assertEquals(14, dog.age());
    }





}
