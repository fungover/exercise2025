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
    public List<PetDto> getPets() {
        return repository.findAll().stream()
                .map(p -> new PetDto(p.getId(), p.getName(), p.getAge(), p.getSpecies(), p.getHungerLevel(), p.getHappiness(), p.getCreatedAt()))
                .toList();
    }

}

