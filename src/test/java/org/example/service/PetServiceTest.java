package org.example.service;

import org.example.dto.PetDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PetService
 */
class PetServiceTest {

    private PetService petService;

    @BeforeEach
    void setUp() {
        petService = new PetService();
    }

    @Test
    void testAdoptPet() {
        PetDTO pet = new PetDTO("Fluffy", "cat", 50, 80);
        PetDTO adoptedPet = petService.adoptPet(pet);

        assertNotNull(adoptedPet.getId());
        assertEquals("Fluffy", adoptedPet.getName());
        assertEquals("cat", adoptedPet.getSpecies());
        assertEquals(50, adoptedPet.getHungerLevel());
        assertEquals(80, adoptedPet.getHappiness());
    }

    @Test
    void testAdoptMultiplePets_UniqueIds() {
        PetDTO pet1 = new PetDTO("Fluffy", "cat", 50, 80);
        PetDTO pet2 = new PetDTO("Rex", "dog", 30, 90);

        PetDTO adopted1 = petService.adoptPet(pet1);
        PetDTO adopted2 = petService.adoptPet(pet2);

        assertNotEquals(adopted1.getId(), adopted2.getId());
    }

    @Test
    void testGetAllPets() {
        petService.adoptPet(new PetDTO("Fluffy", "cat", 50, 80));
        petService.adoptPet(new PetDTO("Rex", "dog", 30, 90));

        List<PetDTO> pets = petService.getAllPets();
        assertEquals(2, pets.size());
    }

    @Test
    void testGetPetById_Found() {
        PetDTO adopted = petService.adoptPet(new PetDTO("Fluffy", "cat", 50, 80));
        Optional<PetDTO> found = petService.getPetById(adopted.getId());

        assertTrue(found.isPresent());
        assertEquals("Fluffy", found.get().getName());
    }

    @Test
    void testGetPetById_NotFound() {
        Optional<PetDTO> found = petService.getPetById(999L);
        assertFalse(found.isPresent());
    }

    @Test
    void testFeedPet_ReducesHunger() {
        PetDTO adopted = petService.adoptPet(new PetDTO("Fluffy", "cat", 50, 80));
        Optional<PetDTO> fed = petService.feedPet(adopted.getId(), 20);

        assertTrue(fed.isPresent());
        assertEquals(30, fed.get().getHungerLevel());
    }

    @Test
    void testFeedPet_MinimumZero() {
        PetDTO adopted = petService.adoptPet(new PetDTO("Fluffy", "cat", 10, 80));
        Optional<PetDTO> fed = petService.feedPet(adopted.getId(), 20);

        assertTrue(fed.isPresent());
        assertEquals(0, fed.get().getHungerLevel());
    }

    @Test
    void testFeedPet_NotFound() {
        Optional<PetDTO> fed = petService.feedPet(999L, 20);
        assertFalse(fed.isPresent());
    }

    @Test
    void testPlayWithPet_IncreasesHappiness() {
        PetDTO adopted = petService.adoptPet(new PetDTO("Rex", "dog", 30, 50));
        Optional<PetDTO> played = petService.playWithPet(adopted.getId(), 30);

        assertTrue(played.isPresent());
        assertEquals(80, played.get().getHappiness());
    }

    @Test
    void testPlayWithPet_MaximumHundred() {
        PetDTO adopted = petService.adoptPet(new PetDTO("Rex", "dog", 30, 95));
        Optional<PetDTO> played = petService.playWithPet(adopted.getId(), 20);

        assertTrue(played.isPresent());
        assertEquals(100, played.get().getHappiness());
    }

    @Test
    void testPlayWithPet_NotFound() {
        Optional<PetDTO> played = petService.playWithPet(999L, 20);
        assertFalse(played.isPresent());
    }

    @Test
    void testReleasePet_Success() {
        PetDTO adopted = petService.adoptPet(new PetDTO("Fluffy", "cat", 50, 80));
        boolean released = petService.releasePet(adopted.getId());

        assertTrue(released);
        assertFalse(petService.getPetById(adopted.getId()).isPresent());
    }

