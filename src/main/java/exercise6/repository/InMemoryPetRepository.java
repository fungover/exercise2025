package exercise6.repository;

import exercise6.pets.AnimalType;
import exercise6.pets.Pet;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class InMemoryPetRepository implements PetRepository {

    private final List<Pet> petList = new CopyOnWriteArrayList<>();

    public InMemoryPetRepository() {
        petList.add(new Pet("Fido", AnimalType.DOG));
        petList.add(new Pet( "Karo", AnimalType.DOG));
        petList.add(new Pet( "Misse", AnimalType.CAT));
        petList.add(new Pet( "Roy", AnimalType.CAT));
        petList.add(new Pet( "Miro", AnimalType.CAT));
    }

    @Override
    public String addPet(String name, AnimalType animalType) {
        petList.add(new Pet(name,  animalType));
        return "Pet added successfully";
    }

    @Override
    public List<Pet> getPets() {
        return petList;
    }

    @Override
    public Pet getUniqPet(int id) {

        Pet uniqPet = petList.stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElse(null);

        if(uniqPet == null) {
            throw new NotFoundException("Pet with id " + id + " not found");
        }

       return uniqPet;
    }

    @Override
    public List<Pet> removePet(int id) {
     petList.removeIf(item -> item.getId() == id);
        return petList;
    }

}
