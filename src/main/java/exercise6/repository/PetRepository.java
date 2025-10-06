package exercise6.repository;

import exercise6.pets.AnimalType;
import exercise6.pets.Pet;

import java.util.List;

public interface PetRepository {

    String addPet(String name, AnimalType animalType);
    List<Pet> getPets();
    Pet getUniqPet(int id);
    List<Pet> removePet(int id);
}
