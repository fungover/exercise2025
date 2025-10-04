package org.example.service;

import org.example.dto.PetDTO;
import org.example.repository.InMemoryPetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class PetServiceTest {

    private PetService petService;

    @BeforeEach
    void setUp() {
        petService = new PetService(new InMemoryPetRepository());
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

    @Test
    void deletePetRemovesPet() {
        PetDTO pet = petService.createPet(new PetDTO("Doris", "Dog", 50, 50));
        boolean deleted = petService.deletePet(pet.getId());


        assertThat(deleted).isTrue();
        assertThat(petService.getPetById(pet.getId())).isEmpty();
    }

    @Test
    void getAllPetsReturnsAllPets() {
        petService.createPet(new PetDTO("Doris", "Dog", 50, 50));
        petService.createPet(new PetDTO("Maja", "Cat", 50, 50));

        assertThat(petService.getAllPets()).hasSize(2);
    }
}
