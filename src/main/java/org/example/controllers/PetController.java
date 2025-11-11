package org.example.controllers;

import jakarta.validation.Valid;
import org.example.entities.Pet;
import org.example.services.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    // Get all pets with optional filtering, sorting, and pagination.
    @GetMapping
    public List<Pet> getAllPetsService(
            @RequestParam(required = false) String species,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) Integer limit) {

        return petService.getAllPets(species, sortBy, order, offset, limit);
    }

    // Get a single pet by id
    @GetMapping("/{id}")
    public Pet getPetById(@PathVariable Long id) {
        return petService.getPetById(id);
    }

    // Create a new pet
    @PostMapping
    public ResponseEntity<Pet> addPet(@Valid @RequestBody Pet pet) {
        Pet createdPet = petService.addPet(pet);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdPet);
    }

    // Feed a pet - decreases hunger level by 1
    @PutMapping("/{id}/feed")
    public Pet feedPet(@PathVariable Long id) {
        return petService.feedPet(id);
    }

    // Play with a pet - increases happiness level by 1
    @PutMapping("/{id}/play")
    public Pet playWithPet(@PathVariable Long id) {
        return petService.playWithPet(id);
    }

    // Delete a pet by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
