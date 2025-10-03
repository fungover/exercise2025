package exercise6.repository;

import exercise6.pets.AnimalType;
import exercise6.pets.Pet;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@ApplicationScoped
public class InMemoryPetRepository implements PetRepository {

    private final List<Pet> petList = new CopyOnWriteArrayList<>();

    public InMemoryPetRepository() {
        petList.add(new Pet("Fido", AnimalType.DOG));
        petList.add(new Pet( "Karo", AnimalType.DOG));
        petList.add(new Pet( "Misse", AnimalType.CAT));
        petList.add(new Pet( "Roy", AnimalType.CAT));
    }

    @Override
    public void addPet(Pet pet) {
        petList.add(pet);
    }

    @Override
    public List<Pet> getPets() {
        return petList;
    }

    @Override
    public Object getUniqPet(String id) {
        int uniqId = Integer.parseInt(id);

        return petList.stream()
                .filter(item -> item.getId() == uniqId).collect(Collectors.toSet());
    }

}
