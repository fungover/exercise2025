package org.example.services;

import org.example.entities.Pet;
import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.repositories.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    // Get all pets with optional filtering, sorting, and pagination.
    public List<Pet> getAllPets(String species, String sortBy, String order, Integer offset, Integer limit) {
        // Get pets - filtered by species if provided
        List<Pet> petList;
        if (species != null && !species.isBlank()) {
            petList = petRepository.findBySpeciesIgnoreCase(species);
        } else {
            petList = petRepository.findAll();
        }

        // Sort if sortBy is provided
        if (sortBy != null && !sortBy.isBlank()) {
            Comparator<Pet> comparator = getComparator(sortBy);
            if ("desc".equalsIgnoreCase(order)) {
                comparator = comparator.reversed();
            }
            petList.sort(comparator);
        }
        // Validate pagination parameters
        if ((offset != null && offset < 0) || (limit != null && limit < 0)) {
            throw new BadRequestException("Offset and limit must be positive");
        }
        // Apply pagination if both offset and limit are provided
        if (offset != null && limit != null) {
            int start = Math.min(offset, petList.size());
            int end = Math.min(start + limit, petList.size());
            return petList.subList(start, end);
        }

        return petList;
    }

    // Get comparator for sorting based on field name
    private Comparator<Pet> getComparator(String sortBy) {
        return switch (sortBy.toLowerCase()) {
            case "name" -> Comparator.comparing(Pet::getName, String.CASE_INSENSITIVE_ORDER);
            case "species" -> Comparator.comparing(Pet::getSpecies, String.CASE_INSENSITIVE_ORDER);
            case "hungerlevel" -> Comparator.comparingInt(Pet::getHungerLevel);
            case "happinesslevel" -> Comparator.comparingInt(Pet::getHappinessLevel);
            default -> Comparator.comparing(Pet::getId);
        };
    }

    // Get pet by id
    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet with id '" + id + "' does not exist"));
    }

    // Create a new pet
    @Transactional
    public Pet addPet(Pet pet) {
        return petRepository.save(pet);
    }

    // Feed a pet - decrease hunger level by 1
    @Transactional
    public Pet feedPet(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet with id '" + id + "' does not exist"));

        if (pet.getHungerLevel() == 0) {
            throw new BadRequestException(pet.getName() + " is full and cannot eat more right now!");
        }

        pet.setHungerLevel(pet.getHungerLevel() - 1);
        return petRepository.save(pet);
    }

    // Play with a pet - increase happiness level by 1
    @Transactional
    public Pet playWithPet(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet with id '" + id + "' does not exist"));

        if (pet.getHappinessLevel() == 10) {
            throw new BadRequestException(pet.getName() + " is tired and cannot play more right now!");
        }

        pet.setHappinessLevel(pet.getHappinessLevel() + 1);
        return petRepository.save(pet);
    }

    // Delete a pet by id
    @Transactional
    public Pet deletePet(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet with id '" + id + "' does not exist"));

        petRepository.delete(pet);
        return pet;
    }
}
