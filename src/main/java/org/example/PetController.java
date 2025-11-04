package org.example;

import org.springframework.web.bind.annotation.GetMapping;
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
        return repository.findAll().stream()
                .map(pet -> new Pet(pet.getName(), pet.getType()))
                .toList();
    }
}
