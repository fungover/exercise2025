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

    @GetMapping("/animals")
    public List<PetDto> getAllAnimals() {
       return repository.findAllAnimals().stream()
               .map(Pet -> new PetDto(
                       Pet.getId(),
                       Pet.getSpecies(),
                       Pet.getName(),
                       Pet.getAge(),
                       Pet.getBirthDate(),
                       Pet.getCreatedAt()

               ))
               .toList();
    }


    // @PostMapping
    // adding animals
    @PostMapping("animals")
    public ResponseEntity<PetDto> saveAnimal(@RequestBody Pet pet) {
        Pet saved = repository.createAnimal(pet);
        PetDto petDto = new PetDto(
                saved.getId(),
                saved.getSpecies(),
                saved.getName(),
                saved.getAge(),
                saved.getBirthDate(),
                saved.getCreatedAt()
        );

        if (saved != null) {
            URI location = URI.create("/api/animals/" + saved.getId());
            return ResponseEntity.created(location).body(petDto);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    // deleting animals
    @DeleteMapping("/animals/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Integer id) {
        boolean deleted = repository.deleteAnimal(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

        // updating animals
     @PutMapping("/animals/{id}")
        public ResponseEntity<PetDto> updateAnimal(@PathVariable int id, @RequestBody Pet pet) {
         System.out.println(pet.getSpecies());
         System.out.println("Birth: "+pet.getBirthDate());
         System.out.println("Created: "+pet.getCreatedAt());
            pet.setId(id);
            Pet updatedPet = repository.updateAnimal(pet);
            if (updatedPet != null) {
                PetDto sendBack = new PetDto(
                        updatedPet.getId(),
                        updatedPet.getSpecies(),
                        updatedPet.getName(),
                        updatedPet.getAge(),
                        updatedPet.getBirthDate(),
                        updatedPet.getCreatedAt()
                );
                return ResponseEntity.ok(sendBack);
            } else {
                return ResponseEntity.notFound().build();
     }
      }

}
