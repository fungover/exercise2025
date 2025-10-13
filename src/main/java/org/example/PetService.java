package org.example;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.pet.Pet;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class PetService {
	Map<String, Pet> pets = new ConcurrentHashMap<>();

	public List<Pet> getPets() {
		return List.copyOf(pets.values());
	}

	public List<Pet> getPets(int offset, int limit) {
		return pets.values().stream()
						.skip(offset)
						.limit(limit)
						.toList();
	}

	public List<Pet> getPets(String species) {
		return pets.values().stream()
						.filter(p -> p.getSpecies().equalsIgnoreCase(species))
						.toList();
	}

	public void adoptPet(Pet pet) {
		pet.setSpecies(pet.getSpecies().toLowerCase().trim());
		pets.put(pet.getName() + pet.getSpecies(), pet);
	}

	public Pet getPet(String id) {
		return pets.get(id);
	}

	public Pet feedPet(String id, int amount) {
		Pet pet = pets.get(id);
		if (pet != null) {
			pet.setHunger(amount);
		}
		return pet;
	}

	public Pet playWithPet(String id, int amount) {
		Pet pet = pets.get(id);
		if (pet != null) {
			pet.setHappiness(amount);
		}
		return pet;
	}

		public Pet deletePet (String id){
			return pets.remove(id);
		}
	}
