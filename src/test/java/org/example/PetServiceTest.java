package org.example;

import org.example.pet.Pet;
import org.example.pet.PetDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

		assertThat(firstPet.getHappiness()).isLessThan(middlePet.getHappiness());
		assertThat(middlePet.getHappiness()).isLessThan(secondPet.getHappiness());
		assertThat(firstPet.getHappiness()).isLessThan(secondPet.getHappiness());
	}

	@Test
	void getSortedPetsDescendingHunger() {
		List<Pet> sortedPets = petService.getSortedPets(petService.getPets(), "hunger", "desc");
		Pet firstPet = sortedPets.getFirst();
		Pet middlePet = sortedPets.get(sortedPets.size() / 2);
		Pet secondPet = sortedPets.getLast();

		assertThat(firstPet.getHunger()).isGreaterThan(middlePet.getHunger());
		assertThat(middlePet.getHunger()).isGreaterThan(secondPet.getHunger());
		assertThat(firstPet.getHunger()).isGreaterThan(secondPet.getHunger());
	}

	@ParameterizedTest
	@ValueSource(strings = {"Lucy", "Sadie", "Fido"})
	void testGetDogPet(String dogName) {
		Pet dog = petService.getPet(dogName + ":" + "dog");

		assertThat(dog).isNotNull();
		assertThat(dog.getName()).isEqualTo(dogName);
		assertThat(dog.getSpecies()).isEqualTo("dog");
	}

	@ParameterizedTest
	@ValueSource(strings = {"Lucy", "Sadie", "Fido"})
	void testFeedingPet(String dogName) {
		int dogHungerBefore = petService.getPet(dogName + ":" + "dog").getHunger();
		Pet fedPet = petService.feedPet(dogName + ":" + "dog");
		int dogHungerAfter = fedPet.getHunger();

		assertThat(dogHungerBefore).isGreaterThan(dogHungerAfter);
	}

	@ParameterizedTest
	@ValueSource(strings = {"Rut", "Krullis", "Chilla", "Capy"})
	void testPlayingWithPet(String rabbitName) {
		int rabbitHappinessBefore = petService.getPet(rabbitName + ":" + "rabbit").getHappiness();
		Pet happyPet = petService.playWithPet(rabbitName + ":" + "rabbit");
		int rabbitHappinessAfter = happyPet.getHappiness();

		assertThat(rabbitHappinessBefore).isLessThan(rabbitHappinessAfter);
	}

	@Test
	void testDeletingAPet() {
		petService.deletePet("Fido:dog");
		assertThat(petService.getPet("Fido:dog")).isNull();
		assertThat(petService.getPets().size()).isEqualTo(17);
	}
}