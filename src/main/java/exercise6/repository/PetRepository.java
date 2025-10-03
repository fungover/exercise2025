package exercise6.repository;

import exercise6.pets.Pet;

import java.util.List;

public interface PetRepository {

    void addPet(Pet pet);
    List<Pet> getPets();
    Object getUniqPet(String id);
}
