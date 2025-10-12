package org.example;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.pet.Happiness;
import org.example.pet.Hunger;
import org.example.pet.Pet;
import org.example.pet.PetDTO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class PetService {
	Map<String, Pet> pets = new ConcurrentHashMap<>();

	public List<Pet> getPets() {
		return List.copyOf(pets.values());
	}

	public void adoptPet(Pet pet) {;
		pets.put(pet.getName() + pet.getSpecies(), pet);
	}

	public Pet getPet(String id) {
		return pets.get(id);
	}

	public Pet feedPet(String id) {
		Pet pet = pets.get(id);
		if (pet != null) {
			pet.setHunger(Hunger.FULL);
		}
		return pet;
	}

	public Pet playWithPet(String id) {
		Pet pet = pets.get(id);
		if (pet != null) {
			pet.setHappiness(Happiness.HAPPY);
		}
		return pet;
	}

		public Pet deletePet (String id){
			return pets.remove(id);
		}
	}
