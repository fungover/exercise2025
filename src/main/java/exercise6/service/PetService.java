package exercise6.service;

import exercise6.pets.AnimalType;
import exercise6.pets.Pet;
import exercise6.repository.PetRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class PetService {

    @Inject
    PetRepository petRepository;

    public List<Pet> sortPetByType(AnimalType animalType) {
        return petRepository.getPets().stream()
                .filter(item -> item.getAnimalType() == animalType).toList();
    }

    public List<Pet> sortPetByHappinessDesc() {
        return petRepository.getPets().stream()
                .sorted(Comparator.comparingInt(Pet::getHappiness).reversed())
                .toList();
    }

    public List<Pet> sortPetByHappinessAsc() {
        return petRepository.getPets().stream()
                .sorted(Comparator.comparingInt(Pet::getHappiness))
                .toList();
    }

    public List<Pet> petPagination(int page, int pageSize) {

        int petToDisplay = petRepository.getPets().size();

        if(petToDisplay == 0){
            return List.of();
        }

        int maxPage = (int) Math.ceil((double) petToDisplay / pageSize);
        if (page > maxPage) {
            page = maxPage;
        }
        long start = (long)(page - 1) * pageSize;
        return petRepository.getPets().stream()
                .skip(start)
                .limit(pageSize)
                .toList();
    }
}
