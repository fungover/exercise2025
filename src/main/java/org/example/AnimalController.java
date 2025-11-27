package org.example;


import org.example.service.AnimalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class AnimalController {
    private final AnimalService repository;

    public AnimalController(AnimalService repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index() {
        return "Authentication successful!";
    }

    @GetMapping("animals")
    public List<PetDto> getAllAnimals() {
       return repository.findAllAnimals().stream()
               .map(Pet -> new PetDto(
                       Pet.getSpecies(),
                       Pet.getName(),
                       Pet.getAge()
               ))
               .toList();
    }



// @PostMapping
    // adding animals

//    @DeleteMapping
    // deleting animals

//    @PutMapping
    // updating animals

}
