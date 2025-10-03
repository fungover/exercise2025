package exercise6.repository;

import exercise6.pets.AnimalType;
import exercise6.pets.Pet;

import java.util.List;

public interface PetRepository {

    String addPet(String name, AnimalType animalType);
    List<Pet> getPets();
    Object getUniqPet(String id);
    List<Pet> removePet(String id);
}
