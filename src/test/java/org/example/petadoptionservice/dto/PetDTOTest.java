package org.example.petadoptionservice.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PetDTOTest {
    @Test
    void constructor_setsFields() {
        PetDTO pet = new PetDTO(1L, "Jack", "Dog", 20, 90);
        assertEquals(1L, pet.id());
        assertEquals("Jack", pet.name());
        assertEquals("Dog", pet.species());
        assertEquals(20, pet.hungerLevel());
        assertEquals(90, pet.happiness());
    }
}
