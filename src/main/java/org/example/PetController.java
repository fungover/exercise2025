package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("api")
public class PetController {

    private final PetRepository repository;

    public PetController(PetRepository repository) {
        this.repository = repository;
    }

    @GetMapping("pets")
    public List<Pet> getAll() {
        return repository.findPets().stream()
                .map(pet -> new Pet(pet.getName(), pet.getType(), pet.getFavorite()))
                .toList();
    }

    @GetMapping("pets/{name}")
    public Pet getByName(@PathVariable String name) {
        return repository.findByName(name).map(
                pet -> new Pet(pet.getName(), pet.getType(), pet.getFavorite()))
                .orElseThrow();
    }
}
