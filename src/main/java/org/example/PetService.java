package org.example;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.pet.Pet;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class PetService {
	private final Map<String, Pet> pets = new ConcurrentHashMap<>();
	private final Random rand = new Random();

	public List<Pet> getPets() {
		return List.copyOf(pets.values());
	}

	public List<Pet> getPaginatedPets(List<Pet> inputPets, int offset, int limit) {
		List<Pet> paginatedPets = inputPets.stream()
						.skip(offset)
						.limit(limit)
						.toList();
		return List.copyOf(paginatedPets);
	}

	public List<Pet> getFilteredPets(List<Pet> inputPets, String species) {
		if (species == null || species.isBlank()) {
			return List.copyOf(inputPets);
		}
		String needle = species.trim();
		List<Pet> filteredPets = inputPets.stream()
						.filter(p -> p.getSpecies() != null && p.getSpecies().equalsIgnoreCase(needle))
						.toList();
		return List.copyOf(filteredPets);
	}

	public List<Pet> getSortedPets(List<Pet> inputPets, String sortBy, String order) {
		List<Pet> sortedPets;
		if (sortBy.equalsIgnoreCase("name")) {
			sortedPets = inputPets.stream().sorted(Comparator.comparing(Pet::getName)).toList();
		} else if (sortBy.equalsIgnoreCase("species")) {
			sortedPets = inputPets.stream().sorted(Comparator.comparing(Pet::getSpecies)).toList();
		} else if (sortBy.equalsIgnoreCase("hunger")) {
			sortedPets = inputPets.stream().sorted(Comparator.comparing(Pet::getHunger)).toList();
		} else {
			sortedPets = inputPets.stream().sorted(Comparator.comparing(Pet::getHappiness)).toList();
		}
		if (order.equalsIgnoreCase("desc")) {
			sortedPets = sortedPets.reversed();
		}
		return List.copyOf(sortedPets);
	}

	public void adoptPet(Pet pet) {
		if (pet == null || pet.getName() == null || pet.getSpecies() == null) {
			throw new IllegalArgumentException("Pet, Name or Species cannot be null");
		}
		String name = pet.getName().trim();
		String species = pet.getSpecies().trim().toLowerCase(java.util.Locale.ROOT);
		pet.setSpecies(species);
		pets.put(name + ":" + species, pet);
	}

	public Pet getPet(String id) {
		return pets.get(id);
	}

	public Pet feedPet(String id) {
		return pets.computeIfPresent(id, (k, pet) -> {
			int reduce = rand.nextInt(10) + 1;
			pet.setHunger(Math.max(pet.getHunger() - reduce, 0));
			return pet;
		});
	}

	public Pet playWithPet(String id) {
		return pets.computeIfPresent(id, (k, pet) -> {
			int increase = rand.nextInt(10) + 1;
			pet.setHappiness(Math.max(pet.getHappiness() + increase, 0));
			return pet;
		});
	}

	public Pet deletePet(String id) {
		return pets.remove(id);
	}
}
