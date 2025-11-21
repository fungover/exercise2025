package org.example;

import org.example.entities.Pet;
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
    public List<Pet> getPets() {
        return repository.findAll().stream()
                .map(p -> new Pet(p.getName(), p.getAge(), p.getSpecies(), p.getHungerLevel(), p.getHappiness()))
                .toList();
    }
}

