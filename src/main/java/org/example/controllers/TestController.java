package org.example.controllers;

import org.example.entities.Pet;
import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.repositories.PetRepository;
import org.example.services.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private final PetRepository repository;
    private final PetService petService;

    public TestController(PetRepository repository, PetService petService) {
        this.repository = repository;
        this.petService = petService;
    }

    @GetMapping("/pets")
    public List<Pet> getAllPets() {
        return repository.findAll();
    }

    @GetMapping("/pets/{id}")
    public Pet getPetById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Pet with id '" + id + "' does not exist"));
    }

    @GetMapping("/pets/species/{species}")
    public List<Pet> getPetsBySpecies(@PathVariable String species) {
        return repository.findBySpeciesIgnoreCase(species);
    }

    @GetMapping("/pets/name/{name}")
    public Pet getPetByName(@PathVariable String name) {
        return repository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new NotFoundException("Pet with name '" + name + "' does not exist"));
    }

    @GetMapping("/test-error")
    public String testBadRequest() {
        throw new BadRequestException("This is a test bad request error!");
    }

    // Test PetService endpoint
    @GetMapping("/service/pets")
    public List<Pet> getAllPetsService(
            @RequestParam(required = false) String species,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) Integer limit) {

        return petService.getAllPets(species, sortBy, order, offset, limit);
    }

}
