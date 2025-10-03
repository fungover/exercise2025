package org.example.service;

import org.example.dto.PetDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PetService
 * Each test checks one piece of behavior
 */
public class PetServiceTest {

    private PetService petService;

    @BeforeEach
    void setUp() {
        petService = new PetService();
    }

    @Test
    void testAddAndGetPet() {
        PetDTO pet = new PetDTO("Figo", "dog", 50, 50);
        Long id = petService.addPet(pet);

        PetDTO found = petService.getPetById(id);

        assertNotNull(found);
        assertEquals("Figo", found.getName());
        assertEquals("dog", found.getSpecies());
    }

    @Test
    void testGetAllPets() {
        petService.addPet(new PetDTO("Figo", "dog", 50, 50));
        petService.addPet(new PetDTO("Tom", "cat", 60, 60));

        Collection<PetDTO> pets = petService.getAllPets();

        assertEquals(2, pets.size());
    }

    @Test
    void testDeletePet() {
        // Add a pet
        Long id = petService.addPet(new PetDTO("Figo", "dog", 50, 50));

        // Verify that pet exists before deletion
        assertNotNull(petService.getPetById(id));

        // Verify that deletePet reports success
        boolean deleted = petService.deletePet(id);
        assertTrue(deleted);

        // Verify that pet is really gone
        assertNull(petService.getPetById(id));
    }

    @Test
    void testFeedPet() {
        Long id = petService.addPet(new PetDTO("Figo", "dog", 50, 50));

        petService.feedPet(id);
        PetDTO pet = petService.getPetById(id);

        assertEquals(40, pet.getHungerLevel());
    }

    @Test
    void testFeedPetNotBelowZero() {
        Long id = petService.addPet(new PetDTO("Figo", "dog", 5, 50));

        petService.feedPet(id);
        PetDTO pet = petService.getPetById(id);

        assertEquals(0, pet.getHungerLevel());
    }

    @Test
    void testPlayWithPet() {
        Long id = petService.addPet(new PetDTO("Figo", "dog", 50, 50));

        petService.playWithPet(id);
        PetDTO pet = petService.getPetById(id);

        assertEquals(60, pet.getHappiness());
    }

    @Test
    void testPlayWithPetNotAboveHundred() {
        Long id = petService.addPet(new PetDTO("Figo", "dog", 50, 95));

        petService.playWithPet(id);
        PetDTO pet = petService.getPetById(id);

        assertEquals(100, pet.getHappiness());
    }


}