    @Test
    void testReleasePet_NotFound() {
        boolean released = petService.releasePet(999L);
        assertFalse(released);
    }

    @Test
    void testGetPetsBySpecies() {
        petService.adoptPet(new PetDTO("Fluffy", "cat", 50, 80));
        petService.adoptPet(new PetDTO("Whiskers", "cat", 40, 70));
        petService.adoptPet(new PetDTO("Rex", "dog", 30, 90));

        List<PetDTO> cats = petService.getPetsBySpecies("cat");
        assertEquals(2, cats.size());
        assertTrue(cats.stream().allMatch(p -> p.getSpecies().equalsIgnoreCase("cat")));
    }

    @Test
    void testGetPetsWithPagination() {
        for (int i = 1; i <= 5; i++) {
            petService.adoptPet(new PetDTO("Pet" + i, "dog", 50, 80));
        }

        List<PetDTO> page1 = petService.getPetsWithPagination(0, 2);
        List<PetDTO> page2 = petService.getPetsWithPagination(2, 2);

        assertEquals(2, page1.size());
        assertEquals(2, page2.size());
    }

    @Test
    void testGetPetsWithSorting_ByHappiness_Asc() {
        petService.adoptPet(new PetDTO("Pet1", "dog", 50, 90));
        petService.adoptPet(new PetDTO("Pet2", "cat", 40, 50));
        petService.adoptPet(new PetDTO("Pet3", "dog", 30, 70));

        List<PetDTO> sorted = petService.getPetsWithSorting("happiness", "asc");

        assertEquals(50, sorted.get(0).getHappiness());
        assertEquals(70, sorted.get(1).getHappiness());
        assertEquals(90, sorted.get(2).getHappiness());
    }

    @Test
    void testGetPetsWithSorting_ByHappiness_Desc() {
        petService.adoptPet(new PetDTO("Pet1", "dog", 50, 90));
        petService.adoptPet(new PetDTO("Pet2", "cat", 40, 50));
        petService.adoptPet(new PetDTO("Pet3", "dog", 30, 70));

        List<PetDTO> sorted = petService.getPetsWithSorting("happiness", "desc");

        assertEquals(90, sorted.get(0).getHappiness());
        assertEquals(70, sorted.get(1).getHappiness());
        assertEquals(50, sorted.get(2).getHappiness());
    }

    @Test
    void testGetPetsWithFilters_Combined() {
        petService.adoptPet(new PetDTO("Cat1", "cat", 50, 90));
        petService.adoptPet(new PetDTO("Cat2", "cat", 40, 50));
        petService.adoptPet(new PetDTO("Dog1", "dog", 30, 70));
        petService.adoptPet(new PetDTO("Cat3", "cat", 60, 80));

        // Get cats, sorted by happiness desc, skip first, get 2
        List<PetDTO> filtered = petService.getPetsWithFilters("cat", "happiness", "desc", 1, 2);

        assertEquals(2, filtered.size());
        assertTrue(filtered.stream().allMatch(p -> p.getSpecies().equalsIgnoreCase("cat")));
    }

    @Test
    void testGetPetCount() {
        petService.adoptPet(new PetDTO("Pet1", "dog", 50, 80));
        petService.adoptPet(new PetDTO("Pet2", "cat", 40, 70));

        assertEquals(2, petService.getPetCount());
    }

    @Test
    void testGetPetCountBySpecies() {
        petService.adoptPet(new PetDTO("Cat1", "cat", 50, 80));
        petService.adoptPet(new PetDTO("Cat2", "cat", 40, 70));
        petService.adoptPet(new PetDTO("Dog1", "dog", 30, 90));

        assertEquals(2, petService.getPetCountBySpecies("cat"));
        assertEquals(1, petService.getPetCountBySpecies("dog"));
    }
}
