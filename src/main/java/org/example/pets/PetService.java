package org.example.pets;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.exception.BadRequestException;
import org.example.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@ApplicationScoped
public class PetService {

    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(4);

    public PetService() {
        // Mocked data
        pets.put(1L, new PetDTO(1L, "Rex", "Dog", 6, 10));
        pets.put(2L, new PetDTO(2L, "Garfield", "Cat", 10, 1));
        pets.put(3L, new PetDTO(3L, "Flax", "Bird", 1, 5));
        pets.put(4L, new PetDTO(4L, "Jax", "Dog", 7, 3));
        pets.put(5L, new PetDTO(5L, "Alex", "Dog", 4, 7));
        pets.put(6L, new PetDTO(6L, "Spot", "Dog", 2, 9));
    }


    public List<PetDTO> getAllPets(String species, String sortBy, String order) {
        List<PetDTO> petList = new ArrayList<>(pets.values());

        // Filter by species if provided
        if (species != null && !species.isBlank()) {
            petList = petList.stream()
                    .filter(pet -> pet.getSpecies().equalsIgnoreCase(species))
                    .collect(Collectors.toList());
        }

        // Sort if sortBy is provided
        if (sortBy != null && !sortBy.isBlank()) {
            Comparator<PetDTO> comparator = getComparator(sortBy);
            if ("desc".equalsIgnoreCase(order)) {
                comparator = comparator.reversed();
            }
            petList.sort(comparator);
        }

        return petList;
    }

    private Comparator<PetDTO> getComparator(String sortBy) {
        return switch (sortBy.toLowerCase()) {
            case "name" -> Comparator.comparing(PetDTO::getName, String.CASE_INSENSITIVE_ORDER);
            case "species" -> Comparator.comparing(PetDTO::getSpecies, String.CASE_INSENSITIVE_ORDER);
            case "hungerlevel" -> Comparator.comparing(PetDTO::getHungerLevel);
            case "happinesslevel" -> Comparator.comparing(PetDTO::getHappinessLevel);
            default -> Comparator.comparing(PetDTO::getId);
        };
    }

    public PetDTO getPetById(Long id) {
        PetDTO pet = pets.get(id);
        if (pet == null) {
            throw new NotFoundException("Pet with id " + id + " does not exist");
        }
        return pet;
    }

    public PetDTO addPet(PetDTO petDTO) {
        Long newId = idGenerator.getAndIncrement();
        petDTO.setId(newId);

        pets.put(newId, petDTO);
        return petDTO;
    }

    public PetDTO feedPet(Long id) {
        PetDTO pet = getPetById(id);

        if (pet.getHungerLevel() == 0) {
            throw new BadRequestException(pet.getName() + " is full and cannot eat more right now!");
        }

        int newHungerLevel = Math.max(0, pet.getHungerLevel() - 1);
        pet.setHungerLevel(newHungerLevel);

        return pet;
    }

    public PetDTO playWithPet(Long id) {
        PetDTO pet = getPetById(id);

        if (pet.getHappinessLevel() == 10) {
            throw new BadRequestException(pet.getName() + " is exhausted and must take a break!");
        }

        int newHappinessLevel = Math.min(10, pet.getHappinessLevel() + 1);
        pet.setHappinessLevel(newHappinessLevel);

        return pet;
    }

    public PetDTO deletePet(Long id) {
        PetDTO pet = getPetById(id);

        pets.remove(id);
        return pet;
    }

}
