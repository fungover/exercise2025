package org.example;

import org.example.pet.Pet;
import org.example.pet.PetDTO;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PetServiceTest {
	PetService petService;

	@BeforeEach
	void init() {
		petService = new PetService();
		List<String> dogNames = Arrays.asList(
						"Fido", "Max", "Lucy",
						"Cooper", "Rocky", "Sadie");
		for (String name : dogNames) {
			petService.adoptPet(new PetDTO(name, "dog"));
		}
		List<String> catNames = Arrays.asList(
						"Luna", "Siri", "Milo",
						"Oliver", "Leo", "Charles");
		for (String name : catNames) {
			petService.adoptPet(new PetDTO(name, "cat"));
		}
		List<String> rabbitNames = Arrays.asList(
						"Rut", "Krullis", "Chilla",
						"Capy", "Tossan", "Bambi");
		for (String name : rabbitNames) {
			petService.adoptPet(new PetDTO(name, "rabbit"));
		}
	}

	@Test
	void testAdoptPet() {
		Pet pet = new PetDTO("Iris", "dog");
		petService.adoptPet(pet);

		assertThat(petService.getPets().size()).isEqualTo(19);
	}

	@Test
	void testGetAllPets() {
		List<Pet> pets = petService.getPets();
		assertThat(pets.size()).isEqualTo(18);
	}

	@Test
	void getPaginatedPets() {
		List<Pet> paginatedPets = petService.getPaginatedPets(petService.getPets(), 0, 4);

		assertThat(paginatedPets.size()).isEqualTo(4);
	}

	@ParameterizedTest
	@ValueSource(strings = {"cat", "dog", "rabbit"})
	void getFilteredPets(String filter) {
		List<Pet> filteredPets = petService.getFilteredPets(petService.getPets(), filter);

		assertThat(filteredPets.size()).isEqualTo(6);
		assertThat(filteredPets)
						.extracting(Pet::getSpecies)
						.containsOnly(filter);
	}

	@Test
	void getSortedPetsAscendingHappiness() {
		List<Pet> sortedPets = petService.getSortedPets(petService.getPets(), "happiness", "asc");
		Pet firstPet = sortedPets.getFirst();
		Pet middlePet = sortedPets.get(sortedPets.size() / 2);
		Pet secondPet = sortedPets.getLast();

		assertThat(firstPet.getHappiness() < middlePet.getHappiness()).isTrue();
		assertThat(middlePet.getHappiness() < secondPet.getHappiness()).isTrue();
		assertThat(firstPet.getHappiness() < secondPet.getHappiness()).isTrue();
	}

	@Test
	void getSortedPetsDescendingHungerDescending() {
		List<Pet> sortedPets = petService.getSortedPets(petService.getPets(), "hunger", "desc");
		Pet firstPet = sortedPets.getFirst();
		Pet middlePet = sortedPets.get(sortedPets.size() / 2);
		Pet secondPet = sortedPets.getLast();

		assertThat(firstPet.getHunger() > middlePet.getHunger()).isTrue();
		assertThat(middlePet.getHunger() > secondPet.getHunger()).isTrue();
		assertThat(firstPet.getHunger() > secondPet.getHunger()).isTrue();
	}

	@ParameterizedTest
	@ValueSource(strings = {"Lucy", "Sadie", "Fido"})
	void testGetDogPet(String dogName) {
		Pet dog = petService.getPet(dogName + "dog");

		assertThat(dog).isNotNull();
		assertThat(dog.getName()).isEqualTo(dogName);
		assertThat(dog.getSpecies()).isEqualTo("dog");
	}

	@ParameterizedTest
	@ValueSource(strings = {"Lucy", "Sadie", "Fido"})
	void testFeedingPet(String dogName) {
		int dogHungerBefore = petService.getPet(dogName + "dog").getHunger();
		Pet fedPet = petService.feedPet(dogName + "dog");
		int dogHungerAfter = fedPet.getHunger();

		assertThat(dogHungerBefore > dogHungerAfter).isTrue();
	}

	@ParameterizedTest
	@ValueSource(strings = {"Rut", "Krullis", "Chilla", "Capy"})
	void testPlayingWithPet(String rabbitName) {
		int rabbitHappinessBefore = petService.getPet(rabbitName + "rabbit").getHappiness();
		Pet happyPet = petService.playWithPet(rabbitName + "rabbit");
		int rabbitHappinessAfter = happyPet.getHappiness();

		assertThat(rabbitHappinessBefore < rabbitHappinessAfter).isTrue();
	}

	@Test
	void testDeletingAPet() {
		petService.deletePet("Fidodog");
		assertThat(petService.getPet("Fidodog")).isNull();
		assertThat(petService.getPets().size()).isEqualTo(17);
	}
}