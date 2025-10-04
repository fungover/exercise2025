package org.example.service;

import org.example.dto.PetDTO;
import org.example.repository.InMemoryPetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PetServiceTest {

    private PetService petService;

    @BeforeEach
    void setUp() {
        petService = new PetService();
        var repo = new InMemoryPetRepository();
        try {
            var field = PetService.class.getDeclaredField("petRepository");
            field.setAccessible(true);
            field.set(petService, repo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createPetSetsDefaultIfNull() {
        PetDTO pet = new PetDTO("Doris", "Dog", null, null);
        PetDTO created = petService.createPet(pet);

        assertThat(created.getId()).isNotNull();
        assertThat(created.getHungerLevel()).isEqualTo(50);
        assertThat(created.getHappiness()).isEqualTo(50);
    }

    @Test
    void createPetRespectsExplicitValues() {
        PetDTO pet = new PetDTO("Doris", "Dog", 0, 0);
        PetDTO created = petService.createPet(pet);

        assertThat(created.getHungerLevel()).isZero();
        assertThat(created.getHappiness()).isZero();
    }

    @Test
    void feedPet_reducesHungerButNotBelow0() {
        PetDTO pet = petService.createPet(new PetDTO("Doris", "Dog", 5, 50));
        Optional<PetDTO> updated = petService.feedPet(pet.getId());

        assertThat(updated).isPresent();
        assertThat(updated.get().getHungerLevel()).isZero();
    }

    @Test
    void playWithPetIncreasesHappinessButNotAbove100() {
        PetDTO pet = petService.createPet(new PetDTO("Doris", "Dog", 50, 95));
        Optional<PetDTO> updated = petService.playWithPet(pet.getId());

        assertThat(updated).isPresent();
        assertThat(updated.get().getHappiness()).isEqualTo(100);
    }
}
